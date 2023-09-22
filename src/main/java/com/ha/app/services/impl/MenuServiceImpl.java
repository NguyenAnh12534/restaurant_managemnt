package com.ha.app.services.impl;

import com.ha.app.annotations.Autowired;
import com.ha.app.annotations.Component;
import com.ha.app.entities.Item;
import com.ha.app.entities.Menu;
import com.ha.app.exceptions.NotFoundException;
import com.ha.app.repositories.ItemRepository;
import com.ha.app.repositories.MenuRepository;
import com.ha.app.services.MenuService;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Component
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Menu get(int id) {
        Optional<Menu> menuOptional = this.menuRepository.get(id);
        try {
            return menuOptional.orElseThrow();
        } catch (NoSuchElementException exception) {
            NotFoundException notFoundException = new NotFoundException();
            notFoundException.setContext("GetOneMenu");
            notFoundException.addParameter("id", id);
            throw notFoundException;
        }
    }

    @Override
    public Set<Menu> getAll() {
        return menuRepository.getAll();
    }

    @Override
    public void create(Menu menu) {
        this.menuRepository.create(menu);
    }

    @Override
    public void addItemToMenu(int itemId, int menuId) {
        Optional<Item> itemToAddOptional = this.itemRepository.get(itemId);
        Optional<Menu> menuOptional = this.menuRepository.get(menuId);

        if (menuOptional.isEmpty()) {
            NotFoundException notFoundException = new NotFoundException();
            notFoundException.setContext("AddingItemToMenu");
            notFoundException.addParameter("menuId", menuId);

            throw notFoundException;
        }

        if (itemToAddOptional.isEmpty()) {
            NotFoundException notFoundException = new NotFoundException();
            notFoundException.setContext("AddingItemToMenu");
            notFoundException.addParameter("itemId", menuId);

            throw notFoundException;
        }

        Item itemToAdd = itemToAddOptional.get();
        Menu menu = menuOptional.get();
        menu.getItems().add(itemToAdd);
        this.menuRepository.update(menu);
    }

    @Override
    public void update(Menu newMenu, int oldMenuId) {
        if (!this.menuRepository.isExisted(oldMenuId)) {
            NotFoundException notFoundException = new NotFoundException();
            notFoundException.setContext("UpdatingMenu");
            notFoundException.addParameter("id", oldMenuId);

            throw notFoundException;
        }

        newMenu.setId(oldMenuId);

        this.menuRepository.update(newMenu);
    }

    @Override
    public void delete(int id) {
        if (this.menuRepository.isExisted(id)) {
            if (!this.menuRepository.isExisted(id)) {
                NotFoundException notFoundException = new NotFoundException();
                notFoundException.setContext("DeletingMenu");
                notFoundException.addParameter("id", id);

                throw notFoundException;
            }
        }
        this.menuRepository.delete(id);
    }
}
