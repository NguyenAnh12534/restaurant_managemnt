package com.ha.app.entities;

import com.ha.app.annotations.data.Entity;
import com.ha.app.annotations.data.Id;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Order {
    @Id
    private int id;
    private String name;
}
