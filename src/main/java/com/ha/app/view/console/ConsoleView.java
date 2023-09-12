package com.ha.app.view.console;

import com.ha.app.annotations.ui.ViewFeature;
import com.ha.app.view.Renderable;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class ConsoleView<T extends ConsoleView> implements Renderable {
    private Scanner scanner = new Scanner(System.in);
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
        for(int i = 0; i < this.features.size(); i++) {
            System.out.println( i+1 + ". " + this.features.get(i).getName());
        }
        System.out.print("Enter the index of the desired feature: ");
        int i = scanner.nextInt();
        this.selectedIndex = i - 1;
    }


    protected void executeFeature() {
        Method selectedFeature = this.features.get(selectedIndex);
        selectedFeature.setAccessible(true);
        try {
            selectedFeature.invoke(getCurrentView());
        }catch (Exception ex) {
            ex.printStackTrace();
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
