package com.ha.app.services;

import com.ha.app.entities.Item;
import com.ha.app.entities.Menu;

import java.util.Set;

public interface MenuService extends Service<Menu>{
     Menu get(int id);
     Set<Menu> getAll();
     void create(Menu menu);
     void update(Menu newMenu, int oldMenuId);
     void addItemToMenu(int itemId, int menuId);
     void delete(int id);
}
