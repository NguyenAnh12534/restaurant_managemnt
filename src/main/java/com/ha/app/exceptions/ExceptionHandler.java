package com.ha.app.exceptions;

import com.ha.app.enums.errors.ErrorType;
import com.ha.app.helpers.FileHelper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class handles all exceptions of the applications
 */
public class ExceptionHandler {
    private static ExceptionHandler exceptionHandler = null;

    public static ExceptionHandler getInstance() {
        if (exceptionHandler == null) {
            exceptionHandler = new ExceptionHandler();
        }

        return exceptionHandler;
    }

    public void notifyUser(Throwable e) {
        if (e instanceof ApplicationException) {
            ApplicationException applicationException = (ApplicationException) e;
            if(applicationException.getErrorType().equals(ErrorType.CLIENT)) {
                // The error is actually caused by the end users malfunction
                // show the cause and also how to correct it
                System.out.println(applicationException.getErrorInfos().peek().getUserErrorDescription());
            } else {
                // The error is not caused by the user
                // Therefore only show standard error message.
                // This way we are not risking expose internal structure of application
                System.out.println("An internal error happened. Pleas contact IT support for more information");
            }
        } else {
            // It is an unknown exception. Show standard error message;
            System.out.println("An internal error happened");
        }

    }

    public void notifyNonUser(Throwable e) {
        if (e instanceof ApplicationException) {
            ApplicationException exception = (ApplicationException) e;

            switch (exception.getErrorType()) {
                case CLIENT -> {
                    // The error is caused by user
                    // It may not be necessary to log
                    // The application is working fine
                    // It is just the user who is malfunctioning
                }
                case INTERNAL -> {
                    File logFile = FileHelper.readFile("log", "bug.txt");

                    try {
                        FileWriter fileWriter = new FileWriter(logFile);
                        fileWriter.write("Full bug id: " + exception.getFullBugId() + "\n");
                        fileWriter.write("Error type: " + exception.getErrorType()+ "\n");
                        fileWriter.write("Severity: " + exception.getErrorSeverity()+ "\n");
                        fileWriter.write("Stack trace: " + "\n");
                        fileWriter.write("[" + "\n");
                        exception.getErrorInfos().forEach(errorInfo -> {
                            try {
                                fileWriter.write("\t{" + "\n");
                                fileWriter.write(errorInfo.toString()+ "\n");
                                fileWriter.write("\t}," + "\n");
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        });
                        fileWriter.write("]" + "\n");
                        fileWriter.flush();
                        fileWriter.close();
                    }catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }

        } else {
            // It is an unknown exception. It is also serious problem since the cause of this error is unknown.
        }
    }

}
