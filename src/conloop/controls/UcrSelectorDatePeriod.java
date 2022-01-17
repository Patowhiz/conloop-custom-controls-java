package conloop.controls;

import conloop.CalendarDateUtil;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;

/**
 *
 * @author PatoWhiz 16/09/2019 11:04 PM
 */
public class UcrSelectorDatePeriod extends UcrSelectorCombobox<String> {

    //private final ObservableList<String> getControl();
    public boolean bIncludeCustomPeriod;
    public boolean bIncludeTerm;
    public boolean bIncludeQuarter;

    public UcrSelectorDatePeriod(ComboBox<String> cbo) {
        super(cbo);
        setTooltip("Selects period");
        //lstValues = FXCollections.observableArrayList();
        bIncludeCustomPeriod = false;
        bIncludeQuarter = false;
        bIncludeTerm = false;
        setListeners();
    }

    public void datePickersToControl(UcrDatePicker ucrDateFrom, UcrDatePicker ucrDateTo) {
        getControl().valueProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (bSuppressEvents) {
                return;
            }

            if (!bValidate) {
                return;
            }

            if (newValue == null) {
                return;
            }

            String periodName = newValue;
            ucrDateFrom.bSuppressEvents = true;//we want to raise after setting both values
            ucrDateTo.bSuppressEvents = true;
            switch (periodName) {
                case "Today":
                    ucrDateFrom.setValue(CalendarDateUtil.getTodayDate());
                    ucrDateTo.setValue(CalendarDateUtil.getTodayDate());
                    break;
                case "Yesterday":
                    ucrDateFrom.setValue(CalendarDateUtil.getYesterdayDate());
                    ucrDateTo.setValue(CalendarDateUtil.getYesterdayDate());
                    break;
                case "This week":
                    ucrDateFrom.setValue(CalendarDateUtil.getCurrentWeekMondayDate());
                    ucrDateTo.setValue(CalendarDateUtil.getCurrentWeekSaturdayDate());
                    break;
                case "Last week":
                    ucrDateFrom.setValue(CalendarDateUtil.getLastWeekMondayDate());
                    ucrDateTo.setValue(CalendarDateUtil.getLastWeekMondayDate());
                    break;
                case "This Month":
                    ucrDateFrom.setValue(CalendarDateUtil.getCurrentMonthStartDate());
                    ucrDateTo.setValue(CalendarDateUtil.getCurrentMonthEndDate());
                    break;
                case "Last Month":
                    ucrDateFrom.setValue(CalendarDateUtil.getLastMonthStartDate());
                    ucrDateTo.setValue(CalendarDateUtil.getLastMonthEndDate());
                    break;
                case "This Quarter":
                    //TODO.
                    break;
                case "This Term":
                    ucrDateFrom.setValue(CalendarDateUtil.getCurrentTermStartDate());
                    ucrDateTo.setValue(CalendarDateUtil.getCurrentTermEndDate());
                    break;
                case "Term 1":
                    ucrDateFrom.setValue(CalendarDateUtil.getTermStartDate(1));
                    ucrDateTo.setValue(CalendarDateUtil.getTermEndDate(1));
                    break;
                case "Term 2":
                    ucrDateFrom.setValue(CalendarDateUtil.getTermStartDate(2));
                    ucrDateTo.setValue(CalendarDateUtil.getTermEndDate(2));
                    break;
                case "Term 3":
                    ucrDateFrom.setValue(CalendarDateUtil.getTermStartDate(3));
                    ucrDateTo.setValue(CalendarDateUtil.getTermEndDate(3));
                    break;
                case "This Year":
                    ucrDateFrom.setValue(CalendarDateUtil.getCurrentYearStartDate());
                    ucrDateTo.setValue(CalendarDateUtil.getCurrentYearEndDate());
                    break;
                case "Last Year":
                    ucrDateFrom.setValue(CalendarDateUtil.getLastYearStartDate());
                    ucrDateTo.setValue(CalendarDateUtil.getLastYearEndDate());
                    break;
                default:
                    break;
            }
            ucrDateFrom.bSuppressEvents = false;
            ucrDateTo.bSuppressEvents = false;
            ucrDateTo.raiseValueChangedEvent();
        });
    }

    private void loadPeriods() {
        getControl().getItems().clear();
        if (bIncludeCustomPeriod) {
            getControl().getItems().add("Custom");
        }
        getControl().getItems().addAll("Today", "Yesterday", "This week", "Last week", "This Month", "Last Month");

        if (bIncludeQuarter) {
            //lstValues.add("This Quarter");//TODO
        }
        if (bIncludeTerm) {
            getControl().getItems().addAll("This Term", "Term 1", "Term 2", "Term 3");
        }

        getControl().getItems().addAll("This Year", "Last Year");

    }

    public void selectToday() {
        setValue("Today");
    }

    public void selectThisYear() {
        setValue("This Year");
    }

    @Override
    public void populateControl() {
        loadPeriods();
        selectFirst();
    }

    @Override
    public String getSelectedValue() {
        return getValue();
    }

    @Override
    public String getSelectedMember() {
        return getControl().getSelectionModel().getSelectedItem();
    }

    @Override
    public String getSelectedItem() {
        return getControl().getSelectionModel().getSelectedItem();
    }

    @Override
    public String getValue() {
        int index = indexOf(getControl().getValue());
        if (index == -1) {
            return "";
        } else {
            //there should be no duplicates for this control
            return getControl().getItems().get(index);
        }//end outer if

    }

    public String getMember() {
        return getValue();
    }

    @Override
    public void setValue(Object objValue) {

        String strMember = String.valueOf(objValue);
        for (int i = 0; i < getControl().getItems().size(); i++) {
            if (getControl().getItems().get(i).equalsIgnoreCase(strMember)) {
                cboValues.getSelectionModel().select(i);
                return;
            }
        }//end for loop
    }//end method

    @Override
    public int indexOf(Object ObjValueToFind) {
        int index = -1;
        String strMember = String.valueOf(ObjValueToFind);
        for (int i = 0; i < getControl().getItems().size(); i++) {
            if (getControl().getItems().get(i).equalsIgnoreCase(strMember)) {
                index = i;
                break;
            }
        }//end for loop

        return index;
    }//end method

}//end class
