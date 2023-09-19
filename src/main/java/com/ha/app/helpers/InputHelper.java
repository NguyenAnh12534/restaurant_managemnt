package com.ha.app.helpers;

import com.ha.app.constants.InputConstants;
import com.ha.app.exceptions.ExitExcpetion;

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

    public int getInteger() {
        do {
            try{
                System.out.print("Please enter an integer number: ");
                int value = Integer.parseInt(this.getInput());
                return value;
            }
            catch (ExitExcpetion ex) {
                throw  ex;
            }
            catch (Exception ex){
                System.out.println("Please enter again. Your input is not valid");
            }
        }while(true);
    }

    public double getDouble() {
        do {
            try{
                System.out.print("Please enter an double number: ");
                double value = Double.parseDouble(this.getInput());
                return value;
            }
            catch (ExitExcpetion ex) {
                throw  ex;
            }
            catch (Exception ex){
                System.out.println("Please enter again. Your input is not valid");
            }
        }while(true);
    }

    public String getLine() {
        String value = this.scanner.nextLine();
        if(InputConstants.EXIT_COMMAND.equals(value)) {
            throw new ExitExcpetion();
        }

        return value;
    }


    private String getInput() {
        String value = this.scanner.next();
        this.scanner.nextLine();
        if(InputConstants.EXIT_COMMAND.equals(value)) {
            throw new ExitExcpetion();
        }

        return value;
    }
}
