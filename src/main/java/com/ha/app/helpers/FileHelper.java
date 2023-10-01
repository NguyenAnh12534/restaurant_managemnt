package com.ha.app.helpers;

import com.ha.app.enums.errors.ErrorSeverity;
import com.ha.app.enums.errors.ErrorType;
import com.ha.app.exceptions.ApplicationException;
import com.ha.app.exceptions.ErrorInfo;
import com.ha.app.exceptions.FileReadException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileHelper {

    public static File readFile(String directoryPath ,String fileName) {

        if(fileName == null) {
            ApplicationException applicationException = new ApplicationException();
            ErrorInfo errorInfo = new ErrorInfo();

            // Error Information Gathering
            errorInfo.setErrorId("FilePathNull");
            errorInfo.setContextId("FileLoader");

            errorInfo.setErrorType(ErrorType.CLIENT);
            errorInfo.setErrorSeverity(ErrorSeverity.CRITICAL);

            errorInfo.setErrorDescription("The file path of file to load was null");
            errorInfo.setErrorCorrection("Make sure filePath parameter is not null.");

            applicationException.addErrorInfo(errorInfo);
            throw  applicationException;
        }

        Path filePath = Path.of(directoryPath, fileName);
        if(!Files.exists(filePath))
        {
            try{
                Files.createDirectories(filePath.getParent());
                Files.createFile(filePath);
            }catch (IOException ex) {
                FileReadException errorInfo = new FileReadException(ex);
                errorInfo.addParameter("filePath", filePath.toString());
                throw  errorInfo;
            }
        }
        return filePath.toFile();
    }
}
