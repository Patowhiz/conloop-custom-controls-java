package conloop.controls;

import conloop.CalendarDateUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox; 

/**
 *
 * @author PatoWhiz 16/09/2019 11:04 PM
 */
public class UcrSelectorDatePeriod extends UcrSelectorCombobox {

    private final ObservableList<String> lstValues;
    public boolean bIncludeCustomPeriod;
    public boolean bIncludeTerm;
    public boolean bIncludeQuarter;

    public UcrSelectorDatePeriod(ComboBox<Object> cbo) {
        super(cbo);
        setTooltip("Selects period");
        lstValues = FXCollections.observableArrayList();
        bIncludeCustomPeriod = false;
        bIncludeQuarter = false;
        bIncludeTerm = false;
        setListeners();
    }

    public void datePickersToControl(UcrDatePicker ucrDateFrom, UcrDatePicker ucrDateTo) {
        getControl().valueProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
                if (bSuppressEvents) {
                    return;
                }

                if (!bValidate) {
                    return;
                }

                if (newValue == null) {
                    return;
                }

                String periodName = newValue.toString();
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

            }
        });
    }

    private void loadPeriods() {
        lstValues.clear();
        if (bIncludeCustomPeriod) {
            lstValues.add("Custom");
        }
        lstValues.add("Today");
        lstValues.add("Yesterday");
        lstValues.add("This week");
        lstValues.add("Last week");
        lstValues.add("This Month");
        lstValues.add("Last Month");

        if (bIncludeQuarter) {
            //lstValues.add("This Quarter");//TODO
        }
        if (bIncludeTerm) {
            lstValues.add("This Term");
            lstValues.add("Term 1");
            lstValues.add("Term 2");
            lstValues.add("Term 3");
        }

        lstValues.add("This Year");
        lstValues.add("Last Year");

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

        cboValues.setItems(lstValues);

        //TODO
        //MAKE IT REMEMBER WHAT THE PREVIOUS DIALOG HAD SELECTED INSTEAD OF SELECXTING FIRST
        selectFirst();

    }

    @Override
    public String getSelectedValue() {
        return getValue();
    }

    @Override
    public String getSelectedMember() {
        return lstValues.get(cboValues.getSelectionModel().getSelectedIndex());
    }

    @Override
    public String getSelectedItem() {
        return lstValues.get(cboValues.getSelectionModel().getSelectedIndex());
    }

    @Override
    public String getValue() {
        int index = indexOf(cboValues.getValue());
        if (index == -1) {
            return "";
        } else {
            //there should be no duplicates for this control
            return lstValues.get(index);
        }//end outer if

    }

    public String getMember() {
        return getValue();
    }

    @Override
    public void setValue(Object objValue) {

        String strMember = String.valueOf(objValue);
        for (int i = 0; i < lstValues.size(); i++) {
            if (lstValues.get(i).equalsIgnoreCase(strMember)) {
                cboValues.getSelectionModel().select(i);
                return;
            }
        }//end for loop
    }//end method

    @Override
    public int indexOf(Object ObjValueToFind) {
        int index = -1;
        String strMember = String.valueOf(ObjValueToFind);
        for (int i = 0; i < lstValues.size(); i++) {
            if (lstValues.get(i).equalsIgnoreCase(strMember)) {
                index = i;
                break;
            }
        }//end for loop

        return index;
    }//end method

}//end class
