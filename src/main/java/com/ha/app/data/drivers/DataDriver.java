package com.ha.app.data.drivers;

import java.util.List;
import java.util.Set;

/**
 * DataDrive is the component that takes care of data processing operation
 */
public interface DataDriver {
    public <T> void saveObject(T object);
    public <T> void saveAllObjects(Set<T> objects);
    public <T> void clearData(Class<T> targetClass);
    public <T> void appendObject(T object);
    public <T> void appendAllObjects(Set<T> objects);
    public <T> Set<T> getAll(Class<T> tClass);
}
