package com.ha.app;

import com.ha.app.annotations.ui.View;
import com.ha.app.commons.depedencyinjection.ApplicationContext;
import com.ha.app.exceptions.ExceptionHandler;
import com.ha.app.view.MainScreen;


/**
 * This is the entry point of the application
 */
public class RestaurantApplication {
    private ApplicationContext applicationContext;

    public void start() {
        MainScreen mainScreen = new MainScreen(this.applicationContext.getBeansOfType(View.class));
        ExceptionHandler exceptionHandler = ExceptionHandler.getInstance();
        while (true) {
            try {
                mainScreen.render();
            } catch (Throwable e) {
                exceptionHandler.notifyUser(e);
                exceptionHandler.notifyNonUser(e);
            }
        }

    }

    public static void main(String[] args) {

        RestaurantApplication restaurantApplication = new RestaurantApplication();
        restaurantApplication.applicationContext = new ApplicationContext(restaurantApplication.getClass().getPackageName());

        restaurantApplication.start();
    }
}
