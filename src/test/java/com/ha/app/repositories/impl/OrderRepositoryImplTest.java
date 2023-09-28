package com.ha.app.repositories.impl;

import com.ha.app.data.DbContext;
import com.ha.app.data.drivers.DataDriver;
import com.ha.app.data.drivers.impl.CsvDataDriver;
import com.ha.app.entities.Item;
import com.ha.app.entities.Order;
import com.ha.app.entities.OrderItem;
import com.ha.app.repositories.ItemRepository;
import com.ha.app.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.Set;

class OrderRepositoryImplTest {
    private static OrderRepository orderRepository;
    private static ItemRepository itemRepository;
    private static DbContext dbContext;


    @BeforeEach
    public void setup() {
        DataDriver dataDriver = new CsvDataDriver();
        dbContext = new DbContext(dataDriver);
        orderRepository = new OrderRepositoryImpl(dbContext);
        itemRepository = new ItemRepositoryImpl(dbContext);
    }
    @Test
    void get() {
        int id = 1;
        System.out.println(orderRepository.get(1).get());
    }

    @Test
    void getAllByFields() {
    }

    @Test
    void getAll() {
        Set<Order> orders = orderRepository.getAll();
        orders.forEach(order -> {
            System.out.println(order);
        });
    }

    @Test
    void create() {
        Order order = new Order();
        order.setCreateAt(new Date());
        order.setUpdatedAt(new Date());

        Item item = itemRepository.get(1).get();

        OrderItem orderItem = new OrderItem(order, item);
        orderItem.setQuantity(2);
        orderItem.setHistoricalItemPrice(12.0);

        orderRepository.create(order);
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void isExisted() {
    }
}