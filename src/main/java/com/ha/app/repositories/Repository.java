package com.ha.app.repositories;

import java.util.List;

public interface Repository<T> {
    public void get(int id);
    public List<T> getAll();
    public void create(T item);
    public void update(T oldItem, T newItem);
    public void delete(int id);
}
