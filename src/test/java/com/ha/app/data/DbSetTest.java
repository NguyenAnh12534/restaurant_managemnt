package com.ha.app.data;

import com.ha.app.data.drivers.DataDriver;
import com.ha.app.data.drivers.impl.CsvDataDriver;

import com.ha.app.entities.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DbSetTest {
    private static DbContext dbContext;

    @BeforeAll
    public static void setup() {
        System.out.println("Setting up test");
        DataDriver dataDriver = new CsvDataDriver();
        dbContext = new DbContext(dataDriver);
    }

    @Test
    public void testDeepCopyArrayList() {
        List<Integer> originalList = new ArrayList<>(Arrays.asList(
                1, 2, 3
        ));

        List<Integer> newList = new ArrayList<>(originalList);
        newList.add(5);
        Assertions.assertNotEquals(newList.size(), originalList.size());
    }


    @Test
    public void testFilterByField() {
        DbSet<Item> dbSet = dbContext.getDbSetOf(Item.class);
        System.out.println(dbSet.getOne());
    }
}