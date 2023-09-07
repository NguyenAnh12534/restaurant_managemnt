package com.ha.app.exceptions;

public class InvalidInputException extends RuntimeException{

    /*
     * Required when we want to add a custom message when throwing the exception
     * as throw new InvalidInputException("Input is invalid");
     */
    public InvalidInputException(String message) {
        super(message);
    }

    /*
     * Required when we want to wrap the exception generated inside the catch block and rethrow it
     * as catch(ArrayIndexOutOfBoundsException e) {
     * throw new CustomUncheckedException(e);
     * }
     */
    public InvalidInputException(Throwable cause) {
        super(cause);
    }

    /*
     * Required when we want both the above
     * as catch(ArrayIndexOutOfBoundsException e) {
     * throw new InvalidInputException(e, "Invalid input");
     * }
     */
    public InvalidInputException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
