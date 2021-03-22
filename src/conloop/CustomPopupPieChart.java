package conloop;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

/**
 *
 * @author PatoWhiz
 */
public class CustomPopupPieChart {

    private final Popup popup;
    private final VBox vbox;
    private final Label lblPieTag;
    private final Label lblPieValue;

    public CustomPopupPieChart() {
        popup = new Popup();
        vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: white;-fx-padding: 10 10 10 10;");
        lblPieTag = new Label();
        lblPieValue = new Label();
        vbox.getChildren().addAll(lblPieTag, lblPieValue);
        popup.getContent().addAll(vbox);
    }

    public void setPieTag(String strName) {
        lblPieTag.setText(strName);
    }

    public void setPieValue(String strValue) {
        lblPieValue.setText(strValue);
    }

    public Popup getPopup() {
        return popup;
    }

}
