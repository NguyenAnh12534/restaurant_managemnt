package com.ha.app.services;

import com.ha.app.entities.Order;
import com.ha.app.entities.OrderItem;

public interface OrderService extends Service<Order>{
    void addOrderItem(OrderItem orderItem, int orderId);
}
