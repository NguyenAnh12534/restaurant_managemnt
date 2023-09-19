package com.ha.app.view.console.item;

import com.ha.app.annotations.Autowired;
import com.ha.app.annotations.ui.View;
import com.ha.app.annotations.ui.ViewFeature;
import com.ha.app.controllers.ItemController;
import com.ha.app.entities.Item;
import com.ha.app.exceptions.ApplicationException;
import com.ha.app.exceptions.ErrorInfo;
import com.ha.app.exceptions.ExitExcpetion;
import com.ha.app.helpers.InputHelper;
import com.ha.app.view.console.ConsoleView;

import java.util.List;
import java.util.Set;

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
        Set<Item> items = itemController.getAll();
        System.out.println("All items are displayed as below: ");
        items.forEach(item -> {
            System.out.println(item);
        });
    }

    @ViewFeature
    public void createItem() {
        System.out.print("Please enter ID of the item: ");
        int itemId = inputHelper.getInteger();

        System.out.print("Please enter name of the item: ");
        String itemName = inputHelper.getLine();

        System.out.print("Please enter price of the item: ");
        double itemPrice = inputHelper.getDouble();

        Item newItem = new Item(itemId, itemName, itemPrice);
        try {
            this.itemController.create(newItem);
            System.out.println("New item has been created");
        } catch (ApplicationException applicationException) {
            ErrorInfo errorInfo = new ErrorInfo();
            errorInfo.setErrorId("CreateNewItemView");

            applicationException.addErrorInfo(errorInfo);
            throw applicationException;
        }
    }

    @ViewFeature
    public void updateItem() {
        System.out.println("Please enter ID of the item to be updated: ");
        int oldItemId = inputHelper.getInteger();

        System.out.print("Please enter new name: ");
        String newName = inputHelper.getLine();

        System.out.println("Please enter new price: ");
        double newPrice = inputHelper.getDouble();

        Item newItem = new Item();
        newItem.setName(newName);
        newItem.setPrice(newPrice);

        try {
            this.itemController.update(newItem, oldItemId);
            System.out.println("Item has been updated");
        } catch (ApplicationException applicationException) {
            ErrorInfo errorInfo = new ErrorInfo();
            errorInfo.setErrorId("UpdateItemView");

            applicationException.addErrorInfo(errorInfo);
            throw applicationException;
        }
    }

    @ViewFeature
    public void deleteItem() {
        System.out.print("Please enter ID of the item to be deleted: ");
        int deleteItemId = inputHelper.getInteger();

        try {
            this.itemController.delete(deleteItemId);
            System.out.println("Item has been deleted");
        } catch (ApplicationException applicationException) {
            ErrorInfo errorInfo = new ErrorInfo();
            errorInfo.setErrorId("DeleteItemView");

            applicationException.addErrorInfo(errorInfo);
            throw applicationException;
        }
    }

    @Override
    protected ItemConsoleView getCurrentView() {
        return this;
    }

}
