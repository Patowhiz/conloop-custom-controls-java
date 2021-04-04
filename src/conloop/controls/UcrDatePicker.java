package conloop.controls;

import conloop.CalendarDateUtil;
import java.time.LocalDate;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.DatePicker; 

/**
 *
 * @author PatoWhiz 12/04/2018
 */
public class UcrDatePicker extends UcrCoreControl {

    private DatePicker dtpDate;

    public UcrDatePicker(DatePicker dtpDate) {
        this.dtpDate = dtpDate;
        bValidate = true;
        bValidateSilently = true;
        bValidateEmpty = true;
        setListeners();
    }

    private void setListeners() {

        getControl().valueProperty().addListener((ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) -> {

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

            //update linked controls
            updateLinkedControls();

        });

    }//end method

    @Override
    public DatePicker getControl() {
        return dtpDate;
    }

    @Override
    public java.time.LocalDate getValue() {
        return dtpDate.getValue();
    }

    public java.sql.Date getValueAsSqlDate() {
        return getValue() == null ? null : CalendarDateUtil.getSqlDate(getValue());
    }
    
    public String getValueAsString(){
         return getValue() == null ? null : getValueAsSqlDate().toString() ;
    }

    public int getMonthValue() {
        return getValue().getMonthValue();
    }

    public int getYearValue() {
        return getValue().getYear();
    }

    /**
     * objValue can be a java.time.LocalDate or java.sql.Date sqlDateValue if
     * null is passed then the date picker will show no date
     *
     * @param objValue
     */
    @Override
    public void setValue(Object objValue) {
        if (objValue == null) {
            dtpDate.setValue(null);
        } else if (objValue instanceof java.time.LocalDate) {
            dtpDate.setValue((java.time.LocalDate) objValue);
        } else if (objValue instanceof java.sql.Date) {
            dtpDate.setValue(((java.sql.Date) objValue).toLocalDate());
        }//end if

    }//end method

    public void setToTodayDate() {
        setValue(java.time.LocalDate.now());
    }

}//end class
