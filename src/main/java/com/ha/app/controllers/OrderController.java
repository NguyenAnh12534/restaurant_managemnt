package com.ha.app.controllers;

import com.ha.app.annotations.Autowired;
import com.ha.app.annotations.Controller;
import com.ha.app.entities.Order;
import com.ha.app.services.OrderService;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    public Order get(int id) {
        return orderService.get(id);
    }

    public Set<Order> getAll() {
        Set<Order> orders = this.orderService.getAll();
        return orders;
    }

    public void create(Order item) {
        this.orderService.create(item);
    }

    public void update(Order newItem, int oldItemId) {
        this.orderService.update(newItem, oldItemId);;
    }

    public void delete(int id) {
        this.orderService.delete(id);
    }
}
