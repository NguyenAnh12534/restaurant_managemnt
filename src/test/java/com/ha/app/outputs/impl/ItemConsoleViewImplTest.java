package com.ha.app.outputs.impl;

import com.ha.app.entities.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ItemConsoleViewImplTest {

    private static List<Item> items;
    @BeforeEach
    void setUp() {
        Item item1 = new Item(1, "Hoang Anh", 12.0);
        Item item2 = new Item(2, "Hoang Em", 24.0);
        items = new ArrayList<>(Arrays.asList(item1, item2));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void viewOne() {
    }

    @Test
    void viewAll() {
    }
}