package com.ha.app.repositories.impl;

import com.ha.app.annotations.Autowired;
import com.ha.app.annotations.Component;
import com.ha.app.annotations.data.PersistenceContext;
import com.ha.app.data.DbContext;
import com.ha.app.data.DbSet;
import com.ha.app.entities.Item;
import com.ha.app.entities.Order;
import com.ha.app.entities.OrderItem;
import com.ha.app.exceptions.ApplicationException;
import com.ha.app.exceptions.ErrorInfo;
import com.ha.app.repositories.OrderItemRepository;
import com.ha.app.repositories.OrderRepository;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Component
public class OrderRepositoryImpl implements OrderRepository {

    @PersistenceContext
    DbContext dbContext;

    @Autowired
    OrderItemRepository orderItemRepository;


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
        DbSet<OrderItem> orderItemDbSet = this.dbContext.getDbSetOf(OrderItem.class);
        order.getOrderItems().forEach(orderItem -> {
            orderItemDbSet.create(orderItem);
        });
    }

    @Override
    public void update(Order newOrder, int oldOrderId) {
        Order oldOrder = this.dbContext.getDbSetOf(Order.class).findById(oldOrderId);
        oldOrder.load(newOrder);

        this.dbContext.getDbSetOf(Order.class).flush();
    }

    @Override
    public void delete(int id) {
        this.dbContext.getDbSetOf(Order.class).deleteById(id);
    }

    @Override
    public boolean isExisted(int id) {
        return this.dbContext.getDbSetOf(Order.class).findById(id) != null;
    }

    private int generateNextId() {
        Set<Order> items = this.getAll();
        if (items.isEmpty())
            return 1;
        return items.stream().map(item -> item.getId()).reduce((currentId, nextId) -> {
            return currentId > nextId ? currentId : nextId;
        }).get() + 1;
    }
}
