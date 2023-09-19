package com.ha.app.repositories.impl;

import com.ha.app.commons.depedencyinjection.ApplicationContext;
import com.ha.app.data.DbContext;
import com.ha.app.data.drivers.DataDriver;
import com.ha.app.data.drivers.impl.CsvDataDriver;
import com.ha.app.entities.Item;
import com.ha.app.repositories.ItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
class ItemRepositoryImplTest {
    private static ItemRepository itemRepository;
    @BeforeAll
    public static void setup() {
        DataDriver dataDriver = new CsvDataDriver();
        DbContext dbContext = new DbContext(dataDriver);
        itemRepository = new ItemRepositoryImpl(dbContext);
    }
    @Test
    void testCreateNewItem() {
        Item item = new Item();

        item.setId(3);
        item.setName("test item 123");
        item.setPrice(12);

        itemRepository.create(item);
    }
    @Test
    void testGetItemById() {
//        int selectedId = 3;
//
//        Item selectedItem = itemRepository.get(selectedId);
//
//        Assertions.assertNotNull(selectedItem);
//        Assertions.assertEquals(selectedId, selectedItem.getId());
    }

    @Test
    void testGetAllItem() {
        Set<Item> items = itemRepository.getAll();
        System.out.println(items.size());
    }



    @Test
    void testUpdateItem() {
//        Item itemToUpdate = new Item();
//        itemToUpdate.setName("New Name");
//        itemToUpdate.setPrice(123);
//
//        itemRepository.update(itemToUpdate, 3);
//
//        Item updatedItem = itemRepository.get(3);
//        Assertions.assertEquals(updatedItem.getName(), updatedItem.getName());
    }

    @Test
    void testDeleteItem() {
//        int idToDelete = 1;
//
//        itemRepository.delete(idToDelete);
//
//        Item updatedItem = itemRepository.get(idToDelete);
//        Assertions.assertNull(updatedItem);
    }


}