package com.ha.app.entities;

import com.ha.app.annotations.data.Entity;
import com.ha.app.annotations.data.ForeignKey;
import com.ha.app.annotations.data.ManyToOne;
import com.ha.app.entities.builders.OrderItemBuilder;

@Entity
public class OrderItem {
    private Integer quantity;

    @ManyToOne
    private  Order order;

    @ForeignKey(parentClass = Order.class)
    private int orderId;

    @ManyToOne
    private Item item;

    @ForeignKey(parentClass = Item.class)
    private int itemId;

    private Double historicalItemPrice;

    public OrderItem(Order order, Item item) {
        this.order = order;
        this.order.getOrderItems().add(this);
        this.item = item;
        this.orderId = this.order.getId();
        this.itemId = this.item.getId();
    }
    public OrderItem() {

    }

    public OrderItem(Order order, Item item, int quantity) {
        this.setOrder(order);
        this.setItem(item);
        this.quantity = quantity;

    }

    public Integer getQuantity() {
        return quantity;
    }

    public Order getOrder() {
        return order;
    }

    public Item getItem(){
        return this.item;
    }

    public Double getHistoricalItemPrice() {
        return historicalItemPrice;
    }

    public void setItem(Item item) {
        if(this.item != null)
            return;
        this.itemId = item.getId();
        this.item = item;
        this.historicalItemPrice = this.item.getPrice();
    }

    public void setOrder(Order order) {
        if(this.order != null)
            return;
        this.order = order;
        this.orderId = order.getId();
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public static OrderItemBuilder builder() {
        return new OrderItemBuilder();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Item name: " + this.item.getName() + "\n");
        stringBuilder.append("Price: " + this.historicalItemPrice + "\n");
        stringBuilder.append("Quantity: " + this.quantity + "\n");

        return stringBuilder.toString();
    }
}
