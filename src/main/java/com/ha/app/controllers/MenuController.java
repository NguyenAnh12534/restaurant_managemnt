package com.ha.app.controllers;

import com.ha.app.annotations.Autowired;
import com.ha.app.annotations.Controller;
import com.ha.app.entities.Menu;
import com.ha.app.services.MenuService;

import java.util.Set;

@Controller
public class MenuController {
    @Autowired
    MenuService menuService;

    public Menu get(int id) {
        return this.menuService.get(id);
    }

    public Set<Menu> getAll() {
        return this.menuService.getAll();
    }

    public void create(Menu menu) {
        this.menuService.create(menu);
    }

    public void addItemToMenu(int itemId, int menuid) {
        this.menuService.addItemToMenu(itemId, menuid);
    }
    public void update(Menu newMenu, int oldMenuId) {
        this.menuService.update(newMenu, oldMenuId);
    }

    public void delete(int id) {
        this.menuService.delete(id);
    }
}
