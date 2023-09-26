package com.ha.app.entities;

import com.ha.app.annotations.data.Entity;
import com.ha.app.annotations.data.Id;
import com.ha.app.annotations.data.ManyToOne;

@Entity
public class OrderItem {
    @Id
    private Integer id;

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
        this.item = item;
        this.order_id = this.order.getId();
        this.item_id = this.item.getId();
    }

    public Integer getId() {
        return id;
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
}
