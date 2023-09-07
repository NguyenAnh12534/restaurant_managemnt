package com.ha.app.controllers;

import com.ha.app.annotations.Autowired;
import com.ha.app.annotations.Controller;
import com.ha.app.entities.Item;
import com.ha.app.ui.outputs.ItemConsoleView;
import com.ha.app.services.ItemService;

import java.util.List;

@Controller
public class ItemController{

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemConsoleView itemConsoleView;


    public void get(int id) {
        itemService.get(id);
    }

    public void getAll() {
        List<Item> items = itemService.getAll();
        itemConsoleView.viewAll(items);
    }

    public void create(Item item) {
        itemService.create(item);
    }

    public void update(Item oldItem, Item newItem) {

    }

    public void delete(int id) {

    }
}
