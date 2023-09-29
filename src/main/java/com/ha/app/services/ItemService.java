package com.ha.app.services;

import com.ha.app.entities.Item;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ItemService extends Service<Item> {
    Set<Item> getAllByFields(Map<Field, Object> criteria);
}
