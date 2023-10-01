package com.ha.app.services.impl;

import com.ha.app.annotations.Autowired;
import com.ha.app.annotations.Component;
import com.ha.app.entities.Order;
import com.ha.app.entities.OrderItem;
import com.ha.app.exceptions.NotFoundException;
import com.ha.app.repositories.ItemRepository;
import com.ha.app.repositories.OrderRepository;
import com.ha.app.services.ItemService;
import com.ha.app.services.OrderService;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Override
    public Order get(int id) {
        Optional<Order> orderOptional = this.orderRepository.get(id);
        try {
            return orderOptional.orElseThrow();
        } catch (NoSuchElementException exception) {
            NotFoundException notFoundException = new NotFoundException();
            notFoundException.setContext("GetOneOrder");
            notFoundException.addParameter("id", id);
            throw notFoundException;
        }
    }

    @Override
    public Set<Order> getAll() {
        return this.orderRepository.getAll();
    }

    @Override
    public void create(Order order) {
        this.orderRepository.create(order);
    }

    @Override
    public void update(Order newOrder, int oldOrderId) {
        if (!this.orderRepository.isExisted(oldOrderId)) {
            NotFoundException notFoundException = new NotFoundException();
            notFoundException.setContext("UpdatingOrder");
            notFoundException.addParameter("id", oldOrderId);

            throw notFoundException;
        }

        this.orderRepository.update(newOrder, oldOrderId);
    }

    @Override
    public void delete(int id) {
        if (this.orderRepository.isExisted(id)) {
            if (!this.orderRepository.isExisted(id)) {
                NotFoundException notFoundException = new NotFoundException();
                notFoundException.setContext("DeletingOrder");
                notFoundException.addParameter("id", id);

                throw notFoundException;
            }
        }
        this.orderRepository.delete(id);
    }

    @Override
    public void addOrderItem(OrderItem orderItem, int orderId) {
        Optional<Order> orderOptional = this.orderRepository.get(orderId);
        Order order;
        try {
            order = orderOptional.orElseThrow();
            if(this.itemRepository.isExisted(orderItem.getItem().getId())) {
                NotFoundException notFoundException = new NotFoundException();
                notFoundException.setContext("AddingNewOrderItem");
                notFoundException.addParameter("itemId", orderItem.getItem().getId());

                throw notFoundException;
            }

            this.orderRepository.update(order, orderId);
        } catch (NoSuchElementException noSuchElementException) {
            NotFoundException notFoundException = new NotFoundException();
            notFoundException.setContext("AddingNewOrderItem");
            notFoundException.addParameter("orderId", orderId);

            throw notFoundException;
        }
    }
}
