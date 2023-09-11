package com.ha.app.commons.depedencyinjection;

import java.lang.reflect.Method;
import java.util.List;

public class Bean {
    private String name;
    private Class<?> type;
    private List<Method> methods;

    public Bean(String name, Class<?> type, List<Method> methods, Object instance) {
        this.name = name;
        this.type = type;
        this.methods = methods;
        this.instance = instance;
    }

    private Object instance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }


    public List<Method> getMethods() {
        return this.methods;
    }

    /**
     * Index starts at 1
     */
    public Method getMethodAt(int index) {
        return this.methods.get(index);
    }

}
