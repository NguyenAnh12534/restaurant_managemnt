package com.ha.app.ui.inputs;

import com.ha.app.annotations.Controller;
import com.ha.app.commons.depedencyinjection.Bean;
import com.ha.app.commons.depedencyinjection.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class ControllerMapping {
    List<Bean> controllerBeans = new ArrayList<>();

    public ControllerMapping(ApplicationContext applicationContext) {
        getControllerBeansFromContext(applicationContext);
    }

    private void getControllerBeansFromContext(ApplicationContext applicationContext) {
        this.controllerBeans = applicationContext.getBeansOfType(Controller.class);
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
