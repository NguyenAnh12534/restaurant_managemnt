package com.ha.app.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface Repository<T> {
    public Optional<T> get(int id);
    public Set<T> getAll();
    public void create(T item);
    public void update(T newItem);
    public void delete(int id);
}
