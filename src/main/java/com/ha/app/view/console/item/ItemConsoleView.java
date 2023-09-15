package com.ha.app.view.console.item;

import com.ha.app.annotations.Autowired;
import com.ha.app.annotations.ui.View;
import com.ha.app.annotations.ui.ViewFeature;
import com.ha.app.controllers.ItemController;
import com.ha.app.entities.Item;
import com.ha.app.exceptions.ApplicationException;
import com.ha.app.exceptions.ExitExcpetion;
import com.ha.app.helpers.InputHelper;
import com.ha.app.view.console.ConsoleView;

import java.util.List;

@View
public class ItemConsoleView extends ConsoleView {

    @Autowired
    ItemController itemController;

    private InputHelper inputHelper = InputHelper.getInstance();

    public ItemConsoleView() {
        super(ItemConsoleView.class);
    }

    @ViewFeature
    public void getItemById() {
        System.out.println("Please enter ID of the desired Item");
        int itemId;
        itemId = inputHelper.getInteger();

        Item selectedItem = itemController.get(itemId);
        System.out.println(selectedItem);
    }

    @ViewFeature
    public void getAllItem() {
        List<Item> items = itemController.getAll();
        System.out.println("All items are displayed as below: ");
        items.forEach(item -> {
            System.out.println(item);
        });
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
