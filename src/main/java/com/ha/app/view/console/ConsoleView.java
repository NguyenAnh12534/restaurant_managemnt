package com.ha.app.view.console;

import com.ha.app.annotations.ui.ViewFeature;
import com.ha.app.exceptions.ApplicationException;
import com.ha.app.exceptions.InvalidInputException;
import com.ha.app.helpers.InputHelper;
import com.ha.app.view.Renderable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public abstract class ConsoleView<T extends ConsoleView> implements Renderable {
    private InputHelper inputHelper = InputHelper.getInstance();
    private List<Method> features;
    private int selectedIndex = 0;

    public ConsoleView(Class<T> childClass) {
        features = new ArrayList<>();
        scanForFeatures(childClass);
    }

    @Override
    public void render() {
        selectFeature();
        executeFeature();
    }

    private void selectFeature() {
        System.out.println("Select one feature from the list below: ");
        for (int i = 0; i < this.features.size(); i++) {
            System.out.println(i + 1 + ". " + this.features.get(i).getName());
        }
        int i;
        i = inputHelper.getInteger();
        if(i > this.features.size())
            throw new InvalidInputException("Number enter is too large");
        this.selectedIndex = i - 1;
    }


    protected void executeFeature() {
        Method selectedFeature = this.features.get(selectedIndex);
        System.out.println("\nSelected feature: " + selectedFeature.getName());
        selectedFeature.setAccessible(true);
        try {
            selectedFeature.invoke(getCurrentView());
        } catch (InvocationTargetException ex) {

            if (ex.getTargetException() instanceof ApplicationException) {
                throw (ApplicationException) ex.getTargetException();
            }
        } catch (IllegalAccessException ex) {

        }
    }

    private void scanForFeatures(Class<T> childClass) {
        Method[] childMethods = childClass.getDeclaredMethods();
        for (Method method : childMethods) {
            if (method.isAnnotationPresent(ViewFeature.class)) {
                this.features.add(method);
            }
        }
    }

    abstract protected T getCurrentView();
}
