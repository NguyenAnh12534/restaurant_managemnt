package com.ha.app.entities;

import com.ha.app.annotations.data_annotations.Entity;
import com.ha.app.annotations.data_annotations.Id;

@Entity
public class Order {
    @Id
    private int id;

    private String name;
}
