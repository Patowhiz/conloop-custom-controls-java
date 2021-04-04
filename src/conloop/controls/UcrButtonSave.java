package conloop.controls;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;

/**
 *
 * @author PatoWhiz 16/04/2018
 */
public class UcrButtonSave extends UcrCoreControl {

    protected Button button;
    protected List<UcrCoreControl> lstValidateControls;
    //when set true, will disable this button on validation of linked controls
    //when set to false it deactivates the checking of valid values by this button
    public boolean bValidateControls;

    public UcrButtonSave(Button btn) {
        this.button = btn; 
        this.bValidateControls = true;
    }

    @Override
    public void setValue(Object objValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Button getControl() {
        return button;
    }

    /**
     * makes this button to listen to the validValueProperty of the control
     * passed. gets disabled if the validValueProperty of
     * ucrLinkedValidateControl is set to false
     *
     * @param ucrLinkedValidateControl
     */
    public void validateControl(UcrCoreControl ucrLinkedValidateControl) {
        if (lstValidateControls == null) {
            validateControl(new ArrayList<>());
        }
        lstValidateControls.add(ucrLinkedValidateControl);
        setValidateControlListener(ucrLinkedValidateControl);
        //triggers validation immediately
        ucrLinkedValidateControl.validateValue();
    }//end method

    public void validateControl(List<UcrCoreControl> lstNewValidateControls) {
        lstValidateControls = lstNewValidateControls;
        for (UcrCoreControl ucr : lstValidateControls) {
            setValidateControlListener(ucr);
            //triggers validation immediately
            ucr.validateValue();
        }

    }

    /**
     * sets the controls validValueProperty listener which this button will use
     * to disable itself
     *
     * @param ucrLinkedValidateControl
     */
    private void setValidateControlListener(UcrCoreControl ucrLinkedValidateControl) {
        ucrLinkedValidateControl.validValueProperty.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (bValidateControls) {
                    if (newValue) {
                        //enable thebutton
                        setDisable(false);
                        //check if the rest of the controls are also valid. If not disable this button and break
                        for (UcrCoreControl ucr : lstValidateControls) {
                            if (!ucr.validValueProperty.get()) {
                                setDisable(true);
                                break;
                            }//end if
                        }//end for loop 

                    } else {
                        //if validValueProperty is false just disable the button
                        setDisable(true);
                    }//end if
                }//end outer if
            }//end method
        });
    }

    /**
     * calls validateValue method of all controls to be validated by this button
     *
     * @return
     */
    public void triggerControlsValidateValue() {
        //check if the rest of the controls are also valid. If not disable this button and break
        for (UcrCoreControl ucr : lstValidateControls) {
            ucr.validateValue();
        }//end for loop 
    }

    public List<UcrCoreControl> getLstValidateControls() {
        return Collections.unmodifiableList(this.lstValidateControls);
    }

}//end class
