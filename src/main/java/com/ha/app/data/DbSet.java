package com.ha.app.data;

import com.ha.app.annotations.data.ManyToOne;
import com.ha.app.annotations.data.OneToMany;
import com.ha.app.data.drivers.DataDriver;
import com.ha.app.entities.Item;
import com.ha.app.exceptions.InvalidInputException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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
        newDbSet.isFiltering = true;
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
    public T findById(int id) {
        return this.filterByField("id", id).getOne();
    }

    public void deleteById(int id) {
        this.deleteByField("id", id);
    }

    public void flush() {
        if(this.elements.size() > 0)
            this.dataDriver.saveAllObjects(this.elements);
    }

    private void deleteByField(String fieldName, Object fieldValue) {
        this.initElements();
        Field[] allFields = targetClass.getDeclaredFields();
        Field selectedField = null;
        for (Field field: allFields) {
            if(field.getName().equals(fieldName)) {
                selectedField = field;
                break;
            }
        }

        if(selectedField == null) {
            throw new IllegalArgumentException();
        }

        selectedField.setAccessible(true);

        selectedField.setAccessible(true);
        if(this.elements.size() > 0) {
            Iterator<T> iterator =  this.elements.iterator();
            while (iterator.hasNext()) {
                T element = iterator.next();
                try {
                    Object value = selectedField.get(element);
                    if (value != null && value.equals(fieldValue)) {
                        iterator.remove();
                    }
                } catch (IllegalAccessException exception) {
                    System.out.println("Can not access field");
                }
            }
        }
    }

    private void initElements() {
        this.elements = this.dataDriver.getAll(targetClass);
        Field[] fields = targetClass.getDeclaredFields();
        for(Field field : fields) {
            if(field.isAnnotationPresent(OneToMany.class)) {
                System.out.println(field.getType().getSimpleName());
            } else if(field.isAnnotationPresent(ManyToOne.class)) {
                System.out.println(field.getType().getSimpleName());
            }
        }
    }
}
