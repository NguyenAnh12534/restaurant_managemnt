package com.ha.app.entities;

import com.ha.app.annotations.data.Entity;
import com.ha.app.annotations.data.Id;
import com.ha.app.annotations.data.OneToMany;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Menu {
    @Id
    private int id;
    private String name;

    @OneToMany(mappedBy = "menu", childEntity = Item.class)
    private Set<Item> items = new HashSet<>();

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

    public Set<Item> getItems() {
        if (items == null) {
            this.items = new HashSet<>();
        }
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( "Menu: " + name + "\n");
        if(!this.items.isEmpty()) {
            stringBuilder.append("All items: \n");
            for (Item item : this.items) {
                stringBuilder.append(item);
            }
        }

        return stringBuilder.toString();
    }
}
