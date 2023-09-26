package com.ha.app.exceptions;

import com.ha.app.enums.errors.ErrorSeverity;
import com.ha.app.enums.errors.ErrorType;

public class InvalidInputException extends ApplicationException{

    private ErrorInfo errorInfo;
    public InvalidInputException() {
        super();
        this.errorInfo = new ErrorInfo();

        errorInfo.setErrorSeverity(ErrorSeverity.WARNING);
        errorInfo.setErrorType(ErrorType.CLIENT);
        errorInfo.setErrorId("InvalidInput");
        errorInfo.setErrorCorrection("Require user to enter other value");

        super.addErrorInfo(this.errorInfo);
    }

    public InvalidInputException(Throwable cause) {
        this();
        this.errorInfo.setCause(cause);
    }
    public void setErrorDescription(String errorDescription) {
        this.errorInfo.setErrorDescription(errorDescription);
    }
    public void setContext(String context) {
        this.errorInfo.setContextId(context);
    }

    public void addParameter(String field, Object value) {
        this.errorInfo.getParameters().put(field, value);
    }
    public ErrorInfo getErrorInfo() {
        return this.errorInfo;
    }
}
