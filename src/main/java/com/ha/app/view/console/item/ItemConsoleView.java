package com.ha.app.view.console.item;

import com.ha.app.annotations.Autowired;
import com.ha.app.annotations.ui.View;
import com.ha.app.annotations.ui.ViewFeature;
import com.ha.app.controllers.ItemController;
import com.ha.app.view.console.ConsoleView;

import java.lang.reflect.Method;

@View
public class ItemConsoleView extends ConsoleView {

    @Autowired
    ItemController itemController;

    public ItemConsoleView() {
        super(ItemConsoleView.class);
    }

    @ViewFeature
    public void getItemById() {
        System.out.println("Getting an item by ID");
    }

    @ViewFeature
    public void getAllItem() {
        itemController.getAll();
    }

    @ViewFeature
    public void createItem() {

    }

    @ViewFeature
    public void updateItem() {

    }

    @ViewFeature
    public void deleteItem() {

    }

    @Override
    protected ItemConsoleView getCurrentView() {
        return this;
    }

}
