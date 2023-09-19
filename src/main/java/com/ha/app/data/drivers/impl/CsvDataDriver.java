package com.ha.app.data.drivers.impl;

import com.ha.app.annotations.data.Id;
import com.ha.app.annotations.data.ManyToOne;
import com.ha.app.annotations.data.OneToMany;
import com.ha.app.constants.DataConstants;
import com.ha.app.data.drivers.DataDriver;
import com.ha.app.enums.errors.ErrorSeverity;
import com.ha.app.enums.errors.ErrorType;
import com.ha.app.exceptions.ApplicationException;
import com.ha.app.exceptions.ErrorInfo;
import com.ha.app.helpers.FileHelper;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.FuzzyMappingStrategyBuilder;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;


import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CsvDataDriver implements DataDriver {

    private void writeHeader(Field field, Writer writer, int index) throws IOException {
        String fieldName;

        if (field.isAnnotationPresent(OneToMany.class) || field.isAnnotationPresent(ManyToOne.class)) {
            return;
        }

        fieldName = field.getName();

        if (index > 0)
            writer.write(",");

        writer.write("\"" + fieldName + "\"");
    }

    private <T> void writeOne(T object, boolean isAppending) {
        try {
            String fileName = object.getClass().getSimpleName() + ".csv";
            File csvFile = FileHelper.readFile(DataConstants.DATA_FOLDER, fileName);
            Writer writer;
            if (isAppending) {
                writer = new FileWriter(csvFile, true);
            } else {
                writer = new FileWriter(csvFile);
            }

            Field[] fields = object.getClass().getDeclaredFields();

            // Write header
            if (!isAppending) {
                for (int i = 0; i < fields.length; i++) {
                    this.writeHeader(fields[i], writer, i);
                }
                writer.write("\n");
            }

            // Write body
            writeBody(writer, fields, object);
            writer.close();
        } catch (Exception exception) {
            exception.fillInStackTrace();
        }
    }

    private <T> void writeAll(Set<T> objects, boolean isAppending) {
        try {
           Class targetClass = objects.toArray()[0].getClass();
            String fileName = targetClass.getSimpleName() + ".csv";
            File csvFile = FileHelper.readFile(DataConstants.DATA_FOLDER, fileName);
            try (Writer writer = new FileWriter(csvFile, isAppending)) {
                Field[] fields = targetClass.getDeclaredFields();

                // Write header
                if (!isAppending) {
                    for (int i = 0; i < fields.length; i++) {
                        this.writeHeader(fields[i], writer, i);
                    }
                    writer.write("\n");
                }

                // Write body
                for (Object object : objects) {
                    writeBody(writer, fields, object);
                }
            } catch (IOException | IllegalAccessException ex) {
                throw ex;

            }
        } catch (ApplicationException exception) {
            ErrorInfo errorInfo = new ErrorInfo();

            errorInfo.setContextId(this.getClass().getSimpleName());
            errorInfo.setErrorId("WriteAllObjects");

            exception.addErrorInfo(errorInfo);

            throw exception;

        } catch (IOException | IllegalAccessException ex) {
            ApplicationException applicationException = new ApplicationException();

            ErrorInfo errorInfo = new ErrorInfo();

            errorInfo.setCause(ex);
            errorInfo.setContextId(this.getClass().getSimpleName());
            errorInfo.setErrorId("WriteAllObjects");

            errorInfo.setErrorSeverity(ErrorSeverity.WARNING);
            errorInfo.setErrorType(ErrorType.INTERNAL);

            errorInfo.setErrorDescription("Fail to write a file due to " + ex.getMessage());
            throw applicationException;
        }
    }

    private void writeBody(Writer writer, Field[] fields, Object object) throws IllegalAccessException, IOException {
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            f.setAccessible(true);

            if (f.isAnnotationPresent(OneToMany.class) || f.isAnnotationPresent(ManyToOne.class)) {
                continue;
            }

            String value;

            if (f.get(object) != null) {
                if (f.getType().equals(String.class)) {
                    value = "\"" + f.get(object) + "\"";
                } else {
                    value = f.get(object).toString();
                }
            } else {
                value = "";
            }

            if (i > 0) {
                writer.write(",");
            }
            writer.write(value);
        }
        writer.write("\n");
    }

    @Override
    public <T> void clearData(Class<T> targetClass) {
        String fileName = targetClass.getSimpleName() + ".csv";
        File csvFile = FileHelper.readFile(DataConstants.DATA_FOLDER, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(csvFile);
            fos.getChannel().truncate(0);
        } catch (IOException ex) {
            System.out.println("can't truncate data");
        }
    }

    @Override
    public <T> void saveObject(T object) {
        writeOne(object, false);
    }

    @Override
    public <T> void saveAllObjects(Set<T> objects) {
        writeAll(objects, false);
    }

    @Override
    public <T> void appendObject(T object) {
        writeOne(object, true);
    }

    @Override
    public <T> void appendAllObjects(Set<T> objects) {
        writeAll(objects, true);
    }

    @Override
    public <T> Set<T> getAll(Class<T> tClass) {
        String fileName = tClass.getSimpleName() + ".csv";
        try {
            MappingStrategy<T> strategy = new FuzzyMappingStrategyBuilder<T>().build();
            strategy.setType(tClass);
            File csvFile = FileHelper.readFile(DataConstants.DATA_FOLDER, fileName);
            List<T> beans = new CsvToBeanBuilder<T>(new FileReader(csvFile)).withMappingStrategy(strategy).build().parse();

            return new HashSet<>(beans);
        } catch (Throwable ex) {
            ApplicationException applicationException = new ApplicationException();

            ErrorInfo errorInfo = new ErrorInfo();

            errorInfo.setCause(ex);
            errorInfo.setContextId(this.getClass().getSimpleName());
            errorInfo.setErrorId("ReadingDataFromCsv");

            errorInfo.setErrorSeverity(ErrorSeverity.CRITICAL);
            errorInfo.setErrorType(ErrorType.INTERNAL);

            errorInfo.setErrorDescription("Data in CSV file is in wrong format. Detail message: " + ex.getMessage());
            errorInfo.setErrorCorrection("Check data in CSV file");
            errorInfo.getParameters().put("filePath", DataConstants.DATA_FOLDER + "/" + fileName);

            applicationException.addErrorInfo(errorInfo);

            throw applicationException;
        }
    }
}
