package com.ha.app.data;

import com.ha.app.data.drivers.impl.CsvDataDriver;
import com.ha.app.entities.Menu;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class DbContextTest {



    @Test
    public void testCreateNewDbContext() {
        DbContext dbContext = new DbContext(new CsvDataDriver());
        DbSet<Menu> menuDbSet = dbContext.getDbSetOf(Menu.class);

        Assertions.assertTrue(menuDbSet != null);
    }

    public void tearDown() throws Exception {
    }
}