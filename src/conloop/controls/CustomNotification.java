package conloop.controls;

import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author PatoWhiz
 */
public class CustomNotification {

    public static String applicationName = "Conloop";

    public static void showAccessDeniedNotification() {
        if (applicationName == null) {
            applicationName = "";
        }
        Notifications notificationBuilder = Notifications.create()
                .title(applicationName)
                .text("Oops! Access Denied")
                .hideAfter(Duration.seconds(3))
                .position(Pos.TOP_RIGHT);
        notificationBuilder.darkStyle();
        notificationBuilder.hideCloseButton();
        notificationBuilder.showInformation();

    }//end method

    public static void showNotification(String contentText) {
        Notifications notificationBuilder = Notifications.create()
                .title(applicationName)
                .text(contentText)
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT);
        notificationBuilder.darkStyle();
        //notificationBuilder.hideCloseButton();
        notificationBuilder.showInformation();

    }//end method

}//end class
