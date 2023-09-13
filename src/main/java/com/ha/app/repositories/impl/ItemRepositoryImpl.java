package com.ha.app.repositories.impl;

import com.ha.app.annotations.Component;
import com.ha.app.annotations.data.PersistenceContext;
import com.ha.app.data.DbContext;
import com.ha.app.entities.Item;
import com.ha.app.exceptions.ApplicationException;
import com.ha.app.exceptions.ErrorInfo;
import com.ha.app.repositories.ItemRepository;

import java.util.List;

@Component
public class ItemRepositoryImpl implements ItemRepository {
    @PersistenceContext
    private DbContext dbContext;

    @Override
    public Item get(int id) {
        try {
            return dbContext.getDbSetOf(Item.class).filterByField("id", id).getOne();
        } catch (ApplicationException exception) {
            ErrorInfo errorInfo = new ErrorInfo();

            errorInfo.setContextId(this.getClass().getSimpleName());
            errorInfo.setErrorId("GetOneItem");

            exception.addErrorInfo(errorInfo);

            throw exception;
        }
    }

    @Override
    public List<Item> getAll() {
        List<Item> items = dbContext.getDbSetOf(Item.class).getAll();
        return items;
    }

    @Override
    public void create(Item item) {
        dbContext.getDbSetOf(Item.class).create(item);
    }

    @Override
    public void update(Item oldItem, Item newItem) {

    }

    @Override
    public void delete(int id) {

    }
}
