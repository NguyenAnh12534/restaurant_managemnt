package com.ha.app.ui.inputs;

import com.ha.app.utils.depedency_injection.Bean;
import com.ha.app.utils.depedency_injection.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class ControllerMapping {
    List<Bean> controllerBeans = new ArrayList<>();

    public ControllerMapping(ApplicationContext applicationContext) {
        getControllerBeansFromContext(applicationContext);
    }

    private void getControllerBeansFromContext(ApplicationContext applicationContext) {
        this.controllerBeans = applicationContext.getControllerBeans();
    }

    public List<Bean> getControllerBeans() {
        return this.controllerBeans;
    }

    /**
     *
     * @param index start at 1
     * @return a bean of controller
     *
     */
    public Bean getControllerBeanAt(int index) {
        return this.controllerBeans.get(index);
    }

    public void showControllerBeans() {
        controllerBeans.forEach(controllerBean -> {
            System.out.println(controllerBean.getName());
        });
    }


}
