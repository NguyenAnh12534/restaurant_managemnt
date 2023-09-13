package com.ha.app.exceptions;

import com.ha.app.enums.errors.ErrorSeverity;
import com.ha.app.enums.errors.ErrorType;

import java.io.IOException;

public class ErrorInfoFactory {

    public static final ErrorInfo getFileReadErrorInfo(
            Exception e, String filePath) {

        ErrorInfo info = new ErrorInfo();
        info.setCause(e);
        info.setErrorId("FileReadFound");
        info.setContextId("FileLoader");

        info.setErrorType(ErrorType.INTERNAL);
        info.setErrorSeverity(ErrorSeverity.CRITICAL);

        info.setErrorDescription("Error processing file" + filePath);

        return info;
    }
}
