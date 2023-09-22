package com.ha.app;


import com.ha.app.annotations.ui.View;
import com.ha.app.commons.depedencyinjection.ApplicationContext;
import com.ha.app.exceptions.ExceptionHandler;
import com.ha.app.exceptions.ExitExcpetion;
import com.ha.app.view.MainScreen;
import java.util.function.Consumer;



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
            } catch (ExitExcpetion exitExcpetion) {
                System.out.println("Stop operation");
            } catch (Throwable e) {
                exceptionHandler.notifyUser(e);
                exceptionHandler.notifyNonUser(e);
            }
        }

    }

    public static void main(String[] args) {
        Consumer<Integer> myFunction = (a) -> {
            System.out.println(a);
        };
        myFunction.accept(2);
        RestaurantApplication restaurantApplication = new RestaurantApplication();
        restaurantApplication.applicationContext = new ApplicationContext(restaurantApplication.getClass().getPackageName());

        restaurantApplication.start();
    }
}
