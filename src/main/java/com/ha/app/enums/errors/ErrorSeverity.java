package com.ha.app.enums.errors;

public enum ErrorSeverity {
    /**
     * These are the most severe errors and typically lead to program termination.
     * They often indicate unrecoverable issues, such as memory corruption or hardware failures
     */
    FATAL,
    /**
     * Critical errors are serious issues that can cause the program to crash or produce incorrect results.
     * They usually require immediate attention and may result in program termination.
     */
    CRITICAL,
    /**
     * Warning errors are less severe than errors and typically indicate potential problems or issues that should be addressed but do not prevent the program from running.
     */
    WARNING;
}
