package com.ha.app;

import com.ha.app.data.DbContext;
import com.ha.app.ui.inputs.ControllerMapping;
import com.ha.app.ui.inputs.UserInputHandler;
import com.ha.app.utils.depedency_injection.ApplicationContext;

/**
 * Hello world!
 */
public class RestaurantApplication {
    private ApplicationContext applicationContext;
    private DbContext dbContext;

    public void start() {
        boolean isTerminated = false;
        ControllerMapping  controllerMapping = new ControllerMapping(this.applicationContext);
        UserInputHandler userInputHandler = new UserInputHandler(controllerMapping);

        while(!isTerminated){
            userInputHandler.handleUserInput();
            isTerminated = true;
        }
    }

    public void useDbContext(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public static void main(String[] args)   {
        RestaurantApplication restaurantApplication = new RestaurantApplication();
        restaurantApplication.applicationContext = new ApplicationContext(restaurantApplication.getClass().getPackageName());

        restaurantApplication.start();
    }
}
