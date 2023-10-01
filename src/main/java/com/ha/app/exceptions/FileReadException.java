package com.ha.app.exceptions;

import com.ha.app.enums.errors.ErrorSeverity;
import com.ha.app.enums.errors.ErrorType;

public class FileReadException extends ApplicationException{
    ErrorInfo errorInfo;
    public FileReadException() {
        super();
        this.errorInfo = new ErrorInfo();

        errorInfo.setErrorSeverity(ErrorSeverity.WARNING);
        errorInfo.setErrorType(ErrorType.CLIENT);
        errorInfo.setErrorId("FileReadError");
        errorInfo.setErrorCorrection("Validate file path");
        errorInfo.setUserErrorDescription("Internal Error");
        errorInfo.setErrorDescription("System encounter an error while processing file");

        super.addErrorInfo(this.errorInfo);
    }

    public FileReadException(Throwable cause) {
        this();
        this.errorInfo.setCause(cause);
    }

    public void setContext(String context) {
        this.errorInfo.setContextId(context);
    }

    public void addParameter(String field, Object value) {
        this.errorInfo.getParameters().put(field, value);
    }

}