package com.ha.app.services.impl;

import com.ha.app.annotations.Autowired;
import com.ha.app.annotations.Component;
import com.ha.app.entities.Item;
import com.ha.app.repositories.ItemRepository;
import com.ha.app.services.ItemService;

import java.util.List;

@Component
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public void get(int id) {
        this.itemRepository.get(id);
    }

    @Override
    public List<Item> getAll() {
        return itemRepository.getAll();
    }

    @Override
    public void create(Item item) {
        this.itemRepository.create(item);
    }

    @Override
    public void update(Item oldItem, Item newItem) {

    }

    @Override
    public void delete(int id) {

    }
}
