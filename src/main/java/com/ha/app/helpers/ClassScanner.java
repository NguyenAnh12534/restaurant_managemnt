package com.ha.app.helpers;

import com.ha.app.helpers.RuntimeHelper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassScanner {

    public static List<Class<?>> getAllClassesInPackage(String packageName){
        List<Class<?>> classes = RuntimeHelper.isRunningFromJar() ? scanComponentsInJar(packageName) : scanComponents(packageName);

        return classes;
    }

    public static List<Class<?>> scanComponentsInJar(String packageName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        List<Class<?>> classes = new ArrayList<>();

        try {
            Enumeration<java.net.URL> resources = classLoader.getResources(path);

            while (resources.hasMoreElements()) {
                java.net.URL resource = resources.nextElement();

                if (resource.getProtocol().equals("jar")) {
                    String jarPath = resource.getPath().substring(5, resource.getPath().indexOf("!"));
                    classes.addAll(scanClassesInJar(jarPath, path, packageName));
                } else {
                    // Handle non-jar resources if needed
                }
            }
        } catch (IOException e) {
            // Handle IOException
        }

        return classes;
    }

    private static List<Class<?>> scanClassesInJar(String jarPath, String packagePath, String packageName) throws IOException {
        List<Class<?>> classes = new ArrayList<>();
        try (java.util.jar.JarFile jar = new java.util.jar.JarFile(jarPath)) {
            Enumeration<java.util.jar.JarEntry> entries = jar.entries();

            while (entries.hasMoreElements()) {
                java.util.jar.JarEntry entry = entries.nextElement();
                String name = entry.getName();

                if (name.startsWith(packagePath) && name.endsWith(".class")) {
                    String className = name.substring(0, name.length() - 6).replace('/', '.');
                    try {
                        Class<?> clazz = Class.forName(className);
                        classes.add(clazz);
                    } catch (ClassNotFoundException e) {
                        // Handle ClassNotFoundException if necessary
                    }
                }
            }
        }
        return classes;
    }

    public static List<Class<?>> scanComponents(String packageName) {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        List<Class<?>> classes = new ArrayList<>();

        try {
            for (File file : new File(classLoader.getResource(path).getFile()).listFiles()) {
                if (file.isDirectory()) {
                    classes.addAll(scanComponents(packageName + "." + file.getName()));
                } else if (file.getName().endsWith(".class")) {
                    String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                    Class<?> clazz = Class.forName(className);
                    classes.add(clazz);
                }
            }
        } catch (Exception e) {
            // Handle the case when the package does not exist
        }

        return classes;
    }
}