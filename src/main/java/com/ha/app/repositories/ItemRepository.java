package com.ha.app.repositories;

import com.ha.app.entities.Item;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface ItemRepository extends Repository<Item>{
    public Optional<Item> get(int id);
    Set<Item> getAllByFields(Map<Field, Object> criteria);
    public Set<Item> getAll();
    public void create(Item item);
    public void update(Item newItem);
    public void delete(int id);
    public boolean isExisted(int itemId);
}
