package com.ha.app.data.drivers.impl;

import com.ha.app.data.drivers.DataDriver;
import com.ha.app.entities.Item;
import com.ha.app.entities.Menu;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvDataDriverTest {

    @Test
    public void testSaveObject() {
        Item item = new Item(1,"new Item", 12.0);
        CsvDataDriver csvDataDriver = new CsvDataDriver();
        csvDataDriver.saveObject(item);
    }

    @Test
    public void testSaveAllObjects() {
        Item item1 = new Item(1,"new Item1", 12.0);
        Item item2 = new Item(1,"new Item2", 12.0);
        Item item3 = new Item(1,"new Item3", 12.0);
        List<Item> items = new ArrayList<>(Arrays.asList(item1, item2, item3));
        CsvDataDriver csvDataDriver = new CsvDataDriver();
        csvDataDriver.saveAllObjects(items);
    }

    @Test
    public void testAppendObject() {
        Item item = new Item(1,"new Item", 12.0);
        CsvDataDriver csvDataDriver = new CsvDataDriver();
        csvDataDriver.appendObject(item);
    }

    @Test
    public void testAppendAllObjects() {
        Item item1 = new Item(1,"new Item1", 12.0);
        Item item2 = new Item(1,"new Item2", 12.0);
        Item item3 = new Item(1,"new Item3", 12.0);
        List<Item> items = new ArrayList<>(Arrays.asList(item1, item2, item3));
        CsvDataDriver csvDataDriver = new CsvDataDriver();
        csvDataDriver.appendAllObjects(items);
    }

    @Test
    public void testReadAllObjects() {
        CsvDataDriver csvDataDriver = new CsvDataDriver();
        List<Item> items = csvDataDriver.getAll(Item.class);
        items.forEach(item -> {
            System.out.println(item.menu_id);
        });
    }

    @Test
    public void testSaveMenuThatHasItems() {
        Menu menu = new Menu();

        menu.setId(1);
        menu.setName("test saving Save Menu That Has Items");

        Item item1 = new Item(1, "test item 1", 12.0);
        Item item2 = new Item(2, "test item 2", 24.0);

        menu.getItems().add(item1);
        menu.getItems().add(item2);

        DataDriver dataDriver = new CsvDataDriver();
        dataDriver.saveObject(menu);
    }

    @Test
    public void testSaveItemThatBelongToAMenu() {
        Menu menu = new Menu();

        menu.setId(2);
        menu.setName("test saving Item That Belong To A Menu");

        Item item1 = new Item(1, "test item 1", 12.0);
        Item item2 = new Item(2, "test item 2", 24.0);
        item1.setMenu(menu);

        List<Item> items = new ArrayList<>(Arrays.asList(item1, item2));
        DataDriver dataDriver = new CsvDataDriver();
        dataDriver.saveAllObjects(items);
    }
}