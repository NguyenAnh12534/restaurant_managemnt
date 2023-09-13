package com.ha.app.repositories;

import com.ha.app.entities.Item;

import java.util.List;

public interface ItemRepository extends Repository<Item>{
    public Item get(int id);
    public List<Item> getAll();
    public void create(Item item);
    public void update(Item oldItem, Item newItem);
    public void delete(int id);
}
