package com.ha.app.ui.inputs;

import com.ha.app.annotations.data.Entity;
import com.ha.app.commons.depedencyinjection.Bean;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UserInputHandler {

    Scanner scanner = new Scanner(System.in);
    private Bean selectedController;
    private Method selectedMethod;
    private ControllerMapping controllerMapping;

    private List<Object> values = new ArrayList<>();

    public UserInputHandler(ControllerMapping controllerMapping) {
        this.controllerMapping = controllerMapping;
    }

    public void handleUserInput() {
        handleUserChooseController();
        handleUserChooseFeature();
        handleRequireInputFromUser();
        executeSelectedFeature();
    }

    private void handleUserChooseController() {
        System.out.println("All controllers: ");
        for (int i = 0; i < this.controllerMapping.getControllerBeans().size(); i++) {
            System.out.println(i + 1 + ". " + this.controllerMapping.getControllerBeans().get(i).getName());
        }
        System.out.print("Please enter index of desired controller: ");
        boolean isInputFinished = false;
        while (!isInputFinished) {
            try {
                int index = scanner.nextInt();
                scanner.nextLine();
                if (index > controllerMapping.getControllerBeans().size()) {
                    throw new IndexOutOfBoundsException();
                }

                this.selectedController = controllerMapping.getControllerBeanAt(index - 1);
                System.out.println("You have chosen: " + this.selectedController.getName());
                isInputFinished = true;
            } catch (InputMismatchException ex) {
                System.out.println("You must enter a integer number, please choose again");
            } catch (IndexOutOfBoundsException ex) {
                System.out.println("Your number is too large, please choose again");
            }
        }
    }

    private void handleUserChooseFeature() {
        String controllerFullName = selectedController.getName();
        String enityName = controllerFullName.substring(0, controllerFullName.indexOf("Controller"));
        System.out.println("All methods: ");
        for (int i = 0; i < this.selectedController.getMethods().size() - 9; i++) {
            System.out.println(i + 1 + ". " + this.selectedController.getMethodAt(i).getName() + " - " + enityName);
        }
        System.out.print("Please enter index of desired method: ");
        boolean isInputFinished = false;
        while (!isInputFinished) {
            try {
                int index = scanner.nextInt();
                scanner.nextLine();
                if (index > this.selectedController.getMethods().size() - 9) {
                    throw new IndexOutOfBoundsException();
                }
                this.selectedMethod = this.selectedController.getMethodAt(index - 1);
                System.out.println("You have chosen: " + this.selectedMethod.getName());
                isInputFinished = true;
            } catch (InputMismatchException ex) {
                System.out.println("You must enter a integer number, please choose again");
            } catch (IndexOutOfBoundsException ex) {
                System.out.println("Your number is too large, please choose again");
            }
        }
    }

    private void handleRequireInputFromUser() {
        Parameter[] parameters = selectedMethod.getParameters();
        for (Parameter parameter : parameters) {
            Object value = null;
            try {
                value = requireInputOfField(parameter.getType());

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            this.values.add(value);
        }
    }

    private void executeSelectedFeature() {
        try {
            if (selectedMethod.getParameters().length == 1) {
                this.selectedMethod.invoke(this.selectedController.getInstance(), this.values.get(0));
            } else if (selectedMethod.getParameters().length > 1){
                this.selectedMethod.invoke(this.selectedController.getInstance(), this.values.toArray());
            } else {
                this.selectedMethod.invoke(this.selectedController.getInstance());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Object requireInputOfField(Class<?> clazz) throws Exception {
        if (clazz.isAnnotationPresent(Entity.class)) {
            Field[] subFields = clazz.getDeclaredFields();
            List<Object> entityProperties = new ArrayList<>();
            for (Field subField : subFields) {
                Object property = requireInputOfField(subField.getType());
                entityProperties.add(property);
            }
            Class<?>[] types = new Class<?>[entityProperties.size()];

            for (int i = 0; i < types.length; i++) {
                types[i] = entityProperties.get(i).getClass();
            }
            Constructor constructor = clazz.getConstructor(types);
            return constructor.newInstance(entityProperties.toArray());
        }

        boolean isInputValid = false;
        System.out.print("Enter value for type " + clazz.getSimpleName() + " : ");
        Object value = scanner.nextLine();
        while (!isInputValid) {
            try {
                value = translateInput(value, clazz);
                isInputValid = true;
            } catch (IllegalArgumentException ex) {
                System.out.println("Input not valid, please enter again");
            }
        }

        return value;
    }

    private Object translateInput(Object inputValue, Class<?> clazz) {
        String inputValueAsString = (String) inputValue;
        switch (clazz.getSimpleName()) {
            case "byte", "Byte", "short", "Short", "int", "Integer" -> {
                inputValue = Integer.parseInt(inputValueAsString);
            }
            case "float", "Float", "double", "Double" -> {
                inputValue = Double.parseDouble(inputValueAsString);
            }
            case "boolean", "Boolean" -> {
                inputValue = Boolean.parseBoolean(inputValueAsString);
            }
            default -> {

            }
        }

        return inputValue;
    }
}
