package com.ha.app.repositories.impl;

import com.ha.app.data.DbContext;
import com.ha.app.data.DbSet;
import com.ha.app.entities.Item;
import com.ha.app.repositories.ItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

class ItemRepositoryImplTest {

    @InjectMocks
    private static ItemRepository itemRepository = new ItemRepositoryImpl();
    @Mock
    private static DbContext dbContext;
    @Mock
    private static DbSet<Item> itemDbSet;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(dbContext.getDbSetOf(Item.class)).thenReturn(itemDbSet);
        when(dbContext.getDbSetOf(Item.class).getAll()).thenReturn(new HashSet<>());

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
        Assertions.assertEquals(0, items.size());
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