package com.ha.app.services;

import com.ha.app.entities.Item;

import java.util.List;
import java.util.Set;

public interface Service<T> {
    public Item get(int id);
    public Set<T> getAll();
    public void create(T item);
    public void update(T newItem, int oldItemId);
    public void delete(int id);
}
