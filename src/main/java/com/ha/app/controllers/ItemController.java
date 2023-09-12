package com.ha.app.controllers;

import com.ha.app.annotations.Autowired;
import com.ha.app.annotations.Controller;
import com.ha.app.entities.Item;
import com.ha.app.services.impl.ItemServiceImpl;
import com.ha.app.ui.outputs.ItemConsoleView;
import com.ha.app.services.ItemService;

import java.util.List;

@Controller
public class ItemController{

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemConsoleView itemConsoleView;


    public Item get(int id) {
        return itemService.get(id);
    }

    public List<Item> getAll() {
        List<Item> items = itemService.getAll();

        return items;
    }

    public void create(Item item) {
        itemService.create(item);
    }

    public void update(Item oldItem, Item newItem) {
        System.out.println("Old item: " + oldItem);
        System.out.println("New item: " + newItem);
    }

    public void delete(int id) {

    }
}
