package conloop;

import java.io.IOException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.util.Pair;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities; 

/**
 *
 * @author PatoWhiz 17/04/2018
 */
public class UcrDialog {

    private final FXFormLoader frmLoader;
    private String title;
    private String headerText;
    private Window window;

    public UcrDialog(Class classLoader) {
        frmLoader = new FXFormLoader(classLoader);
    }

    public UcrDialog(Class classLoader, Window window, String filePathAndName, String title) {
        this(classLoader);
        setWindow(window);
        setFilePathAndName(filePathAndName);
        setTitle(title);
    }

    public UcrDialog(Class classLoader, Window window, String filePathAndName, String title, String headerText) {
        this(classLoader,window, filePathAndName, title);
        setHeaderText(headerText);
    }

    public UcrDialog(Class classLoader , Window window, Parent rootNode, String title, String headerText) {
        this(classLoader, window, "", title, headerText);
        setRootNode(rootNode);//for those that aleady have the root node inistialised
    }

    public void setWindow(Window clsWindow) {
        this.window = clsWindow;
    }

    public void setRootNode(Parent rootNode) {
        this.frmLoader.setRootNode(rootNode);
    }

    public void setTitle(String strtitle) {
        this.title = strtitle;
    }

    public void setHeaderText(String strheaderText) {
        this.headerText = strheaderText;
    }

    public void setFilePathAndName(String strfilePathAndName) {
        frmLoader.setFilePathAndName(strfilePathAndName);
    }

    public Initializable getController() throws IOException {
        return frmLoader.getController();
    }

    public void setDialogProperties(Window window, String filePathAndName, String title, String headerText) throws IOException {
        setWindow(window);
        setFilePathAndName(filePathAndName);
        setTitle(title);
        setHeaderText(headerText);
    }//end class

    public void showAndWait() throws IOException {
        // Create the custom dialog.
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(headerText);
        dialog.initOwner(window);
        dialog.initModality(window == null ? Modality.APPLICATION_MODAL : Modality.WINDOW_MODAL);
        dialog.getDialogPane().setContent(frmLoader.getRootNode());

        //The below code is meant to make this dialog closeable via the close x on top of the window
        //For more abou this functionality check on CodeTobeUsedFxDialogSample method BELOW
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);
        dialog.getDialogPane().lookupButton(buttonTypeCancel).setVisible(false);
        dialog.setResultConverter((ButtonType b) -> "");
        dialog.showAndWait();

    }//end method

    /**
     * This is for showing JavaFX content inside the Swing JDialog. All the
     * swing threads have taken care of
     */
    public void showAndWaitSwingJDialog() {
        //This method is invoked on the EDT thread
        SwingUtilities.invokeLater(() -> {

            final JFXPanel fxPanel = new JFXPanel();
            final JDialog dialog = new JDialog();
            dialog.setTitle(title);
            dialog.setModalityType(java.awt.Dialog.DEFAULT_MODALITY_TYPE);

            Platform.runLater(() -> {
                try {
                    // This is invoked on the
                    //JavaFX application thread / UI thread
                    final Scene scene = new Scene(frmLoader.getRootNode());
                    fxPanel.setScene(scene);

                    //I have chosen this over onHidden event handler
                    //because the form loaded could also want
                    //to utilise the WINDOW_HIDDEN event
                    scene.getWindow().addEventHandler(WindowEvent.WINDOW_HIDDEN, (WindowEvent event) -> {
                        SwingUtilities.invokeLater(() -> {
                            dialog.dispose();
                        });
                    });// end of event handler

                    //After  rendering the JavaFX Scene in JavaFX thread
                    //now go ahead and make the JDiaolog visble
                    //This code is placed here so that to await
                    //automatic computation of the scene dimension
                    //during the JavaFX thread
                    //to be used by the JDIalog pack method
                    SwingUtilities.invokeLater(() -> {
                        dialog.add(fxPanel);
                        dialog.pack();
                        dialog.setLocationRelativeTo(null);
                        dialog.setVisible(true);
                    });//end Swing Utilties runLater

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(fxPanel, "Error in loading the form " + ex);
                }//end of catch
            }); //end of platform run later

        });

    }//end of run method

    //Deprecated on ***05/10/2018 02:14 AM*** and left here as reference in favour of the above new showAndWait.
    //Because This pops a dialog which is not closeable "abnormally"
    private void showAndWaitOLD() throws IOException {
        // Create the custom dialog.
        Dialog<?> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(headerText);
        dialog.initOwner(window);
        dialog.initModality(window == null ? Modality.APPLICATION_MODAL : Modality.WINDOW_MODAL);
        dialog.getDialogPane().setContent(frmLoader.getRootNode());
        dialog.showAndWait();
    }

    //TODO
    //USE THIS CODE FOR showAndWait EVENTUALLY
    private void CodeTobeUsedFxDialogSample(Window window) {
        //---------------------------------------------------------------------
        //TODO. NOT CODED YET. THIS IS JUST A BOILERPLATE

        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Login Dialog");
        dialog.setHeaderText("Look, a Custom Login Dialog");

        dialog.initOwner(window);
        //dialog.initModality(Modality.NONE);

// Set the icon (must be included in the project).
        //dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));
// Set the button types.
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

// Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);

// Enable/Disable login button depending on whether a username was entered.
        javafx.scene.control.Button loginButton = (javafx.scene.control.Button) dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

// Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> username.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {  //where dialogButton -> tradiotanlly would have been new Callback<ButtonType, Pair>()
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
        });
    }//end method

}//end class
