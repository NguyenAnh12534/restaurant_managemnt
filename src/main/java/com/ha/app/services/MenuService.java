package com.ha.app.services;

import com.ha.app.entities.Item;
import com.ha.app.entities.Menu;

import java.util.Set;

public interface MenuService extends Service<Menu>{
     void addItemToMenu(int itemId, int menuId);
}
