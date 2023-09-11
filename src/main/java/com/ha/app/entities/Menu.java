package com.ha.app.entities;

import com.ha.app.annotations.data.Entity;
import com.ha.app.annotations.data.OneToMany;

import java.util.List;

@Entity
public class Menu {
    private int id;
    private String name;

    @OneToMany
    private List<Item> items;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        if (items == null) {

        }
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
