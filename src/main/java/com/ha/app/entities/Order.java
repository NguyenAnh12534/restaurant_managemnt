package com.ha.app.entities;

import com.ha.app.annotations.data.Entity;
import com.ha.app.annotations.data.Id;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Order {
    @Id
    private int id;
    public boolean isAnagram(String s, String t) {
        if(s.length() != t.length())
            return false;
        int charSum = 0;

        Map<Character, Integer> values = new HashMap<>();

        for(int i = 0; i < s.length(); i++) {

        }

        return charSum == 0;
    }
    private String name;
}
