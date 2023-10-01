package com.ha.app.entities.builders;

import com.ha.app.entities.Item;
import com.ha.app.entities.Menu;
import com.ha.app.entities.Order;
import com.ha.app.entities.OrderItem;

public class OrderItemBuilder {
    private Item item;
    private Order order;
    private int quantity;

    OrderItem orderItem = new OrderItem();

    public OrderItemBuilder setItem(Item item) {
        this.item = item;
        return this;
    }

    public OrderItemBuilder setOrder(Order order) {
        this.order = order;
        return this;
    }


    public OrderItemBuilder setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderItem build() {

        return new OrderItem();
    }

}
