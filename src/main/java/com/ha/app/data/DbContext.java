package com.ha.app.data;

import com.ha.app.annotations.data_annotations.Entity;
import com.ha.app.constants.DataConstants;
import com.ha.app.data.drivers.DataDriver;
import com.ha.app.helpers.ClassScanner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbContext {

    private static final DbContext dbContext = null;
    private final DataDriver dataDriver;
    private Map<Class<?>, DbSet> dbSetMap = new HashMap<>();

    public DbContext(DataDriver dataDriver) {
        this.dataDriver = dataDriver;
        scanForDbSets();
    }

    private void scanForDbSets() {
        List<Class<?>> classes = ClassScanner.getAllClassesInPackage(DataConstants.MODALS_PACKAGE);
        classes.forEach(targetClass -> {
            if (targetClass.isAnnotationPresent(Entity.class)) {
                this.addDbSetOfModal(targetClass);
            }
        });
    }

    public <T> DbSet<T> getDbSetOf(Class<T> tClass) {
        return this.dbSetMap.get(tClass);
    }

    private <T> void addDbSetOfModal(Class<T> tClass) {

        this.dbSetMap.put(tClass, new DbSet(tClass, dataDriver));
    }

    public Map<Class<?>, DbSet> getDbSetMap() {
        return this.dbSetMap;
    }
}
