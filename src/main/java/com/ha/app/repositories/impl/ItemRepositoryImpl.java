package com.ha.app.repositories.impl;

import com.ha.app.annotations.Autowired;
import com.ha.app.annotations.Component;
import com.ha.app.annotations.data_annotations.PersistenceContext;
import com.ha.app.data.DbContext;
import com.ha.app.data.DbSet;
import com.ha.app.entities.Item;
import com.ha.app.repositories.ItemRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class ItemRepositoryImpl implements ItemRepository {
    @PersistenceContext
    private DbContext dbContext;

    @Override
    public void get(int id) {
        System.out.println("getting id: " + id);
    }

    @Override
    public List<Item> getAll() {
        List<Item> items = dbContext.getDbSetOf(Item.class).getAll();
        return items;
    }

    @Override
    public void create(Item item) {
        dbContext.getDbSetOf(Item.class).create(item);
    }

    @Override
    public void update(Item oldItem, Item newItem) {

    }

    @Override
    public void delete(int id) {

    }
}
