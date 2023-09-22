package com.ha.app.repositories;

import com.ha.app.entities.Item;
import com.ha.app.entities.Menu;

import java.util.Optional;
import java.util.Set;

public interface MenuRepository extends Repository<Menu>{
    public Optional<Menu> get(int id);
    public Set<Menu> getAll();
    public void create(Menu menu);
    public void update(Menu newMenu);
    public void delete(int id);
    public boolean isExisted(int itemId);
}
