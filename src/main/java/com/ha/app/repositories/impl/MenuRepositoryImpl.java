package com.ha.app.repositories.impl;

import com.ha.app.annotations.Component;
import com.ha.app.annotations.data.PersistenceContext;
import com.ha.app.data.DbContext;
import com.ha.app.entities.Item;
import com.ha.app.entities.Menu;
import com.ha.app.exceptions.ApplicationException;
import com.ha.app.exceptions.ErrorInfo;
import com.ha.app.repositories.MenuRepository;

import java.util.Optional;
import java.util.Set;

@Component
public class MenuRepositoryImpl implements MenuRepository {

    @PersistenceContext
    DbContext dbContext;


    @Override
    public Optional<Menu> get(int id) {
        try {
            Menu menu = dbContext.getDbSetOf(Menu.class).findById(id);
            Optional<Menu> menuOptional = Optional.of(menu);
            return menuOptional;
        } catch (ApplicationException exception) {
            ErrorInfo errorInfo = new ErrorInfo();

            errorInfo.setContextId(this.getClass().getSimpleName());
            errorInfo.setErrorId("GetOneItem");

            exception.addErrorInfo(errorInfo);

            throw exception;
        }
    }

    @Override
    public Set<Menu> getAll() {
        return null;
    }

    @Override
    public void create(Menu menu) {

    }

    @Override
    public void update(Menu newMenu) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public boolean isExisted(int itemId) {
        return false;
    }
}
