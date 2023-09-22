package com.ha.app.exceptions;

import com.ha.app.enums.errors.ErrorSeverity;
import com.ha.app.enums.errors.ErrorType;

import javax.swing.plaf.basic.BasicTreeUI;

/**
 * This exception is thrown when an element can not be found
 */
public class NotFoundException extends ApplicationException{
    ErrorInfo errorInfo;
    public NotFoundException() {
        super();
        this.errorInfo = new ErrorInfo();

        errorInfo.setErrorSeverity(ErrorSeverity.WARNING);
        errorInfo.setErrorType(ErrorType.CLIENT);
        errorInfo.setErrorId("NotFound");
        errorInfo.setErrorCorrection("Require user to choose another element");
        errorInfo.setUserErrorDescription("Can not find element");
        errorInfo.setErrorDescription("User looked for a non-existed element");

        super.addErrorInfo(this.errorInfo);
    }

    public NotFoundException(Throwable cause) {
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
