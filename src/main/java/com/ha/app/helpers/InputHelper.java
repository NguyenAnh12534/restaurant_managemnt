package com.ha.app.helpers;

import com.ha.app.constants.InputConstants;
import com.ha.app.exceptions.ExitExcpetion;
import com.ha.app.exceptions.InvalidInputException;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class InputHelper {
    StringBuilder stringBuilder = new StringBuilder();
    Scanner scanner = new Scanner(System.in);
    private static InputHelper inputHelper;

    public static InputHelper getInstance() {
        if (inputHelper == null) {

            inputHelper = new InputHelper();
        }

        return inputHelper;
    }

    public Object getInputOfType(Class<?> targetClass) {

        switch (targetClass.getSimpleName()) {
            case InputConstants.INTEGER_SIMPLE_NAME -> {
                return this.getInteger();
            }
            case InputConstants.DOUBLE_SIMPLE_NAME -> {
                return this.getDouble();
            }
            default -> {
                return this.getLine();
            }
        }
    }

    public Integer getInteger() {
        do {
            try {
                System.out.print("Please enter an integer number: ");
                String input = this.getInput();
                if(input.isBlank())
                    return null;
                int value = Integer.parseInt(input);
                return value;
            } catch (ExitExcpetion ex) {
                throw ex;
            } catch (Exception ex) {
                System.out.println("Please enter again. Your input is not valid");
            }
        } while (true);
    }

    public Double getDouble() {
        do {
            try {
                System.out.print("Please enter an double number: ");
                String input = this.getInput();
                if(input.isBlank())
                    return null;
                double value = Double.parseDouble(input);
                return value;
            } catch (ExitExcpetion ex) {
                throw ex;
            } catch (Exception ex) {
                System.out.println("Please enter again. Your input is not valid");
            }
        } while (true);
    }

    public String getLine() {
        System.out.print("\nPlease enter a string: ");
        String value = this.scanner.nextLine();
        if (InputConstants.EXIT_COMMAND.equals(value)) {
            throw new ExitExcpetion();
        }

        return value;
    }

    public int selectIndexOfCollection(Collection<?> elements) {
        boolean isSelecting = true;
        int selectedFieldIndex;
        while (isSelecting) {
            try {
                selectedFieldIndex = this.getInteger();
                isSelecting = false;
                if (selectedFieldIndex > elements.size()) {
                    InvalidInputException invalidInputException = new InvalidInputException();
                    invalidInputException.setErrorDescription("Chosen number is too large");
                    throw invalidInputException;
                }
                if(selectedFieldIndex <= 0) {
                    {
                        InvalidInputException invalidInputException = new InvalidInputException();
                        invalidInputException.setErrorDescription("Chosen number is too small");
                        throw invalidInputException;
                    }
                }
                return  selectedFieldIndex;
            } catch (InvalidInputException invalidInputException) {
                System.out.println(invalidInputException.getErrorInfo().getErrorDescription());
                System.out.print("Do you want to try again (Y/N) - default is N: ");
                String continueOption = this.scanner.nextLine();
                if (continueOption.trim().toLowerCase().equals("y")) {
                    isSelecting = true;
                }
            }
        }

        throw new ExitExcpetion();
    }


    private String getInput() {
        String value = this.scanner.nextLine();
        if (InputConstants.EXIT_COMMAND.equals(value)) {
            throw new ExitExcpetion();
        }

        return value;
    }
}
