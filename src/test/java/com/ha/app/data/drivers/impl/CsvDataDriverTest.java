package com.ha.app.data.drivers.impl;

import com.ha.app.entities.Item;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvDataDriverTest {

    @Test
    public void testSaveObject() {
        Item item = new Item(1,"new Item", 12);
        CsvDataDriver csvDataDriver = new CsvDataDriver();
        csvDataDriver.saveObject(item);
    }

    @Test
    public void testSaveAllObjects() {
        Item item1 = new Item(1,"new Item1", 12);
        Item item2 = new Item(1,"new Item2", 12);
        Item item3 = new Item(1,"new Item3", 12);
        List<Item> items = new ArrayList<>(Arrays.asList(item1, item2, item3));
        CsvDataDriver csvDataDriver = new CsvDataDriver();
        csvDataDriver.saveAllObjects(items);
    }

    @Test
    public void testAppendObject() {
        Item item = new Item(1,"new Item", 12);
        CsvDataDriver csvDataDriver = new CsvDataDriver();
        csvDataDriver.appendObject(item);
    }

    @Test
    public void testAppendAllObjects() {
        Item item1 = new Item(1,"new Item1", 12);
        Item item2 = new Item(1,"new Item2", 12);
        Item item3 = new Item(1,"new Item3", 12);
        List<Item> items = new ArrayList<>(Arrays.asList(item1, item2, item3));
        CsvDataDriver csvDataDriver = new CsvDataDriver();
        csvDataDriver.appendAllObjects(items);
    }

    @Test
    public void testReadAllObjects() {
        CsvDataDriver csvDataDriver = new CsvDataDriver();
        List<Item> items = csvDataDriver.getAll(Item.class);
        items.forEach(item -> {
            System.out.println(item.getId());
        });
    }
}