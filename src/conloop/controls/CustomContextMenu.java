package conloop.controls;

import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Patowhiz 09/03/2019
 */
public class CustomContextMenu {

    private final ContextMenu context;
    private EnumMenuItemType defaultEnumMenuItemlType;
    private MenuActionEventListener menuActionlistener;

    public CustomContextMenu() {
        context = new ContextMenu();
        defaultEnumMenuItemlType = EnumMenuItemType.LABEL;
    }

    public void setDefaultEnumMenuItemlType(EnumMenuItemType defaultEnumMenuItemlType) {
        this.defaultEnumMenuItemlType = defaultEnumMenuItemlType;
    }

    public void setMenuItems(ObservableList<String> menuItems) {
        clearMenuItems();
        addMenuItems(menuItems);
    }

    public CustomContextMenu addMenuItems(String... menuItems) {
        addMenuItems(FXCollections.observableList(Arrays.asList(menuItems)));
        return this;
    }

    public void addMenuItems(ObservableList<String> menuItems) {
        menuItems.forEach((item) -> {
            addMenuItem(item, defaultEnumMenuItemlType);
        });
    }

    public void addMenuItem(String menuItemLabel, EnumMenuItemType controlType) {

        if (null == controlType) {
            CustomAlerts.showDeveloperErrorAlert("Does not accept such control type");
        } else {
            switch (controlType) {
                case LABEL:
                    MenuItem item = new MenuItem(menuItemLabel);
                    item.setOnAction((ActionEvent event) -> {
                        if (menuActionlistener != null) {
                            menuActionlistener.onActionChangedEvent(item);
                        }
                    });
                    context.getItems().add(item);
                    break;
                default:
                    CustomAlerts.showDeveloperErrorAlert("Does not accept such control type");
                    break;
            }
        }

    }

    public void clearMenuItems() {
        context.getItems().clear();
    }

    public void show(Node anchor, double screenX, double screenY) {
        context.show(anchor, screenX, screenY);
    }

    public void show(Node anchor, MouseEvent event) {
        context.show(anchor, event.getScreenX(), event.getScreenY());
    }

    public void setMenuItemEventListener(MenuActionEventListener listener) {
        this.menuActionlistener = listener;
    }

    public interface MenuActionEventListener {

        //TODO
        public void onActionChangedEvent(MenuItem menuItem);//In future we can have the parameter be our custom object
    }

    public static enum EnumMenuItemType {
        LABEL;
    }

}//end class
