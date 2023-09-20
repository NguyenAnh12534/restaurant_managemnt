package com.ha.app.data;

import com.ha.app.annotations.data.Entity;
import com.ha.app.annotations.data.Id;
import com.ha.app.annotations.data.ManyToOne;
import com.ha.app.annotations.data.OneToMany;
import com.ha.app.commons.depedencyinjection.ApplicationContext;
import com.ha.app.constants.DataConstants;
import com.ha.app.data.drivers.DataDriver;
import com.ha.app.data.relationships.RelationshipManager;
import com.ha.app.enums.errors.ErrorSeverity;
import com.ha.app.enums.errors.ErrorType;
import com.ha.app.exceptions.ApplicationException;
import com.ha.app.exceptions.ErrorInfo;
import com.ha.app.helpers.ClassHelper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DbContext {
    private final DataDriver dataDriver;
    private Map<Class<?>, DbSet> dbSetMap = new HashMap<>();
    private RelationshipManager relationshipManager;

    public DbContext(DataDriver dataDriver) {
        this.dataDriver = dataDriver;
        this.scanForDbSets();
        this.relationshipManager = new RelationshipManager(this.dbSetMap.keySet());
    }

    public <T> DbSet<T> getDbSetOf(Class<T> tClass) {
        return this.dbSetMap.get(tClass);
    }

    public Map<Class<?>, DbSet> getDbSetMap() {
        return this.dbSetMap;
    }

    public void flush() {
        dbSetMap.forEach((aClass, dbSet) -> {
            dbSet.flush();
        });
    }

    private void scanForDbSets() {
        List<Class<?>> classes = ClassHelper.getAllClassesInPackage(DataConstants.ENTITIES_PACKAGE);
        classes.forEach(targetClass -> {
            if (targetClass.isAnnotationPresent(Entity.class)) {
                this.addDbSetOfModal(targetClass);
            }
        });
    }

    private <T> void addDbSetOfModal(Class<T> tClass) {

        this.dbSetMap.put(tClass, new DbSet(tClass, dataDriver, this));
    }

    public <T> void eagerLoadDataForEntity(T object) {
        Field[] fields = object.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                eagerLoadDataForField(field, object);
            }
            this.relationshipManager.resetValidation();
        } catch (Exception exception) {
            ApplicationException applicationException = new ApplicationException();

            ErrorInfo errorInfo = new ErrorInfo();
            errorInfo.setErrorId("EagerLoadingData");
            errorInfo.setContextId(this.getClass().getSimpleName());

            errorInfo.setErrorType(ErrorType.INTERNAL);
            errorInfo.setErrorSeverity(ErrorSeverity.CRITICAL);

            errorInfo.setErrorCorrection("Fail to persisting data from relationships.");

            applicationException.addErrorInfo(errorInfo);
            throw applicationException;
        }
    }

    public <T> void eagerLoadDataForField(Field field, T object) throws NoSuchFieldException, IllegalAccessException {
        if (field.isAnnotationPresent(ManyToOne.class)) {
            loadDataForManyToOne(field, object);
        } else if (field.isAnnotationPresent(OneToMany.class)) {
            loadDataForOneToMany(field, object);
        }
    }

    private <T> void loadDataForManyToOne(Field field, T object) throws NoSuchFieldException, IllegalAccessException {

        Class<?> parentClass = field.getType();
        Class<?> childClass = object.getClass();

        if (this.willCreateCircular(childClass, parentClass)) {
            return;
        }

        Class<?> parentType = field.getType();
        Field foreignKeyField = object.getClass().getDeclaredField(parentType.getSimpleName().toLowerCase() + "_id");

        foreignKeyField.setAccessible(true);
        int foreignKeyValue = foreignKeyField.getInt(object);

        field.setAccessible(true);
        Object parent = this.getDbSetOf(parentType).filterByField("id", foreignKeyValue).getOne();
        if (parent != null) {
            field.set(object, parent);
        }
    }

    private <T> void loadDataForOneToMany(Field field, T object) throws NoSuchFieldException, IllegalAccessException {
        field.setAccessible(true);
        OneToMany oneToMany = field.getAnnotation(OneToMany.class);
        Class<?> childClass = oneToMany.childEntity();
        Class<?> parentClass = object.getClass();

        if (this.willCreateCircular(parentClass, childClass)) {
            return;
        }

        Field primaryField = null;

        Field[] fields = object.getClass().getDeclaredFields();

        for (Field attribute : fields) {
            if (attribute.isAnnotationPresent(Id.class)) primaryField = attribute;
        }

        if (primaryField == null) return;

        primaryField.setAccessible(true);
        Object primaryFieldValue = primaryField.get(object);

        field.setAccessible(true);
        Object children = this.getDbSetOf(childClass).filterByField(object.getClass().getSimpleName().toLowerCase() + "_id", primaryFieldValue).getAll();
        if (children != null) {
            field.set(object, children);
        }
    }

    private boolean willCreateCircular(Class<?> sourceClass, Class<?> targetClass) {
        if (this.relationshipManager.isConnected(sourceClass, targetClass) && !this.relationshipManager.isOldConnection(sourceClass, targetClass)) {
            return true;
        }

        this.relationshipManager.link(sourceClass, targetClass);
        return false;
    }
}
