package com.ha.app.helpers;

public class RuntimeHelper {

    public static boolean isRunningFromJar(){
        String className = RuntimeHelper.class.getName().replace('.', '/');
        String classJar = RuntimeHelper.class.getResource("/" + className + ".class").toString();
        return classJar.startsWith("jar");
    }
}
