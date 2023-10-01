package com.ha.app.entities;

import com.ha.app.annotations.data.Entity;
import com.ha.app.annotations.data.Id;
import com.ha.app.annotations.data.OneToMany;
import com.ha.app.entities.builders.OrderBuilder;
import com.ha.app.enums.status.OrderStatus;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Order {
    @Id
    private int id;

    private OrderStatus orderStatus = OrderStatus.UN_PAID;

    @OneToMany(mappedBy = "order", childEntity = OrderItem.class)
    Set<OrderItem> orderItems = new HashSet<>();

    @CsvBindByName(column = "createAt")
    @CsvDate("dd-MM-yyyy")
    private Date createAt;

    @CsvBindByName(column = "updatedAt")
    @CsvDate("dd-MM-yyyy")
    private Date updatedAt;

    public Order() {
        this.createAt =  new Date();
        this.updatedAt = new Date();
    }

    public Order(OrderStatus orderStatus, Set<OrderItem> orderItems) {
        this();
        this.orderStatus = orderStatus;
        this.orderItems = orderItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        this.orderItems.forEach(orderItem -> orderItem.setOrder(this));
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }


    public void load(Order newOrder) {
        this.orderStatus = newOrder.getOrderStatus() != null ? newOrder.getOrderStatus() : this.orderStatus;
        this.orderItems = newOrder.getOrderItems() != null ? newOrder.getOrderItems() : this.orderItems;

        this.updatedAt = new Date();
    }

    public OrderBuilder builder() {
        return new OrderBuilder();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Order ID: " + this.id + "\n");
        stringBuilder.append("Order Status: " + this.orderStatus + "\n");
        stringBuilder.append("Created Date: " + this.createAt + "\n");
        stringBuilder.append("Updated Date: " + this.updatedAt + "\n");
        if (!this.orderItems.isEmpty()) {
            stringBuilder.append("All order items: \n");
            for (OrderItem orderItem : this.orderItems) {
                stringBuilder.append(orderItem);
            }
        }

        return stringBuilder.toString();
    }
}
