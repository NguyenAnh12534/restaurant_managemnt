package com.ha.app.view;

import com.ha.app.commons.depedencyinjection.Bean;
import com.ha.app.exceptions.InvalidInputException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainScreen {
    List<Renderable> crudViews = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    private int currentViewIndex;

    public MainScreen(List<Bean> crudViewBeans) {
        initializeViews(crudViewBeans);
    }

    private void initializeViews(List<Bean> crudViewBeans) {
        crudViewBeans.forEach(viewBean -> {
            try {
                crudViews.add((Renderable) viewBean.getInstance());
            }catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    public void render() {
        selectCrudView();
        renderSelectView();
    }

    private void selectCrudView() {
        System.out.println("Please choose views: ");
        for(int i = 0; i < crudViews.size(); i++) {
            System.out.println( i+1 + ". " + crudViews.get(i).getClass().getSimpleName());
        }
        try{
            int chosenIndex = scanner.nextInt();
            if(chosenIndex >= crudViews.size()) {
                throw new InvalidInputException("Chosen number is too large");
            }
            this.currentViewIndex = chosenIndex;
        }catch (InvalidInputException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void renderSelectView() {
        this.crudViews.get(currentViewIndex).render();
    }
}
