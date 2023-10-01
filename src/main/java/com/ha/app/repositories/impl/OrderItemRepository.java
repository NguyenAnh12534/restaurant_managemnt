package com.ha.app.repositories.impl;

import com.ha.app.annotations.data.PersistenceContext;
import com.ha.app.data.DbContext;
import com.ha.app.entities.Order;
import com.ha.app.repositories.OrderRepository;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class OrderItemRepository implements OrderRepository {
    @PersistenceContext
    DbContext dbContext;

    @Override
    public Optional<Order> get(int id) {
        return Optional.empty();
    }

    @Override
    public Set<Order> getAllByFields(Map<Field, Object> criteria) {
        return null;
    }

    @Override
    public Set<Order> getAll() {
        return null;
    }

    @Override
    public void create(Order item) {

    }

    @Override
    public void update(Order newItem, int oldItemId) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public boolean isExisted(int id) {
        return false;
    }
}
