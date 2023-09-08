package com.ha.app;

import com.ha.app.data.DbContext;
import com.ha.app.ui.inputs.ControllerMapping;
import com.ha.app.ui.inputs.UserInputHandler;
import com.ha.app.utils.depedency_injection.ApplicationContext;

/**
 * This is the entry point of the application
 */
public class RestaurantApplication {
    private ApplicationContext applicationContext;

    public void start() {
        boolean isTerminated = false;
        ControllerMapping  controllerMapping = new ControllerMapping(this.applicationContext);
        UserInputHandler userInputHandler = new UserInputHandler(controllerMapping);

        while(!isTerminated){
            userInputHandler.handleUserInput();
            isTerminated = true;
        }
    }

    public static void main(String[] args)   {
        String str = "asdas";
        Object ob = str;
        System.out.println(ob.getClass().getSimpleName());
        System.out.println(str.getClass().getSimpleName());

        RestaurantApplication restaurantApplication = new RestaurantApplication();
        restaurantApplication.applicationContext = new ApplicationContext(restaurantApplication.getClass().getPackageName());

        restaurantApplication.start();
    }
}
