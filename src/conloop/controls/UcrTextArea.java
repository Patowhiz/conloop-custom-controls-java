package conloop.controls;

import javafx.scene.control.TextArea;

/**
 *
 * @author PatoWhiz 12/04/2018
 */
public class UcrTextArea extends UcrTextInput {

    protected TextArea txtArea;
    public boolean bValidate;

    public UcrTextArea(TextArea newTxtArea) {
        this.txtArea = newTxtArea;
        bValidate = false;
    }

    @Override
    public TextArea getControl() {
        return this.txtArea;
    }

    @Override
    public String getValue() {
        return txtArea.getText();
    }

    @Override
    public void setValue(Object objValue) {
        if (objValue == null) {
            txtArea.setText("");
        } else {
            txtArea.setText(String.valueOf(objValue));
        }

    }

    public void setBackColor(EnumColorType clsColorType) {
        txtArea.setStyle("-fx-control-inner-background: " + clsColorType.getColorTypeValue());
    }

    public void clearBackColor() {
        //set inner style instead of using stylesheet class. Improves speed
        txtArea.setStyle("-fx-control-inner-background: " + EnumColorType.WHITE.getColorTypeValue());
    }

    public boolean isEmpty() {
        return getValue().length() <= 0;
    }

    public void Clear() {
        bValidate = false;
        setValue("");//TODO I'm not sure whether to use txtField.clear() or this
        //txtField.clear();
        setBackColor(EnumColorType.WHITE);
        bValidate = true;
    }

}
