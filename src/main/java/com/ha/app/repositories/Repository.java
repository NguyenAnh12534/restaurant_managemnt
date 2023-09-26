package com.ha.app.repositories;

import com.ha.app.entities.Item;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface Repository<T> {
    public Optional<T> get(int id);
    Set<T> getAllByFields(Map<Field, Object> criteria);
    public Set<T> getAll();
    public void create(T item);
    public void update(T newItem);
    public void delete(int id);
}
