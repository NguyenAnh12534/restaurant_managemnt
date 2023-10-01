package com.ha.app.exceptions;

import com.ha.app.enums.errors.ErrorSeverity;
import com.ha.app.enums.errors.ErrorType;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains information of an error
 */
public class ErrorInfo {

    protected Throwable cause;
    protected String errorId;
    protected String contextId;
    protected ErrorType errorType;
    protected ErrorSeverity errorSeverity;
    protected String userErrorDescription;
    protected String errorDescription;
    protected String errorCorrection;
    protected Map<String, Object> parameters = new HashMap<>();

    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
        this.setErrorDescription(cause.getMessage());
    }

    public String getErrorId() {
        return errorId;
    }

    public void setErrorId(String errorId) {
        this.errorId = errorId;
    }

    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    public ErrorSeverity getErrorSeverity() {
        return errorSeverity;
    }

    public void setErrorSeverity(ErrorSeverity errorSeverity) {
        this.errorSeverity = errorSeverity;
    }

    public String getUserErrorDescription() {
        return userErrorDescription;
    }

    public void setUserErrorDescription(String userErrorDescription) {
        this.userErrorDescription = userErrorDescription;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getErrorCorrection() {
        return errorCorrection;
    }

    public void setErrorCorrection(String errorCorrection) {
        this.errorCorrection = errorCorrection;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
    public void addParameter(String parameterName, Object parameterValue) {
        this.parameters.put(parameterName, parameterValue);
    }

    @Override
    public String toString() {
        StringBuilder errorStrBuilder = new StringBuilder();

        errorStrBuilder.append("\t\tContext ID: " + this.contextId);
        errorStrBuilder.append("\n");
        errorStrBuilder.append("\t\tError ID: " + this.errorId);
        errorStrBuilder.append("\n");
        if(this.errorType != null) {
            errorStrBuilder.append("\t\tError Type: " + this.errorType);
            errorStrBuilder.append("\n");
        }
        if(this.errorSeverity != null) {
            errorStrBuilder.append("\t\tSeverity Type: " + this.errorSeverity);
            errorStrBuilder.append("\n");
        }
        errorStrBuilder.append("\t\tError description: " + this.errorDescription);
        errorStrBuilder.append("\n");
        errorStrBuilder.append("\t\tError correction: " + this.errorCorrection);
        errorStrBuilder.append("\n");
        if(!this.parameters.isEmpty()) {
            errorStrBuilder.append("\t\tVariables at runtime: ");
            errorStrBuilder.append("\n");
            this.parameters.entrySet().forEach(parameter -> {
                errorStrBuilder.append("\t\t\t" + parameter.getKey() + ": " + parameter.getValue());
                errorStrBuilder.append("\n");

            });
        }

        return errorStrBuilder.toString();
    }
}
