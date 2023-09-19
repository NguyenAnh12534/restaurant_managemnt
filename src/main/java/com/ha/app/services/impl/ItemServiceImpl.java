package com.ha.app.services.impl;

import com.ha.app.annotations.Autowired;
import com.ha.app.annotations.Component;
import com.ha.app.entities.Item;
import com.ha.app.repositories.ItemRepository;
import com.ha.app.services.ItemService;

import java.util.List;
import java.util.Set;

@Component
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Item get(int id) {

        return  this.itemRepository.get(id);
    }

    @Override
    public Set<Item> getAll() {
        return itemRepository.getAll();
    }

    @Override
    public void create(Item item) {
        this.itemRepository.create(item);
    }

    @Override
    public void update(Item newItem, int oldItemId) {
        if(this.itemRepository.isExisted(oldItemId))
            this.itemRepository.update(newItem, oldItemId);
    }

    @Override
    public void delete(int id) {
        if(this.itemRepository.isExisted(id))
            this.itemRepository.delete(id);
    }
}
