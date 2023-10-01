package com.ha.app.entities.builders;

import com.ha.app.entities.Item;
import com.ha.app.entities.Menu;
import com.ha.app.entities.Order;
import com.ha.app.entities.OrderItem;
import com.ha.app.enums.status.OrderStatus;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class OrderBuilder {
    private OrderStatus orderStatus;
    private Set<OrderItem> orderItems = new HashSet<>();
    public OrderBuilder setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public OrderBuilder setOrderItems(Set<OrderItem> orderItems) {
        this.orderStatus = orderStatus;
        return this;
    }

    public OrderBuilder addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        return this;
    }

    public Order build(){
        Order order = new Order(this.orderStatus, this.orderItems);
        return order;
    }
}
