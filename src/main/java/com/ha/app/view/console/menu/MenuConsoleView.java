package com.ha.app.view.console.menu;

import com.ha.app.annotations.Autowired;
import com.ha.app.annotations.ui.View;
import com.ha.app.annotations.ui.ViewFeature;
import com.ha.app.controllers.ItemController;
import com.ha.app.controllers.MenuController;
import com.ha.app.entities.Item;
import com.ha.app.entities.Menu;
import com.ha.app.exceptions.ApplicationException;
import com.ha.app.exceptions.ErrorInfo;
import com.ha.app.helpers.InputHelper;
import com.ha.app.view.console.ConsoleView;

import java.util.Set;

@View
public class MenuConsoleView extends ConsoleView {
    @Autowired
    MenuController menuController;

    @Autowired
    ItemController itemController;
    private InputHelper inputHelper = InputHelper.getInstance();

    public MenuConsoleView(Class childClass) {
        super(childClass);
    }

    @ViewFeature
    public void viewMenuById() {
        System.out.println("Please enter ID of the desired Menu");
        int menuId;
        menuId = inputHelper.getInteger();

        Menu selectedMenu = menuController.get(menuId);
        System.out.println(selectedMenu);
    }

    @ViewFeature
    public void viewAllItem() {
        Set<Menu> menus = this.menuController.getAll();
        System.out.println("All items are displayed as below: ");
        menus.forEach(menu -> {
            System.out.println(menu);
        });
    }

    @ViewFeature
    public void createMenu() {
        System.out.println("Please enter ID of the menu: ");
        int menuId = inputHelper.getInteger();

        System.out.println("Please enter name of the menu: ");
        String menuName = inputHelper.getLine();

        Menu menu = new Menu();
        menu.setId(menuId);
        menu.setName(menuName);
        try {
            this.menuController.create(menu);
            System.out.println("New menu has been created");
        } catch (ApplicationException applicationException) {
            ErrorInfo errorInfo = new ErrorInfo();
            errorInfo.setErrorId("CreateNewMenuView");

            applicationException.addErrorInfo(errorInfo);
            throw applicationException;
        }
    }

    @Override
    protected ConsoleView getCurrentView() {
        return this;
    }
}
