package com.ha.app.services;

import com.ha.app.entities.Item;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ItemService extends Service<Item> {
    Item get(int id);

    Set<Item> getAll();
    Set<Item> getAllByFields(Map<Field, Object> criteria);

    void create(Item item);

    void update(Item newItem, int oldItemId);

    void delete(int id);
}
