package conloop.controls;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

/**
 *
 * @author Patowhiz 26/05/2019 02:42 PM
 */
public class UcrRadioButton extends UcrCoreControl {

    private RadioButton rdButton;

    public UcrRadioButton(RadioButton rdButton) {
        this.rdButton = rdButton;
        setDefaultListeners();
    }

    private void setDefaultListeners() {
        addValueChangedListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
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
            rdButton.setSelected(((int) objValue) > 0);
        } else {
            rdButton.setSelected((boolean) objValue);
        }

    }

    @Override
    public Boolean getValue() {
        return rdButton.isSelected();
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
    public RadioButton getControl() {
        return rdButton;
    }

    @Override
    public Boolean getDBValue() {
        return dbValue == null ? false : (boolean) dbValue;
    }

    public void setToggleGroup(ToggleGroup rbToggleGroup) {
        getControl().setToggleGroup(rbToggleGroup);
    }
    
     public ToggleGroup getToggleGroup() {
        return getControl().getToggleGroup();
    }
}//end class
