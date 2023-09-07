package com.ha.app.entities;

import com.ha.app.annotations.data_annotations.Entity;

@Entity
public class OrderItem {
    private int id;

    private String name;

    private int quantity;
}
