package com.ha.app.services.impl;

import com.ha.app.annotations.Autowired;
import com.ha.app.annotations.Component;
import com.ha.app.entities.Item;
import com.ha.app.enums.errors.ErrorSeverity;
import com.ha.app.enums.errors.ErrorType;
import com.ha.app.exceptions.ApplicationException;
import com.ha.app.exceptions.ErrorInfo;
import com.ha.app.exceptions.NotFoundException;
import com.ha.app.repositories.ItemRepository;
import com.ha.app.services.ItemService;
import com.ha.app.services.OrderService;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Component
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderService orderService;

    @Override
    public Item get(int id) {
        Optional<Item> itemOptional = this.itemRepository.get(id);
        try{
            return itemOptional.orElseThrow();
        }catch (NoSuchElementException exception) {
            NotFoundException notFoundException = new NotFoundException();
            notFoundException.setContext("GetOneItem");
            notFoundException.addParameter("id", id);
            throw notFoundException;
        }
    }

    @Override
    public Set<Item> getAll() {
        return itemRepository.getAll();
    }

    @Override
    public Set<Item> getAllByFields(Map<Field, Object> criteria) {
        return this.itemRepository.getAllByFields(criteria);
    }

    @Override
    public void create(Item item) {
        this.itemRepository.create(item);
    }

    @Override
    public void update(Item newItem, int oldItemId) {
        if (!this.itemRepository.isExisted(oldItemId)) {
            NotFoundException notFoundException = new NotFoundException();
            notFoundException.setContext("UpdatingItem");
            notFoundException.addParameter("id", oldItemId);

            throw notFoundException;
        }

        this.itemRepository.update(newItem, oldItemId);
    }

    @Override
    public void delete(int id) {
        if (this.itemRepository.isExisted(id)) {
            if (!this.itemRepository.isExisted(id)) {
                NotFoundException notFoundException = new NotFoundException();
                notFoundException.setContext("DeletingItem");
                notFoundException.addParameter("id", id);

                throw notFoundException;
            }
        }
        this.itemRepository.delete(id);
    }
}
