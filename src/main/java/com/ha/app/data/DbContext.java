package com.ha.app.data;

import com.ha.app.annotations.data.Entity;
import com.ha.app.constants.DataConstants;
import com.ha.app.data.drivers.DataDriver;
import com.ha.app.helpers.ClassScanner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbContext {
    private final DataDriver dataDriver;
    private Map<Class<?>, DbSet> dbSetMap = new HashMap<>();

    public DbContext(DataDriver dataDriver) {
        this.dataDriver = dataDriver;
        scanForDbSets();
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
        List<Class<?>> classes = ClassScanner.getAllClassesInPackage(DataConstants.ENTITIES_PACKAGE);
        classes.forEach(targetClass -> {
            if (targetClass.isAnnotationPresent(Entity.class)) {
                this.addDbSetOfModal(targetClass);
            }
        });
    }



    private <T> void addDbSetOfModal(Class<T> tClass) {

        this.dbSetMap.put(tClass, new DbSet(tClass, dataDriver));
    }



}
