package com.ha.app.controllers;

import com.ha.app.annotations.Autowired;
import com.ha.app.annotations.Controller;
import com.ha.app.entities.Item;
import com.ha.app.services.ItemService;
import com.ha.app.services.impl.ItemServiceImpl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class ItemController{

    @Autowired
    private ItemService itemService;

    public Item get(int id) {
        return itemService.get(id);
    }

    public Set<Item> getAll() {
        Set<Item> items = this.itemService.getAll();
        return items;
    }

    public Set<Item> getAllByFields(Map<Field, Object> criteria) {
        return this.itemService.getAllByFields(criteria);
    }

    public void create(Item item) {
        this.itemService.create(item);
    }

    public void update(Item newItem, int oldItemId) {
        this.itemService.update(newItem, oldItemId);;
    }

    public void delete(int id) {
        this.itemService.delete(id);
    }
}
