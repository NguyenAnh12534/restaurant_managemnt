package com.ha.app.data.drivers;

import java.util.List;

public interface DataDriver {
    public <T> void saveObject(T object);
    public <T> void saveAllObjects(List<T> objects);
    public <T> void appendObject(T object);
    public <T> void appendAllObjects(List<T> objects);
    public <T> List<T> getAll(Class<T> tClass);
}
