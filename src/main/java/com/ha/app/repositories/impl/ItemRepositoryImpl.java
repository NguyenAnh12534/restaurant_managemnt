package com.ha.app.repositories.impl;

import com.ha.app.annotations.Component;
import com.ha.app.annotations.data.PersistenceContext;
import com.ha.app.data.DbContext;
import com.ha.app.data.DbSet;
import com.ha.app.data.drivers.DataDriver;
import com.ha.app.entities.Item;
import com.ha.app.entities.Menu;
import com.ha.app.enums.errors.ErrorSeverity;
import com.ha.app.enums.errors.ErrorType;
import com.ha.app.exceptions.ApplicationException;
import com.ha.app.exceptions.ErrorInfo;
import com.ha.app.repositories.ItemRepository;

import javax.xml.crypto.Data;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Component
public class ItemRepositoryImpl implements ItemRepository {
    @PersistenceContext
    private DbContext dbContext;
    private int nextId = -1;
    public ItemRepositoryImpl() {

    }
    public ItemRepositoryImpl(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    @Override
    public Optional<Item> get(int id) {
        try {
            Item item = dbContext.getDbSetOf(Item.class).findById(id);
            return Optional.ofNullable(item);
        } catch (ApplicationException exception) {
            ErrorInfo errorInfo = new ErrorInfo();

            errorInfo.setContextId(this.getClass().getSimpleName());
            errorInfo.setErrorId("GetOneItem");

            exception.addErrorInfo(errorInfo);

            throw exception;
        }
    }

    @Override
    public Set<Item> getAllByFields(Map<Field, Object> criteria) {
        DbSet<Item> itemDbSet = this.dbContext.getDbSetOf(Item.class);
        for (Map.Entry<Field, Object> filter : criteria.entrySet()) {
            itemDbSet = itemDbSet.filterByField(filter.getKey().getName(), filter.getValue());
        }

        return itemDbSet.getAll();
    }

    @Override
    public Set<Item> getAll() {
        Set<Item> items = dbContext.getDbSetOf(Item.class).getAll();
        return items;
    }

    @Override
    public void create(Item item) {
        if(this.nextId < 0) {
            this.nextId = this.generateNextId();
        } else {
            this.nextId++;
        }
        item.setId(nextId);
        dbContext.getDbSetOf(Item.class).create(item);
    }

    @Override
    public void update(Item newItem) {
        Item oldItem = this.dbContext.getDbSetOf(Item.class).findById(newItem.getId());
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

    private int generateNextId() {
        Set<Item> items = this.getAll();
        if (items.isEmpty())
            return 1;
        return items.stream().reduce(null, (nextId, item) -> {
            return nextId.getId() < item.getId()? item : nextId;
        }).getId();
    }

}
