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
            errorInfo.setErrorId("GetOneMenu");

            exception.addErrorInfo(errorInfo);

            throw exception;
        }
    }

    @Override
    public Set<Menu> getAll() {
        try{
            Set<Menu> menus = dbContext.getDbSetOf(Menu.class).getAll();
            return menus;
        }catch (ApplicationException exception){
            ErrorInfo errorInfo = new ErrorInfo();

            errorInfo.setContextId(this.getClass().getSimpleName());
            errorInfo.setErrorId("GetAllMenu");

            exception.addErrorInfo(errorInfo);

            throw exception;
        }
    }

    @Override
    public void create(Menu menu) {
        try{
            this.dbContext.getDbSetOf(Menu.class).create(menu);
        }
        catch (ApplicationException exception){
            ErrorInfo errorInfo = new ErrorInfo();

            errorInfo.setContextId(this.getClass().getSimpleName());
            errorInfo.setErrorId("GetAllMenu");

            exception.addErrorInfo(errorInfo);

            throw exception;
        }
    }

    @Override
    public void update(Menu newMenu) {
        Menu oldMenu = this.dbContext.getDbSetOf(Menu.class).findById(newMenu.getId());
        oldMenu.setName(newMenu.getName());
        oldMenu.setItems(newMenu.getItems());

        this.dbContext.getDbSetOf(Item.class).flush();
    }

    @Override
    public void delete(int id) {
        this.dbContext.getDbSetOf(Menu.class).deleteById(id);
    }

    @Override
    public boolean isExisted(int menuId) {
        return dbContext.getDbSetOf(Menu.class).findById(menuId) != null;
    }
}
