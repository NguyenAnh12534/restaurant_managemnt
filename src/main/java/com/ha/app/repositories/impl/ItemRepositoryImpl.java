package com.ha.app.repositories.impl;

import com.ha.app.annotations.Component;
import com.ha.app.annotations.data.PersistenceContext;
import com.ha.app.data.DbContext;
import com.ha.app.data.drivers.DataDriver;
import com.ha.app.entities.Item;
import com.ha.app.entities.Menu;
import com.ha.app.enums.errors.ErrorSeverity;
import com.ha.app.enums.errors.ErrorType;
import com.ha.app.exceptions.ApplicationException;
import com.ha.app.exceptions.ErrorInfo;
import com.ha.app.repositories.ItemRepository;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Set;

@Component
public class ItemRepositoryImpl implements ItemRepository {
    @PersistenceContext
    private DbContext dbContext;

    @Override
    public Item get(int id) {
        try {
            Item item = dbContext.getDbSetOf(Item.class).findById(id);
            if (item != null) {
                Menu menu = dbContext.getDbSetOf(Menu.class).findById(id);
                if (menu != null)
                    item.setMenu(menu);
            }
            return item;
        } catch (ApplicationException exception) {
            ErrorInfo errorInfo = new ErrorInfo();

            errorInfo.setContextId(this.getClass().getSimpleName());
            errorInfo.setErrorId("GetOneItem");

            exception.addErrorInfo(errorInfo);

            throw exception;
        }
    }

    @Override
    public Set<Item> getAll() {
        Set<Item> items = dbContext.getDbSetOf(Item.class).getAll();
        return items;
    }

    @Override
    public void create(Item item) {
        dbContext.getDbSetOf(Item.class).create(item);
    }

    @Override
    public void update(Item newItem, int oldItemId) {
        Item oldItem = this.dbContext.getDbSetOf(Item.class).findById(oldItemId);
        oldItem.setName(newItem.getName());
        oldItem.setPrice(newItem.getPrice());

        this.dbContext.getDbSetOf(Item.class).flush();
    }

    @Override
    public void delete(int id) {
        this.dbContext.getDbSetOf(Item.class).deleteById(id);
        this.dbContext.flush();
    }

    @Override
    public boolean isExisted(int itemId) {
        return dbContext.getDbSetOf(Item.class).findById(itemId) != null;
    }

    public ItemRepositoryImpl() {

    }

    public ItemRepositoryImpl(DbContext dbContext) {
        this.dbContext = dbContext;
    }
}
