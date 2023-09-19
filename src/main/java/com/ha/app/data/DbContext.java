package com.ha.app.data;

import com.ha.app.annotations.data.Entity;
import com.ha.app.annotations.data.ManyToOne;
import com.ha.app.annotations.data.OneToMany;
import com.ha.app.constants.DataConstants;
import com.ha.app.data.drivers.DataDriver;
import com.ha.app.helpers.ClassHelper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbContext {
    private final DataDriver dataDriver;
    private Map<Class<?>, DbSet> dbSetMap = new HashMap<>();
    private Map<String, Class<?>> entityRelationships = new HashMap<>();

    public DbContext(DataDriver dataDriver) {
        this.dataDriver = dataDriver;
        this.scanForDbSets();
        this.initializeEntityRelationships();
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

    private void initializeEntityRelationships() {
        if (this.dbSetMap.isEmpty()) {
            return;
        }

        initializeManyToOneRelationShips();
    }

    private void initializeManyToOneRelationShips() {
        this.dbSetMap.keySet().forEach(entityClass -> {
            Field[] fields = entityClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(ManyToOne.class)) {
                    if (!this.entityRelationships.containsKey(field.getName())) {
                        this.entityRelationships.put(field.getName(), field.getType());
                    }
                }
            }
        });
    }

    public <T> void eagerLoadDataForField(Field field, T object) throws NoSuchFieldException, IllegalAccessException {
        if(field.isAnnotationPresent(ManyToOne.class)) {
            Class<?> parentType = field.getType();
            Field foreignKeyField = object.getClass().getDeclaredField(parentType.getSimpleName().toLowerCase() + "_id");

            foreignKeyField.setAccessible(true);
            int foreignKeyValue = foreignKeyField.getInt(object);

            field.setAccessible(true);
            Object parent = this.getDbSetOf(parentType).filterByField("id", foreignKeyValue).getOne();
            if(parent != null) {
                field.set(object, parent);
            }
        }
    }

}
