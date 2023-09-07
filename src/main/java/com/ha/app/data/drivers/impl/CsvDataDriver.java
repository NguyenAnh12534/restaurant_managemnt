package com.ha.app.data.drivers.impl;

import com.ha.app.constants.DataConstants;
import com.ha.app.data.drivers.DataDriver;
import com.ha.app.helpers.FileHelper;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.FuzzyMappingStrategyBuilder;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.List;

public class CsvDataDriver implements DataDriver {

    private <T> void writeOne(T object, boolean isAppending) {
        try {
            String fileName = object.getClass().getSimpleName() + ".csv";
            File csvFile = FileHelper.readFile(DataConstants.DATA_FOLDER, fileName);
            Writer writer;
            if(isAppending) {
                writer = new FileWriter(csvFile, true);
            } else {
                writer = new FileWriter(csvFile);
            }

            Field[] fields = object.getClass().getDeclaredFields();

            // Write header
            if (!isAppending) {
                for (int i = 0; i < fields.length; i++) {
                    if (i == fields.length - 1) {
                        writer.write("\"" + fields[i].getName() + "\"" + "\n");
                    } else {
                        writer.write("\"" + fields[i].getName() + "\"" + ",");
                    }
                }
            }

            // Write body
            writeBody(writer, fields, object);
            writer.close();
        } catch (Exception exception) {
            exception.fillInStackTrace();
        }
    }

    private <T> void writeAll(List<T> objects, boolean isAppending) {
        try {
            String fileName = objects.get(0).getClass().getSimpleName() + ".csv";
            File csvFile = FileHelper.readFile(DataConstants.DATA_FOLDER, fileName);
            Writer writer;
            if(isAppending) {
                writer = new FileWriter(csvFile, true);
            } else {
                writer = new FileWriter(csvFile);
            }

            Object obj = objects.get(0);
            Field[] fields = obj.getClass().getDeclaredFields();

            // Write header
            if (!isAppending) {
                for (int i = 0; i < fields.length; i++) {
                    if (i == fields.length - 1) {
                        writer.write("\"" + fields[i].getName() + "\"" + "\n");
                    } else {
                        writer.write("\"" + fields[i].getName() + "\"" + ",");
                    }
                }
            }

            // Write body
            for (Object object : objects) {
                writeBody(writer, fields, object);
            }
            writer.close();
        } catch (Exception exception) {
            exception.fillInStackTrace();
        }
    }

    private void writeBody(Writer writer, Field[] fields, Object object) throws IllegalAccessException, IOException {
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            f.setAccessible(true);
            String value;
            if (f.getType().equals(String.class)) {
                value = "\"" + f.get(object) + "\"";
            } else {
                value = f.get(object).toString();
            }
            if (i == fields.length - 1) {
                writer.write(value + "\n");
            } else {
                writer.write(value + ",");
            }
        }
    }

    @Override
    public <T> void saveObject(T object) {
        writeOne(object, false);
    }

    @Override
    public <T> void saveAllObjects(List<T> objects) {
        writeAll(objects, false);
    }

    @Override
    public <T> void appendObject(T object) {
        writeOne(object,true);
    }

    @Override
    public <T> void appendAllObjects(List<T> objects) {
        writeAll(objects, true);
    }

    @Override
    public <T> List<T> getAll(Class<T> tClass) {
        try {
            String fileName = tClass.getSimpleName() + ".csv";
            MappingStrategy<T> strategy = new FuzzyMappingStrategyBuilder<T>().build();
            strategy.setType(tClass);
            File csvFile = FileHelper.readFile(DataConstants.DATA_FOLDER, fileName);
            List<T> beans = new CsvToBeanBuilder<T>(new FileReader(csvFile)).withMappingStrategy(strategy).build().parse();
            return beans;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
