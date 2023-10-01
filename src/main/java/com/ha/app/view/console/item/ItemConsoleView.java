package com.ha.app.view.console.item;

import com.ha.app.annotations.Autowired;
import com.ha.app.annotations.ui.View;
import com.ha.app.annotations.ui.ViewFeature;
import com.ha.app.controllers.ItemController;
import com.ha.app.entities.Item;
import com.ha.app.exceptions.ApplicationException;
import com.ha.app.exceptions.ErrorInfo;
import com.ha.app.exceptions.InvalidInputException;
import com.ha.app.helpers.ClassHelper;
import com.ha.app.helpers.InputHelper;
import com.ha.app.view.console.ConsoleView;

import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@View
public class ItemConsoleView extends ConsoleView<ItemConsoleView> {

    @Autowired
    ItemController itemController;

    private InputHelper inputHelper = InputHelper.getInstance();

    public ItemConsoleView() {
        super(ItemConsoleView.class);
    }

    @ViewFeature
    public void viewItemById() {
        System.out.println("Please enter ID of the desired Item");
        int itemId;
        itemId = inputHelper.getInteger();

        Item selectedItem = itemController.get(itemId);
        System.out.println(selectedItem);
    }

    @ViewFeature
    public void viewAllItems() {
        Set<Item> items = itemController.getAll();
        System.out.println("All items are displayed as below: ");
        items.forEach(item -> {
            System.out.println(item);
        });
    }

    @ViewFeature
    public void viewAllItemsWithFilter() {
        boolean isChoosingFilter = true;
        List<Field> filterableField = ClassHelper.getFilterableField(Item.class);
        HashMap<Field, Object> criteria = new HashMap<>();
        while (isChoosingFilter) {
            System.out.println("Please choose a field to filter: ");
            for (int i = 0; i < filterableField.size(); i++) {
                System.out.println(i + 1 + ". " + filterableField.get(i).getName());
            }
            System.out.println("Choose index of the desired filter: ");
            int selectedFieldIndex = this.inputHelper.selectIndexOfCollection(filterableField) - 1;

            Field selectedField = filterableField.get(selectedFieldIndex);
            Object valueToFilterBy = inputHelper.getInputOfType(selectedField.getType());
            criteria.put(selectedField, valueToFilterBy);
            filterableField.remove(selectedField);
            isChoosingFilter = false;

            System.out.print("Do you want to add another filter (Y/N) - default is N: ");
            String continueOption = this.inputHelper.getLine();
            if (continueOption.trim().toLowerCase().equals("y")) {
                isChoosingFilter = true;
            }
        }
        Set<Item> filteredItems = this.itemController.getAllByFields(criteria);
        System.out.println("All filtered items are displayed as below: ");
        filteredItems.forEach(item -> {
            System.out.println(item);
        });
    }

    @ViewFeature
    public void createItem() {
        Item newItem = requireItemInputFromUser();
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
        this.viewAllItems();
        System.out.println("Please enter ID of the item to be updated: ");
        int oldItemId = inputHelper.getInteger();

        Item newItem = requireItemInputFromUser();

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
        this.viewAllItems();
        System.out.println("Please enter ID of the item to be deleted: ");
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

    private Item requireItemInputFromUser() {
        System.out.print("Please enter new name (press <Enter> to skip): ");
        String newName = inputHelper.getLine();

        System.out.print("Please enter new description (press <Enter> to skip): ");
        String newDescription = inputHelper.getLine();

        System.out.print("Please enter new image URL (press <Enter> to skip): ");
        String newImageURL = inputHelper.getLine();

        System.out.print("Please enter new additional detail (press <Enter> to skip): ");
        String newAdditionalDetail = inputHelper.getLine();

        System.out.println("Please enter new price (press <Enter> to skip): ");
        Double newPrice = inputHelper.getDouble();

        return Item.build()
                .setName(newName)
                .setDescription(newDescription)
                .setImageURL(newImageURL)
                .setAdditionalDetail(newAdditionalDetail)
                .setPrice(newPrice)
                .build();
    }

    @Override
    protected ItemConsoleView getCurrentView() {
        return this;
    }
}
