package conloop.controls;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

/**
 *
 * @author PatoWhiz 15/01/2022 04:05 AM at Machakos house
 */
public class UcrDateRangeSelector {

    private final UcrDatePicker ucrDateFrom;
    private final UcrDatePicker ucrDateTo;

    public UcrDateRangeSelector(ComboBox<String> cboPeriod, DatePicker dtpDateFrom, DatePicker dtpDateTo) {
        ucrDateFrom = new UcrDatePicker(dtpDateFrom);
        ucrDateTo = new UcrDatePicker(dtpDateTo);

        UcrSelectorDatePeriod ucrSelectorDatePeriod = new UcrSelectorDatePeriod(cboPeriod);
        ucrSelectorDatePeriod.bIncludeCustomPeriod = true;
        ucrSelectorDatePeriod.bIncludeTerm = true;
        ucrSelectorDatePeriod.datePickersToControl(ucrDateFrom, ucrDateTo);
        ucrSelectorDatePeriod.populateControl();
        ucrSelectorDatePeriod.selectThisYear();

        ucrDateFrom.setToTodayDate();
        ucrDateTo.setToTodayDate();

    }

    public UcrDatePicker getUcrDateFrom() {
        return ucrDateFrom;
    }

    public UcrDatePicker getUcrDateTo() {
        return ucrDateTo;
    }

    public java.sql.Date getFromDateAsSqlDate() {
        return ucrDateFrom.getValueAsSqlDate();
    }

    public java.sql.Date getToDateAsSqlDate() {
        return ucrDateTo.getValueAsSqlDate();
    }

    public String getDateRangeSelectionString() {
        return "From " + getFromDateAsSqlDate() + " To " + getToDateAsSqlDate();
    }

    public void setPeriodDate(java.sql.Date fromDate, java.sql.Date toDate) {
        ucrDateFrom.setValue(fromDate);
        ucrDateTo.setValue(toDate);
    }

    public void setFromDate(java.sql.Date date) {
        ucrDateFrom.setValue(date);
    }

    public void setToDate(java.sql.Date date) {
        ucrDateTo.setValue(date);
    }

    public void setValueChangedEventListener(CustomChangeEventListener listener) {
        ucrDateFrom.setValueChangedEventListener(listener);
        ucrDateTo.setValueChangedEventListener(listener);
    }
}
