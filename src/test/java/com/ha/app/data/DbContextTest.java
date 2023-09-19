package com.ha.app.data;

import com.ha.app.data.drivers.impl.CsvDataDriver;
import com.ha.app.entities.Item;
import com.ha.app.entities.Menu;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Set;


public class DbContextTest {
    static DbContext dbContext;
    @BeforeAll
    public static void setup() {
        dbContext  = new DbContext(new CsvDataDriver());
    }

    @Test
    public void testCreateNewDbContext() {

        DbSet<Menu> menuDbSet = dbContext.getDbSetOf(Menu.class);
        Assertions.assertTrue(menuDbSet != null);
    }

    @Test
    public void testEagerLoading() throws NoSuchFieldException, IllegalAccessException {
        Set<Item> items = dbContext.getDbSetOf(Item.class).getAll();
        Field parentField = Item.class.getDeclaredField("menu");
        for (Item item : items) {
            dbContext.eagerLoadDataForField(parentField, item);
            System.out.println(item);
        }


    }

    public void tearDown() throws Exception {
    }
}