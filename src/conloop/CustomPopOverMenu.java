package conloop;

import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 *
 * @author Patowhiz 01/03/2019 11:00 AM
 */
public class CustomPopOverMenu {

    private final CustomPopOver customPopOver;
    private final VBox vboxContentNode;
    private MenuActionEventListener menuActionlistener;
    private EnumControlType defaultEnumControlType;
    public boolean bHideMenuOnClick = true;

    public CustomPopOverMenu() {
        customPopOver = new CustomPopOver();
        vboxContentNode = new VBox();
        defaultEnumControlType = EnumControlType.BUTTON;
        setContentNode();
        customPopOver.setArrowLocationToTopCenter();
    }

    public void setDefaultEnumControlType(EnumControlType defaultEnumControlType) {
        this.defaultEnumControlType = defaultEnumControlType;
    }

    private void setContentNode() {
        vboxContentNode.setAlignment(Pos.CENTER);
        vboxContentNode.setSpacing(10d);
        vboxContentNode.setPadding(new Insets(8, 8, 8, 8));
        customPopOver.setContentNode(vboxContentNode);
    }

    public void setTitle(String title) {
        customPopOver.setTitle(title);
    }

    public void setMenuItems(ObservableList<String> menuItemLabels) {
        clearMenuItems();
        addMenuItems(menuItemLabels);
    }

    public void addMenuItems(String... menuItemLabels) {
        addMenuItems(FXCollections.observableList(Arrays.asList(menuItemLabels)));
    }

    public void addMenuItems(ObservableList<String> menuItemLabels) {
        menuItemLabels.forEach((item) -> {
            addMenuItem(item, defaultEnumControlType);
        });
    }

    public void addMenuItem(String menuItemLabel, EnumControlType controlType) {

        if (null == controlType) {
            CustomAlerts.showDeveloperErrorAlert("Does not accept such control type");
        } else {
            switch (controlType) {
                case BUTTON:
                    Button btn = new Button(menuItemLabel);
                    btn.setOnAction((ActionEvent event) -> {
                        if (menuActionlistener != null) {
                                  //TODO. Remove this and use the CustomEventListener built as at 06/08/2019
                            menuActionlistener.onActionChangedEvent(btn);
                            if (bHideMenuOnClick){
                                 hide();
                            }      
                        }//end if
                    });
                    HBox.setHgrow(btn, Priority.ALWAYS);
                    btn.setMaxWidth(Double.MAX_VALUE);
                    vboxContentNode.getChildren().add(btn);
                    break;
                case LABEL:
                    Label lbl = new Label(menuItemLabel);
                    lbl.setOnMouseClicked((MouseEvent event) -> {
                        if (menuActionlistener != null) {
                            //TODO In future. Was done on 09/03/2019 03:33
                            //menuActionlistener.onActionChangedEvent(lbl);
                        }
                    });
                    HBox.setHgrow(lbl, Priority.ALWAYS);
                    lbl.setMaxWidth(Double.MAX_VALUE);
                    vboxContentNode.getChildren().add(lbl);
                    break;
                default:
                    CustomAlerts.showDeveloperErrorAlert("Does not accept such control type");
                    break;
            }
        }

    }

    public void clearMenuItems() {
        vboxContentNode.getChildren().clear();
    }

    public void show(Node owner) {
        customPopOver.show(owner);    
    }
    
     public void hide() {
        customPopOver.hide();    
    }

    public CustomPopOver getCustomPopOver() {
        return customPopOver;
    }

    public void setMenuItemEventListener(MenuActionEventListener listener) {
        this.menuActionlistener = listener;
    }


          //TODO. Remove this and use the CustomEventListener built as at 06/08/2019
    public interface MenuActionEventListener {
        //TODO. Remove this and use the CustomEventListener built as at 06/08/2019
        public void onActionChangedEvent(Button menuItem); 
    }

    public static enum EnumControlType {
        BUTTON,
        LABEL;
    }

}//end class
