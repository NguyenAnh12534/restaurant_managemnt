package com.ha.app.view;

import com.ha.app.commons.depedencyinjection.Bean;
import com.ha.app.enums.errors.ErrorSeverity;
import com.ha.app.enums.errors.ErrorType;
import com.ha.app.exceptions.ApplicationException;
import com.ha.app.exceptions.ErrorInfo;
import com.ha.app.exceptions.InvalidInputException;
import com.ha.app.helpers.InputHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handle View Selection
 */
public class MainScreen {
    List<Renderer> crudViews = new ArrayList<>();
    private int currentViewIndex;
    private InputHelper inputHelper = InputHelper.getInstance();

    public MainScreen(List<Bean> crudViewBeans) {
        initializeViews(crudViewBeans);
    }

    private void initializeViews(List<Bean> crudViewBeans) {
        crudViewBeans.forEach(viewBean -> {
            try {
                crudViews.add((Renderer) viewBean.getInstance());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    public void render() {
        try {
            selectCrudView();
            renderSelectView();
        } catch (InvalidInputException exception) {
            ApplicationException applicationException = new ApplicationException();

            ErrorInfo errorInfo = new ErrorInfo();

            errorInfo.setCause(exception);
            errorInfo.setErrorType(ErrorType.CLIENT);
            errorInfo.setErrorSeverity(ErrorSeverity.WARNING);
            errorInfo.setUserErrorDescription(exception.getMessage());

            applicationException.addErrorInfo(errorInfo);

            throw applicationException;
        }
    }

    private void selectCrudView() {
        System.out.println("\nPlease choose views");
        for (int i = 0; i < crudViews.size(); i++) {
            System.out.println(i + 1 + ". " + crudViews.get(i).getClass().getSimpleName());
        }

        int chosenIndex = inputHelper.selectIndexOfCollection(this.crudViews);
        this.currentViewIndex = chosenIndex - 1;
    }

    private void renderSelectView() {
        Renderer selectedView = this.crudViews.get(currentViewIndex);
        System.out.println("\nCurrent view: " + selectedView.getClass().getSimpleName());
        selectedView.render();
    }
}
