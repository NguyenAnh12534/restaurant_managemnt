package com.ha.app.helpers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileHelper {

    public static File readFile(String directoryPath ,String fileName) {
        Path filePath = Path.of(directoryPath, fileName);
        if(!Files.exists(filePath))
        {
            try{
                Files.createDirectories(filePath.getParent());
                Files.createFile(filePath);
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return filePath.toFile();
    }
}
