package com.ha.app.data;

import com.ha.app.annotations.data.Id;
import com.ha.app.data.drivers.DataDriver;
import com.ha.app.helpers.ClassHelper;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * This class handle data of an Entity
 *
 * @param <T>
 *         type of the Entity being handled
 */
public class DbSet<T> {
    private Set<T> elements = new HashSet<>();
    private Class<T> targetClass;
    private DataDriver dataDriver;
    private DbContext dbContext;
    private boolean isFiltering = false;
    private boolean isDataChanged = true;
    private int nextId = -1;

    public DbSet() {

    }

    public DbSet(Class<T> tClass, DataDriver dataDriver, DbContext dbContext) {
        this.dataDriver = dataDriver;
        this.targetClass = tClass;
        this.dbContext = dbContext;
    }

    public DbSet(DbSet dbSet) {
        this.dataDriver = dbSet.dataDriver;
        this.elements = new HashSet<>(dbSet.elements);
        this.dbContext = dbSet.dbContext;
        this.targetClass = dbSet.targetClass;
    }

    public DbSet<T> filterByField(Field filterField, Object value) {
        this.initElements();

        DbSet<T> newDbSet = new DbSet<>(this);
        newDbSet.isFiltering = true;
        Set<T> newElements = new HashSet<>();


        if (filterField == null) {
            return newDbSet;
        }

        if (value.getClass().isAssignableFrom(filterField.getType())) {
            filterField.setAccessible(true);
            newDbSet.elements.forEach(element -> {
                try {
                    if (filterField.getType().equals(String.class)) {
                        String databaseValue = (String) filterField.get(element);
                        if (databaseValue.contains((String) value)) {
                            newElements.add(element);
                        }
                    } else if (filterField.get(element).equals(value)) {
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

    public Set<T> getAll() {
        if (!isFiltering) {
            this.initElements();
        }
        return elements;
    }

    public T getOne() {
        if (!this.isFiltering) {
            initElements();
        }

        if (elements.isEmpty())
            return null;
        return (T) elements.toArray()[0];
    }

    public boolean create(T item) {
        initElements();
        if (this.elements.size() > 0) {
            this.dataDriver.appendObject(item);
        } else {
            this.dataDriver.saveObject(item);
        }

        this.isDataChanged = true;
        return true;
    }

    public boolean create(Set<T> items) {
        this.dataDriver.appendAllObjects(items);
        this.isDataChanged = true;
        return true;
    }

    public T findById(Object id) {
        return this.filterByField(this.extractPrimaryField(), id).getOne();
    }

    public void deleteById(int id) {
        this.deleteByField(this.extractPrimaryField(), id);
    }

    public void flush() {
        if (this.elements.size() > 0)
            this.dataDriver.saveAllObjects(this.elements);
        else
            this.dataDriver.clearData(this.targetClass);
        this.isDataChanged = true;
    }

    private void deleteByField(Field selectedField, Object fieldValue) {
        this.initElements();

        selectedField.setAccessible(true);

        if (this.elements.size() > 0) {
            Iterator<T> iterator = this.elements.iterator();
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
        if (this.isFiltering)
            return;

        if (isDataChanged) {
            isDataChanged = false;
            this.elements = this.dataDriver.getAll(targetClass);
            if (!elements.isEmpty()) {
                this.elements.forEach(element -> {
                    this.dbContext.eagerLoadDataForEntity(element);
                });
            }
        }
    }

    private int getNextId() {
        if (this.nextId != -1) {
            return this.nextId;
        }
        int nextIdValue = 1;
        Field idField = this.extractPrimaryField();
        if (idField != null) {
            idField.setAccessible(true);
            if (this.elements.isEmpty()) {
                this.initElements();
            }
            for (T element : this.elements) {
                try {
                    int id = (int) idField.get(element);
                    if (nextIdValue < id) {
                        nextIdValue = id;
                    }
                } catch (IllegalAccessException ex) {
                    throw new RuntimeException();
                }
            }
        }

        return nextIdValue;
    }

    /**
     * This function extract the field marked with as Id in a class
     * @return
     */
    public Field extractPrimaryField() {
        Field[] fields = this.targetClass.getDeclaredFields();
        for(Field field : fields) {
            if(field.isAnnotationPresent(Id.class))
                return field;
        }
        throw new IllegalArgumentException("Class doesn't contain a Id field");

    }
}
