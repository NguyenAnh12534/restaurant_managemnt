package com.ha.app;

import com.ha.app.annotations.ui.View;
import com.ha.app.ui.inputs.ControllerMapping;
import com.ha.app.ui.inputs.UserInputHandler;
import com.ha.app.commons.depedencyinjection.ApplicationContext;
import com.ha.app.view.MainScreen;

/**
 * This is the entry point of the application
 */
public class RestaurantApplication {
    private ApplicationContext applicationContext;

    public void start() {
        boolean isTerminated = false;
        ControllerMapping  controllerMapping = new ControllerMapping(this.applicationContext);

        while(true){
            MainScreen mainScreen = new MainScreen(this.applicationContext.getBeansOfType(View.class));
            mainScreen.render();
            isTerminated = true;
        }
    }

    public static void main(String[] args)   {
        RestaurantApplication restaurantApplication = new RestaurantApplication();
        restaurantApplication.applicationContext = new ApplicationContext(restaurantApplication.getClass().getPackageName());

        restaurantApplication.start();
    }
}
