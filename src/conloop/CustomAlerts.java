package conloop;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Window;
import javax.swing.JOptionPane;

/**
 *
 * @author PatoWhiz 09/04/2018
 */
public class CustomAlerts {

    private static Alert createAlertControl(Window window, Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);

        if (headerText != null && !headerText.isEmpty()) {
            alert.setHeaderText(headerText);
        } else {
            //alert.setHeaderText(null);
        }
        if (contentText != null && !contentText.isEmpty()) {
            alert.setContentText(contentText);
        }

        if (window != null) {
            alert.initOwner(window);
        }

        return alert;
    }

    public static boolean showConfirmAlert(Window window, String title, String headerText, String contentText) {

        Alert alert = createAlertControl(window, Alert.AlertType.CONFIRMATION, title, headerText, contentText);
        ButtonType btnTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType btnTypeCancel = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(btnTypeYes, btnTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == btnTypeYes;
    }

    public static boolean showWarningAlert(Window window, String title, String headerText, String contentText) {

        Alert alert = createAlertControl(window, Alert.AlertType.WARNING, title, headerText, contentText);

        ButtonType btnTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType btnTypeCancel = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(btnTypeYes, btnTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == btnTypeYes) {
            return true;
        } else {
            return false;
        }//end inner if
    }

    public static void showMessageAlert(Window window, String title, String headerText, String contentText) {
        Alert alert = createAlertControl(window, Alert.AlertType.INFORMATION, title, headerText, contentText);
        alert.showAndWait();
    }

    public static void showMessageAlert(Window window, String contentText) {
        showMessageAlert(window, "", "", contentText);
    }
    
     public static void showMessageAlert(  String contentText) {
        showMessageAlert(null, "", "", contentText);
    }

    public static void showErrorAlert(Window window, String title, String headerText, String contentText) {
        Alert alert = createAlertControl(window, Alert.AlertType.ERROR, title, headerText, contentText);
        alert.showAndWait();
    }

    public static void showErrorAlert(Window window, String headerText, String contentText) {
        showErrorAlert(window, "Error", headerText, contentText);
    }

    public static void showErrorAlert(Window window, String contentText) {
        showErrorAlert(window, "Error", "Exception", contentText);
    }
    
    public static void showErrorAlert(  String contentText) {
        showErrorAlert(null, "Error", "Exception", contentText);
    }

    public static void showDeveloperErrorAlert(String contentText) {
        showErrorAlert(null, "Error", "Developer Error",   contentText);
    }

    public static void showDeveloperErrorAlert(String contentText, Exception ex) {
        showErrorExceptionAlert(null, "Error", "Developer Error", contentText, ex);
    }

    public static void showErrorExceptionAlert(String contentText, Exception ex) {
        showErrorExceptionAlert(null, "Error", "Error", contentText, ex);
    }

    public static void showErrorExceptionAlert(Window window, String contentText, Exception ex) {
        showErrorExceptionAlert(window, "Error", "Error", contentText, ex);
    }

    public static void showErrorExceptionAlert(Window window, String title, String headerText, String contentText, Exception ex) {

        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);

        showErrorExceptionAlert(window, title, headerText, contentText, sw.toString());

//        Alert alert = createAlertControl(window, Alert.AlertType.ERROR, title, headerText, contentText);
//
//        Label label = new Label("The exception stacktrace was:");
//
//        TextArea textArea = new TextArea(sw.toString());
//        textArea.setEditable(false);
//        textArea.setWrapText(true);
//
//        textArea.setMaxWidth(Double.MAX_VALUE);
//        textArea.setMaxHeight(Double.MAX_VALUE);
//        GridPane.setVgrow(textArea, Priority.ALWAYS);
//        GridPane.setHgrow(textArea, Priority.ALWAYS);
//
//        GridPane expContent = new GridPane();
//        expContent.setMaxWidth(Double.MAX_VALUE);
//        expContent.add(label, 0, 0);
//        expContent.add(textArea, 0, 1);
//
//        //Set expandable Exception into the dialog pane.
//        alert.getDialogPane().setExpandableContent(expContent);
//
//        alert.showAndWait();
    }

    public static void showErrorExceptionAlert(Window window, String title, String headerText, String contentText, String ex) {
        Alert alert = createAlertControl(window, Alert.AlertType.ERROR, title, headerText, contentText);

        // Create expandable Exception.
        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(ex);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        //Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();

    }

    public static String showTextInputDialog(Window window, String title, String headerText, String contentText, String defaultValue) {
        TextInputDialog dialog = new TextInputDialog(defaultValue);
        dialog.setTitle(title);
        if (window != null) {
            dialog.initOwner(window);
        }

        if (headerText != null && !headerText.isEmpty()) {
            dialog.setHeaderText(headerText);
        }

        if (contentText != null && !contentText.isEmpty()) {
            dialog.setContentText(contentText);
        }

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return result.get();
        } else {
            return "";
        }

        // The Java 8 way to get the response value (with lambda expression).
        //Left here for reference
        //result.ifPresent(name -> System.out.println("Your name: " + name));
    }

    public static void showErrorExceptionJOptionPane(String contentText, Exception ex) {
        JOptionPane.showMessageDialog(null, contentText + " : " + ex);
    }

    public static void showMessageJOptionPane(String contentText) {
        JOptionPane.showMessageDialog(null, contentText);
    }

    public static void showChoiceDialog() {
        //---------------------------------------------------------------------
        //TODO. NOT CODED YET. THIS IS JUST A BOILERPLATE
        List<String> choices = new ArrayList<>();
        choices.add("a");
        choices.add("b");
        choices.add("c");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("b", choices);
        dialog.setTitle("Choice Dialog");
        dialog.setHeaderText("Look, a Choice Dialog");
        dialog.setContentText("Choose your letter:");

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("Your choice: " + result.get());
        }

// The Java 8 way to get the response value (with lambda expression).
        result.ifPresent(letter -> System.out.println("Your choice: " + letter));
    }

}//end class
