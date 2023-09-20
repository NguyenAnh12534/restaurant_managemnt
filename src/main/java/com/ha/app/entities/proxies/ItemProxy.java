package com.ha.app.entities.proxies;

import com.ha.app.data.DbContext;
import com.ha.app.entities.Item;

public class ItemProxy extends Item {
    private Item concreteItem;
    DbContext dbContext;

    public ItemProxy(DbContext dbContext) {
        this.dbContext  = dbContext;
    }


}
