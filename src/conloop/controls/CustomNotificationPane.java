package conloop.controls;

import conloop.StringsUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.controlsfx.control.NotificationPane;

/**
 * A class for creating notificationPanes to be used In a universal way
 *
 * @author Pato
 */
public class CustomNotificationPane {

    private final NotificationPane notificationPane;
    private ImageView image;
    public final SimpleStringProperty infoNotification = new SimpleStringProperty();
    public final SimpleStringProperty errorNotification = new SimpleStringProperty();
    public final SimpleStringProperty wariningNotification = new SimpleStringProperty();

    public CustomNotificationPane(HBox parent) {
        this.notificationPane = new NotificationPane();
        createNotificationPane(parent);

        infoNotification.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (StringsUtil.isNotNullOrEmpty(newValue)) {
                showNotificationPaneInfo(newValue);
            }
        });

        errorNotification.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (StringsUtil.isNotNullOrEmpty(newValue)) {
                showNotificationPaneError(newValue);
            }
        });

        wariningNotification.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (StringsUtil.isNotNullOrEmpty(newValue)) {
                showNotificationPaneWarning(newValue);
            }
        });
    }

    private void createNotificationPane(HBox parent) {

        //Instantiate the notificationPane
        notificationPane.setShowFromTop(false);
        notificationPane.getStyleClass().add(NotificationPane.STYLE_CLASS_DARK);

        //Create a container into which the notificationPane
        //Will show up in
        HBox hbContents = new HBox();

        //Set the padding insets to give the sapce in which
        //The notificationPane will show in
        hbContents.setPadding(new Insets(5, 0, 0, 0));

        /**
         * Set the preferred width. this listener makes sure that the HBOX
         * wrapped into the notificationPane to show contents Also grows as the
         * form grows
         */
        parent.widthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            //SetPrefWidth according to the parent width
            hbContents.setPrefWidth(parent.getWidth());
            // hbContents.setPrefHeight(parent.getHeight());
        });

        // Wrap the container into the notification pane
        notificationPane.setContent(hbContents);
        //Then add the notificationPane into the hbNotify container
        parent.getChildren().addAll(notificationPane);

    }//end of method

    public NotificationPane getNotificationPane() {
        return notificationPane;
    }//end of method

    /**
     * this method pops up the notificationPane
     *
     * @param theMessage message to display
     * @param enumIcon
     */
    public void showNotificationPane(String theMessage, EnumIcons enumIcon) {
        image = new ImageView(enumIcon.getUrl());
        notificationPane.setGraphic(image);
        notificationPane.show(theMessage);
    } //end method

    public void showNotificationPaneInfo(String theMessage) {
        showNotificationPane(theMessage, EnumIcons.ICONINFO);
    } //end method

    public void showNotificationPaneError(String theMessage) {
        showNotificationPane(theMessage, EnumIcons.ICONERROR);
    } //end method

    public void showNotificationPaneWarning(String theMessage) {
        showNotificationPane(theMessage, EnumIcons.ICONWARNING);
    } //end method

    public void showNotificationPaneQuestion(String theMessage) {
        showNotificationPane(theMessage, EnumIcons.ICONQUESTION);
    } //end method

    public void hideNotificationPane() {
        if (notificationPane.isShowing()) {
            notificationPane.hide();
        }//end id
    }//end method

    public EnumIcons getICONERROR() {
        return EnumIcons.ICONERROR;
    }

    public EnumIcons getICONWARNING() {
        return EnumIcons.ICONWARNING;
    }

    public EnumIcons getICONQUESTION() {
        return EnumIcons.ICONQUESTION;
    }

    public EnumIcons getICONINFO() {
        return EnumIcons.ICONINFO;
    }

    public static enum EnumIcons {

        ICONERROR(CustomNotificationPane.class.getResource("icons/error.png").toExternalForm()),
        ICONWARNING(CustomNotificationPane.class.getResource("icons/warning.png").toExternalForm()),
        ICONQUESTION(CustomNotificationPane.class.getResource("icons/question.png").toExternalForm()),
        ICONINFO(CustomNotificationPane.class.getResource("icons/info.png").toExternalForm());

        private final String url;

        private EnumIcons(String url) {
            this.url = url;
        }

        /**
         * returns string representation of the resource url of the resource
         *
         * @return string url
         */
        public String getUrl() {
            return this.url;
        }

    }
}//end class
