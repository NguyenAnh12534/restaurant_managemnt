package com.ha.app.ui.outputs.impl;

import com.ha.app.annotations.Component;
import com.ha.app.entities.Item;
import com.ha.app.ui.outputs.ItemConsoleView;

import java.util.List;

@Component
public class ItemConsoleViewImpl implements ItemConsoleView {
    @Override
    public void viewOne(Item item) {
        System.out.println(item);
    }

    @Override
    public void viewAll(List<Item> items) {
        if(items.isEmpty()) {
            System.out.println("No item is found");
            return;
        }
        System.out.println("\nAll items: ");
        items.forEach(item -> {
            System.out.println(item);
        });
    }
}
