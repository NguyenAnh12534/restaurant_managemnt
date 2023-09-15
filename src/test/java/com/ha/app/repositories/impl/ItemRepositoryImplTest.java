package com.ha.app.repositories.impl;

import com.ha.app.commons.depedencyinjection.ApplicationContext;
import com.ha.app.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class ItemRepositoryImplTest {
    private static ItemRepository itemRepository;
    @BeforeAll
    public void setup() {
        ApplicationContext applicationContext = new ApplicationContext();
        itemRepository = new ItemRepositoryImpl();
    }
    @Test
    void get() {

    }
}