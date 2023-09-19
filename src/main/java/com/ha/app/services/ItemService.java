package com.ha.app.services;

import com.ha.app.entities.Item;

import java.util.List;
import java.util.Set;

public interface ItemService extends Service<Item>{
    public Item get(int id);
    public Set<Item> getAll();
    public void create(Item item);
    public void update(Item newItem, int oldItemId);
    public void delete(int id);
}
