package com.ha.app.entities;

import com.ha.app.annotations.data.Entity;
import com.ha.app.annotations.data.Id;
import com.ha.app.annotations.data.ManyToOne;

@Entity
public class OrderItem {
    private Integer quantity;

    @ManyToOne
    private Order order;

    private int order_id;

    @ManyToOne
    private Item item;

    private int item_id;

    private Double historicalItemPrice;

    public OrderItem(Order order, Item item) {
        this.order = order;
        this.order.addOrderItem(this);
        this.item = item;
        this.order_id = this.order.getId();
        this.item_id = this.item.getId();
    }
    public OrderItem() {

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
        this.item_id = item.getId();
        this.item = item;
    }

    public void setOrder(Order order) {
        this.order = order;
        this.order_id = order.getId();
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setHistoricalItemPrice(Double historicalItemPrice) {
        this.historicalItemPrice = historicalItemPrice;
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
