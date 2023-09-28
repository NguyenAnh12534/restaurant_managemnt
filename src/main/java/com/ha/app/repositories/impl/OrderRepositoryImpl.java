package com.ha.app.repositories.impl;

import com.ha.app.annotations.Component;
import com.ha.app.annotations.data.PersistenceContext;
import com.ha.app.data.DbContext;
import com.ha.app.entities.Item;
import com.ha.app.entities.Order;
import com.ha.app.entities.OrderItem;
import com.ha.app.exceptions.ApplicationException;
import com.ha.app.exceptions.ErrorInfo;
import com.ha.app.repositories.OrderRepository;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Component
public class OrderRepositoryImpl implements OrderRepository {

    @PersistenceContext
    DbContext dbContext;

    private int nextId = -1;

    public OrderRepositoryImpl(){

    }

    public  OrderRepositoryImpl(DbContext dbContext) {
        this.dbContext = dbContext;
    }
    @Override
    public Optional<Order> get(int id) {
        try {
            Order order = dbContext.getDbSetOf(Order.class).findById(id);
            return Optional.ofNullable(order);
        } catch (ApplicationException exception) {
            ErrorInfo errorInfo = new ErrorInfo();

            errorInfo.setContextId(this.getClass().getSimpleName());
            errorInfo.setErrorId("GetOneOrder");

            exception.addErrorInfo(errorInfo);

            throw exception;
        }
    }

    @Override
    public Set<Order> getAllByFields(Map<Field, Object> criteria) {
        return null;
    }

    @Override
    public Set<Order> getAll() {
        Set<Order> orders = dbContext.getDbSetOf(Order.class).getAll();
        return orders;
    }

    @Override
    public void create(Order order) {
        if(this.nextId < 0) {
            this.nextId = this.generateNextId();
        } else {
            this.nextId++;
        }
        order.setId(nextId);
        dbContext.getDbSetOf(Order.class).create(order);
        order.getOrderItems().forEach(orderItem -> {
            this.dbContext.getDbSetOf(OrderItem.class).create(orderItem);
        });
    }

    @Override
    public void update(Order newItem) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public boolean isExisted(int id) {
        return false;
    }

    private int generateNextId() {
        Set<Order> items = this.getAll();
        if (items.isEmpty())
            return 1;
        return items.stream().reduce(null, (nextId, item) -> {
            return nextId.getId() < item.getId()? item : nextId;
        }).getId();
    }
}
