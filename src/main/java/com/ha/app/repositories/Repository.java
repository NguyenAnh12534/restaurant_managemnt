package com.ha.app.repositories;

import java.util.List;
import java.util.Set;

public interface Repository<T> {
    public T get(int id);
    public Set<T> getAll();
    public void create(T item);
    public void update(T newItem, int oldItemId);
    public void delete(int id);
}
