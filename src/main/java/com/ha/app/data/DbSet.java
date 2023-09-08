package com.ha.app.data;

import com.ha.app.data.drivers.DataDriver;
import com.ha.app.entities.Item;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DbSet<T> {
    private List<T> elements = new ArrayList<>();
    private Class<T> targetClass;
    private DataDriver dataDriver;
    private boolean isFiltering = false;

    public DbSet(Class<T> tClass, DataDriver dataDriver) {
        this.dataDriver = dataDriver;
        this.targetClass = tClass;
    }

    private DbSet(DbSet dbSet) {
        this.dataDriver = dbSet.dataDriver;
        this.elements = new ArrayList<>(dbSet.elements);
    }

    public DbSet<T> filterByField(String fieldName, Object value) {
        this.isFiltering = true;
        this.initElements();

        DbSet<T> newDbSet = new DbSet<>(this);
        List<T> newElements = new ArrayList<>();
        Class<T> tClass = (Class<T>) elements.get(0).getClass();
        Field targetField = null;
        Field[] allFields = tClass.getDeclaredFields();
        for (int i = 0; i < allFields.length; i++) {
            if (allFields[i].getName().equals(fieldName)) {
                targetField = allFields[i];
            }
        }

        if (targetField == null) {
            return newDbSet;
        }

        if (value.getClass().isAssignableFrom(targetField.getType())) {
            Field finalTargetField = targetField;
            finalTargetField.setAccessible(true);
            newDbSet.elements.forEach(element -> {
                try {
                    if (finalTargetField.get(element).equals(value)) {
                        newElements.add(element);
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            });
            newDbSet.elements = newElements;
        }

        return newDbSet;
    }

    public List<T> getAll() {
        if (!isFiltering) {
            initElements();
        }
        return elements;
    }

    public T getOne() {
        if (!this.isFiltering) {
            initElements();
        }

        if (elements.isEmpty())
            return null;

        return elements.get(0);
    }

    public boolean create(Item item) {
        this.dataDriver.appendObject(item);
        return true;
    }

    public boolean create(List<Item> items){
        this.dataDriver.appendAllObjects(items);
        return true;
    }

    private void initElements() {
        if (this.elements.isEmpty()) {
            this.elements = this.dataDriver.getAll(targetClass);
        }
    }
}
