package com.ha.app.commons.depedencyinjection;

import com.ha.app.annotations.Autowired;
import com.ha.app.annotations.Component;
import com.ha.app.annotations.data.PersistenceContext;
import com.ha.app.data.DbContext;
import com.ha.app.data.drivers.impl.CsvDataDriver;
import com.ha.app.helpers.ClassScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is the central container for all beans in application
 * Each bean is either annotated with @Component or annotations that are annotated with @Component
 */
public class ApplicationContext {
    private List<Bean> beans;

    private DbContext dbContext;

    public ApplicationContext(String packageName) {
        try {
            dbContext = new DbContext(new CsvDataDriver());
            beans = scanForBeans(packageName);
            injectDependenciesForAllBeans(beans);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public List<Bean> scanForBeans(String packageName) throws Exception {
        List<Class<?>> classes = ClassScanner.getAllClassesInPackage(packageName);
        List<Bean> beans = new ArrayList<>();
        for (Class<?> targetClass : classes) {
            if (checkForAnnotationOnClass(targetClass, Component.class) && !targetClass.isAnnotation()) {
                Bean newBean = new Bean(
                        targetClass.getSimpleName(),
                        targetClass,
                        Arrays.asList(targetClass.getMethods()),
                        targetClass.getConstructor().newInstance());
                beans.add(newBean);
            }
        }

        return beans;
    }

    private boolean checkForAnnotationOnClass(Class<?> clazz, Class<? extends Annotation> targetannotation) {
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(targetannotation) || annotation.annotationType().isAnnotationPresent(Component.class)) {
                return true;
            }
        }
        return false;
    }

    private void injectDependenciesForAllBeans(List<Bean> beans) {
        beans.forEach(this::injectDependenciesForBean);
    }


    private void injectDependenciesForBean(Bean bean) {
        Class<?> targetClass = bean.getType();
        Field[] fields = targetClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Autowired.class)) {
                Class<?> clazz = field.getType();
                try {
                    field.set(bean.getInstance(), getBeanInstance(clazz));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else if (field.isAnnotationPresent(PersistenceContext.class)) {
                try {
                    field.set(bean.getInstance(), this.dbContext);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public <T> T getBeanInstance(Class<T> clazz) {
        for (Bean bean : beans) {
            if (clazz.isAssignableFrom(bean.getType())) {
                return clazz.cast(bean.getInstance());
            }
        }
        return null;
    }

    public <T extends Annotation> List<Bean> getBeansOfType(Class<T> tClass) {
        List<Bean> controllerBeans = new ArrayList<>();
        this.beans.forEach(bean -> {
            if (bean.getType().isAnnotationPresent(tClass))
                controllerBeans.add(bean);
        });

        return controllerBeans;
    }
}
