package conloop;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;

/**
 *
 * @author PatoWhiz 12/04/2018
 */
public class UcrCheckBox extends UcrCoreControl {

    private CheckBox chkBox;

    public UcrCheckBox(CheckBox chkBox) {
        this.chkBox = chkBox;
        setListeners();
        setListeners();
    }

    private void setListeners() {
        addValueChangedListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (bSuppressEvents) {
                return;
            }
            //check if value is valid
            if (bValidate) {
                if (validateValue()) {
                    raiseValueChangedEvent();
                    setBackColor(EnumColorType.WHITE);
                } else {
                    setBackColor(EnumColorType.RED);
                }
            } else {
                raiseValueChangedEvent();
            }
            
            updateLinkedControls();
        });
    }

    /**
     * the value changed for CheckBox is selectedProperty
     *
     * @param clsChangelistener
     */
    public void addValueChangedListener(ChangeListener<Boolean> clsChangelistener) {
        getControl().selectedProperty().addListener(clsChangelistener);
    }

    /**
     * accepts boolean, integer(1 or 0 only)
     *
     * @param objValue
     */
    @Override
    public void setValue(Object objValue) {
        if (objValue instanceof Integer) {
            chkBox.setSelected(((int) objValue) > 0);
        } else {
            chkBox.setSelected((boolean) objValue);
        }

    }

    @Override
    public Boolean getValue() {
        return chkBox.isSelected();
    }

    /**
     * returns 1 if check box is selected and 0 otherwise
     *
     * @return
     */
    public int getValueAsInteger() {
        if (getValue()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public CheckBox getControl() {
        return chkBox;
    }

    @Override
    public Boolean getDBValue() {
        return dbValue == null ? false : (boolean) dbValue;
    }

}//end class
