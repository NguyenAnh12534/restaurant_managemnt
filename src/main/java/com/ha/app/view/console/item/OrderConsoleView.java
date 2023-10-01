package com.ha.app.view.console.item;

import com.ha.app.annotations.Autowired;
import com.ha.app.annotations.ui.View;
import com.ha.app.annotations.ui.ViewFeature;
import com.ha.app.controllers.OrderController;
import com.ha.app.entities.Item;
import com.ha.app.entities.Order;
import com.ha.app.helpers.InputHelper;
import com.ha.app.view.console.ConsoleView;

import java.util.Set;

@View
public class OrderConsoleView extends ConsoleView<OrderConsoleView> {

    @Autowired
    private OrderController orderController;

    private InputHelper inputHelper = InputHelper.getInstance();

    public OrderConsoleView() {
        super(OrderConsoleView.class);
    }

    @ViewFeature
    public void viewAllOrder() {
        Set<Order> orders = this.orderController.getAll();
        orders.forEach(System.out::println);
    }

    @ViewFeature
    public void viewOrderById() {
        System.out.println("Please enter ID of the desired Order");
        int itemId;
        itemId = inputHelper.getInteger();

        Order selectedOrder = this.orderController.get(itemId);
        System.out.println(selectedOrder);
    }

    @ViewFeature
    public void createOrder() {

    }

    @ViewFeature
    public void updateOrder() {

    }


    @Override
    protected OrderConsoleView getCurrentView() {
        return null;
    }
}
