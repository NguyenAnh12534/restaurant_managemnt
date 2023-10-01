package com.ha.app.data;

import com.ha.app.annotations.data.Entity;
import com.ha.app.annotations.data.ForeignKey;
import com.ha.app.annotations.data.Id;
import com.ha.app.annotations.data.ManyToOne;
import com.ha.app.annotations.data.OneToMany;
import com.ha.app.constants.DataConstants;
import com.ha.app.data.drivers.DataDriver;
import com.ha.app.enums.errors.ErrorSeverity;
import com.ha.app.enums.errors.ErrorType;
import com.ha.app.exceptions.ApplicationException;
import com.ha.app.exceptions.ErrorInfo;
import com.ha.app.helpers.ClassHelper;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class is the container of all DbSet in the application
 * Each DbSet handles data of an Entity
 * This class also handles eager loading data between entities
 */
public class DbContext {
    private final DataDriver dataDriver;
    private Map<Class<?>, DbSet> dbSetMap = new HashMap<>();
    private RelationshipManager relationshipManager;

    public DbContext(DataDriver dataDriver) {
        this.dataDriver = dataDriver;
        this.scanForDbSets();
        this.relationshipManager = new RelationshipManager(this.dbSetMap.keySet());
    }

    /**
     * This function get a DbSet of a particular class
     * @param tClass must be an Entity
     * @return
     * @param <T>
     */
    public <T> DbSet<T> getDbSetOf(Class<T> tClass) {
        return this.dbSetMap.get(tClass);
    }

    /**
     * This method save data changes
     */
    public void flush() {
        dbSetMap.forEach((aClass, dbSet) -> {
            dbSet.flush();
        });
    }

    /**
     * This method helps to scan all entities in application
     * A DbSet in then created for each entity
     */
    private void scanForDbSets() {
        List<Class<?>> classes = ClassHelper.getAllClassesInPackage(DataConstants.ENTITIES_PACKAGE);
        classes.forEach(targetClass -> {
            if (targetClass.isAnnotationPresent(Entity.class)) {
                this.addDbSetOfModal(targetClass);
            }
        });
    }

    /**
     * This method creates a DbSet for an Entity
     * The newly created DbSet is added directly into dbSetMap
     *
     * @param tClass
     *         Class of the Entity of the DbSet
     * @param <T>
     *         Type of the Entity of the DbSet
     */
    private <T> void addDbSetOfModal(Class<T> tClass) {

        this.dbSetMap.put(tClass, new DbSet(tClass, dataDriver, this));
    }

    /**
     * This method help to eagerly load data for all relationships of an Entity
     *
     * @param object
     *         the concrete Entity object to load data for
     */
    public void eagerLoadDataForEntity(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                eagerLoadDataForField(field, object);
            }
            this.relationshipManager.resetValidation();
        } catch (Exception exception) {
            ApplicationException applicationException = new ApplicationException();

            ErrorInfo errorInfo = new ErrorInfo();
            errorInfo.setErrorId("EagerLoadingData - " + exception.getMessage());
            errorInfo.setContextId(this.getClass().getSimpleName());

            errorInfo.setErrorType(ErrorType.INTERNAL);
            errorInfo.setErrorSeverity(ErrorSeverity.CRITICAL);

            errorInfo.setErrorCorrection("Fail to persisting data from relationships.");
            errorInfo.addParameter("targetClass", object.getClass());

            applicationException.addErrorInfo(errorInfo);
            throw applicationException;
        }
    }

    /**
     * This method load data of a relationship field in a Entity
     *
     * @param field
     *         the relationship field
     * @param object
     *         the concrete Entity object
     *
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public void eagerLoadDataForField(Field field, Object object) throws NoSuchFieldException, IllegalAccessException {
        if (field.isAnnotationPresent(ManyToOne.class)) {
            loadDataForManyToOneRelationship(field, object);
        } else if (field.isAnnotationPresent(OneToMany.class)) {
            loadDataForOneToManyRelationship(field, object);
        }
    }

    /**
     * This method load data for ManyToOne relationship
     *
     * @param field
     *         the field with ManyToOne relationship in an Entity
     * @param object
     *         the concrete Entity object
     *
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private void loadDataForManyToOneRelationship(Field field, Object object) throws NoSuchFieldException, IllegalAccessException {

        Class<?> parentClass = field.getType();
        Class<?> childClass = object.getClass();

        if (this.willCreateCircular(childClass, parentClass)) {
            return;
        }

        Class<?> parentType = field.getType();
        Field foreignKeyField = this.extractForeignKeyTo(parentClass, childClass);

        foreignKeyField.setAccessible(true);
        Object foreignKeyValue = foreignKeyField.get(object);

        Field parentPrimaryKey = this.getDbSetOf(parentClass).extractPrimaryField();

        field.setAccessible(true);
        Object parent = this.getDbSetOf(parentType).filterByField(parentPrimaryKey, foreignKeyValue).getOne();
        if (parent != null) {
            field.set(object, parent);
        }
    }

    /**
     * This method load data for OneToMany relationship
     *
     * @param oneToManyField
     *         the field with ManyToOne relationship in an Entity
     * @param object
     *         the concrete Entity object
     *
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private void loadDataForOneToManyRelationship(Field oneToManyField, Object object) throws NoSuchFieldException, IllegalAccessException {
        oneToManyField.setAccessible(true);
        Class<?> childClass = oneToManyField.getAnnotation(OneToMany.class).childEntity();
        Class<?> parentClass = object.getClass();

        if (this.willCreateCircular(parentClass, childClass)) {
            return;
        }

        Field parentPrimaryField = this.getDbSetOf(parentClass).extractPrimaryField();
        parentPrimaryField.setAccessible(true);
        Object parentPrimaryFieldValue = parentPrimaryField.get(object);

        Field childForeignKey = this.extractForeignKeyTo(parentClass, childClass);

        oneToManyField.setAccessible(true);
        Object children = this.getDbSetOf(childClass).filterByField(childForeignKey, parentPrimaryFieldValue).getAll();
        if (children != null) {
            oneToManyField.set(object, children);
        }
    }

    /**
     * This method check if an eager loading operation from an Entity to another will create a circular reference or not
     * Therefore, it helps prevent stackoverflow error when eager loading data
     *
     * @param sourceClass
     *         the source entity
     * @param targetClass
     *         the target entity being linked to
     *
     * @return
     */
    private boolean willCreateCircular(Class<?> sourceClass, Class<?> targetClass) {
        if (this.relationshipManager.isConnected(sourceClass, targetClass) && !this.relationshipManager.isOldConnection(sourceClass, targetClass)) {
            return true;
        }

        this.relationshipManager.link(sourceClass, targetClass);
        return false;
    }

    /**
     * This method extracts all fields that are marked as Foreign key in a class
     *
     * @param targetClass
     *
     * @return a list of fields
     */
    private List<Field> extractForeignKeyFields(Class<?> targetClass) {
        if (!targetClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Class is not an Entity");
        }
        return Arrays.stream(targetClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(ForeignKey.class))
                .collect(Collectors.toList());
    }

    /**
     * This method extract foreign key to a parent class from a child class
     * Both classes must be Entities
     *
     * @param parentClass
     * @param childClass
     *
     * @return
     */
    private Field extractForeignKeyTo(Class<?> parentClass, Class<?> childClass) {
        if (!parentClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Class is not an Entity");
        }

        List<Field> foreignKeyFields = this.extractForeignKeyFields(childClass);

        for (Field foreignKeyField : foreignKeyFields) {
            if (foreignKeyField.getAnnotation(ForeignKey.class).parentClass().equals(parentClass)) {
                return foreignKeyField;
            }
        }
        throw new IllegalArgumentException("Foreign key to not found");
    }

}
