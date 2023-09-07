package com.ha.app.ui.outputs;

import com.ha.app.entities.Item;

import java.util.List;

public interface ItemConsoleView {
    public void viewOne(Item item);
    public void viewAll(List<Item> items);
}
