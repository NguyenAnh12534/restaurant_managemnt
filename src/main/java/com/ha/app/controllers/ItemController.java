package com.ha.app.controllers;

import com.ha.app.annotations.Autowired;
import com.ha.app.annotations.Controller;
import com.ha.app.entities.Item;
import com.ha.app.services.ItemService;

import java.util.List;
import java.util.Set;

@Controller
public class ItemController{

    @Autowired
    private ItemService itemService;

    public Item get(int id) {
        return itemService.get(id);
    }

    public Set<Item> getAll() {
        Set<Item> items = itemService.getAll();
        return items;
    }

    public void create(Item item) {
        itemService.create(item);
    }

    public void update(Item newItem, int oldItemId) {
        this.itemService.update(newItem, oldItemId);;
    }

    public void delete(int id) {
        this.itemService.delete(id);
    }
}
