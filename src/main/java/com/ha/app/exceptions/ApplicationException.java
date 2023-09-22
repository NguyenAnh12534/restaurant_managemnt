package com.ha.app.exceptions;

import com.ha.app.enums.errors.ErrorSeverity;
import com.ha.app.enums.errors.ErrorType;

import java.util.Stack;

/**
 * This is the main Exception class of application
 * Every custom exception extends from this class
 */
public class ApplicationException extends RuntimeException {

    private Stack<ErrorInfo> errorInfos = new Stack<>();

    public ApplicationException() {

    }

    public void addErrorInfo(ErrorInfo errorInfo) {
        this.errorInfos.add(errorInfo);
    }

    public Stack<ErrorInfo> getErrorInfos() {
        return this.errorInfos;
    }

    public ErrorType getErrorType() {
        for (ErrorInfo errorInfo : this.errorInfos) {
            if (errorInfo.getErrorType() != null) {
                return errorInfo.getErrorType();
            }
        }

        return null;
    }

    public ErrorSeverity getErrorSeverity() {
        for (ErrorInfo errorSeverity : this.errorInfos) {
            if (errorSeverity.getErrorSeverity() != null) {
                return errorSeverity.getErrorSeverity();
            }
        }

        return null;
    }

    public String getFullBugId() {
        StringBuilder bugIdBuilder = new StringBuilder();
        this.errorInfos.forEach(errorInfo -> {
            bugIdBuilder.append(errorInfo.getContextId());
            bugIdBuilder.append(":");
            bugIdBuilder.append(errorInfo.getErrorId());
            bugIdBuilder.append("/");
        });

        return bugIdBuilder.toString();
    }
}
