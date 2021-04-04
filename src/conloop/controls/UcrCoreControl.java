package conloop.controls;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;
import javafx.stage.Window; 

/**
 *
 * @author PatoWhiz 12/04/2018
 */
public abstract class UcrCoreControl {

    protected List<Control> lstLinkedDisplayControls;
    protected LinkedHashMap<UcrCoreControl, List<ControlCondition>> mapLinkedControls;
    //holds the database value. To be used for showing value change
    protected Object dbValue;
    public final SimpleBooleanProperty validValueProperty = new SimpleBooleanProperty(true);
    public boolean bValidate;
    public boolean bValidateSilently;
    public boolean bValidateEmpty;

    public abstract void setValue(Object objValue);

    public abstract Object getValue();

    //protected abstract void setControl(Control ctrControl);
    public abstract Control getControl();
    public boolean bSuppressEvents = false;
    protected CustomChangeEventListener valueChangedlistener;

    public void raiseValueChangedEvent() {
        if (valueChangedlistener != null) {
            valueChangedlistener.onChangeEvent(this);
        }
    }

    /**
     * This sets the sets the database value and also the actual value. Its
     * calls setValue() internally.
     *
     * @param objValue
     */
    public void setDBValue(Object objValue) {
        setValue(objValue);
        dbValue = getValue();
    }

    public Object getDBValue() {
        return dbValue;
    }

    public boolean ValueEqualsDBValue() {
        return getValue().equals(getDBValue());
    }

    /**
     * validates the value of the control and sets the validValueProperty Please
     * note, this does NOT set the controls colours
     *
     * @return validValueProperty.get()
     */
    public boolean validateValue() {
        return validValueProperty.get();
    }

    public boolean isValid() {
        //validateValue();
        return validValueProperty.get();
    }

    public void setValueChangedEventListener(CustomChangeEventListener listener) {
        this.valueChangedlistener = listener;
    }

    public void setBackColor(EnumColorType clsColorType) {
        //clearBackColor();
        //txtField.getStyleClass().add(clsColorType.getColorTypeCssClass());

        //set inner style instead of using stylesheet class. Improves speed
        //set inner style instead of using stylesheet class. Improves speed
        getControl().setStyle("-fx-control-inner-background: " + clsColorType.getColorTypeValue());

    }

    public void clearBackColor() {
        //set inner style instead of using stylesheet class. Improves speed
        getControl().setStyle("-fx-control-inner-background: " + EnumColorType.WHITE.getColorTypeValue());

        //txtField.getStyleClass().remove(EnumColorType.WHITE.getColorTypeValue());
        //txtField.getStyleClass().remove(EnumColorType.CYAN.getColorTypeValue());
        //txtField.getStyleClass().remove(EnumColorType.RED.getColorTypeValue());
    }

    public void setLinkedDisplayControls(List<Control> lstNewLinkedDisplayControls) {
        //should be set once to prevent repetition
        if (lstLinkedDisplayControls == null) {
            lstLinkedDisplayControls = lstNewLinkedDisplayControls;
            setLinkedDisplayControlsListeners();
        } else {
            lstLinkedDisplayControls = lstNewLinkedDisplayControls;
        }
    }

    public void addLinkedDisplayControl(Control ctrLinkedDisplayControl) {
        if (lstLinkedDisplayControls == null) {
            setLinkedDisplayControls(new ArrayList<>());
        }
        lstLinkedDisplayControls.add(ctrLinkedDisplayControl);
    }

    private void setLinkedDisplayControlsListeners() {
        //should be set once to prevent repetition
        //I'm using a list because the current visibleProperty().bind implementation prevents the linked controls from setting their visibilities
        //i.e it only allows the visibility property to be controlled by one control or object

        getControl().visibleProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (lstLinkedDisplayControls != null) {
                    lstLinkedDisplayControls.forEach((ctr) -> {
                        ctr.setVisible(newValue);
                    });
                }//end if
            }//end method
        });

        getControl().disabledProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (lstLinkedDisplayControls != null) {
                    lstLinkedDisplayControls.forEach((ctr) -> {
                        ctr.setDisable(newValue);
                    });
                }//end if
            }//end method
        });
    }//end method

    public void setLinkedControl(LinkedHashMap<UcrCoreControl, List<ControlCondition>> mapNewLinkedControls) {
        mapLinkedControls = mapNewLinkedControls;
        updateLinkedControls();
    }//end method

    public void addLinkedControl(UcrCoreControl ucrLinkedControl, List<ControlCondition> clsCondition) {
        if (mapLinkedControls == null) {
            setLinkedControl(new LinkedHashMap<>());
        }
        mapLinkedControls.put(ucrLinkedControl, clsCondition);
        updateLinkedControls();
    }//end method

    /**
     * the control to be controlled by this control and the condition to be used
     *
     * @param ucrLinkedControl
     * @param clsCondition
     */
    public void addLinkedControl(UcrCoreControl ucrLinkedControl, ControlCondition clsCondition) {
        //adds the passed property if the control is already linked
        if (IsLinkedTo(ucrLinkedControl)) {
            mapLinkedControls.get(ucrLinkedControl).add(clsCondition);
            addLinkedControl(ucrLinkedControl, mapLinkedControls.get(ucrLinkedControl));
        } else {
            addLinkedControl(ucrLinkedControl, Arrays.asList(clsCondition));
        }//end if
    }//end method

    protected void updateLinkedControls() {
        if (mapLinkedControls != null) {
            for (Map.Entry<UcrCoreControl, List<ControlCondition>> entry : mapLinkedControls.entrySet()) {
                for (ControlCondition clsProp : entry.getValue()) {
                    clsProp.updateControlled();
                    //TODO. IMPROVE MORE ON THIS h
                }//end inner for loop

                entry.getKey().updateLinkedControls();
            }//end for loop      
        }//end if
    }//end method

    public boolean IsLinkedTo(UcrCoreControl ucrLinked) {
        return (mapLinkedControls == null ? false : mapLinkedControls.containsKey(ucrLinked));
    }

    public void getFocus() {
        getControl().requestFocus();
    }

    public void setVisible(boolean bVisible) {
        getControl().setVisible(bVisible);
    }

    public boolean isVisible() {
        return getControl().isVisible();
    }

    public void setDisable(boolean bDisable) {
        getControl().setDisable(bDisable);
    }

    public boolean isDisabled() {
        return getControl().isDisabled();
    }

    public void setTooltip(String strToolTipText) {
        getControl().setTooltip(new Tooltip(strToolTipText));
    }

    public final Window getWindow() {
        return getControl().getScene().getWindow();
    }

}//end class
