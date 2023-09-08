package com.ha.app.entities;

import com.ha.app.annotations.data_annotations.Entity;

@Entity
public class Item{
    private Integer id;
    private String name;
    private Double price;

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

    @Override
    public String toString() {
        return "ID: " + this.id + ", Name: " + this.name;
    }
}
