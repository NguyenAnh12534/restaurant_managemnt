package com.ha.app.enums.errors;

public enum ErrorType {
    /**
     * Client errors are errors caused by the clients wrong use of the application.
     * For instance, a user types in the wrong file name, or types in characters where the input should have been a number.
     * A client error is normal. It is expected to happen from time to time.
     * A client error is not a sign that your application has bugs.
     * It is a sign that your application correctly rejected invalid data or usage.
     *
     */
    CLIENT,
    /**
     * Internal errors are errors that are caused by your application.
     * The cause for such errors could for instance be a bug in the application or invalid configuration.
     * Generally, any error that cannot be determined to be a client error or service error, should be categorized as an internal error.
     */
    INTERNAL
}
