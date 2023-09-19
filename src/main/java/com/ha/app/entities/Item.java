package com.ha.app.entities;

import com.ha.app.annotations.data.Entity;
import com.ha.app.annotations.data.Id;
import com.ha.app.annotations.data.ManyToOne;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Objects;

@Entity
public class Item{
    @Id
    private Integer id;
    private String name;
    private Double price;

    @ManyToOne
    private Menu menu;

    public int menu_id;

    public Item(Integer id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Item() {

    }

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
        this.menu_id = menu.getId();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ID: ");
        stringBuilder.append(this.id);
        stringBuilder.append(", Name: ");
        stringBuilder.append(this.name);
        stringBuilder.append(", Price: ");
        stringBuilder.append(this.price);
        if(this.menu_id != 0) {
            stringBuilder.append(", ");
            stringBuilder.append(this.menu);
        }

        return stringBuilder.toString() ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id.equals(item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
