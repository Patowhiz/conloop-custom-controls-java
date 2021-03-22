package conloop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox; 

/**
 *
 * @author PatoWhiz 15/04/2018 12:05 AM
 */
public class UcrSelectorMonth extends UcrSelectorCombobox {

    protected final ObservableList<Month> lstValues;
    public boolean bIncludeAllMonths;

    public UcrSelectorMonth(ComboBox<Object> cbo) {
        super(cbo);
        setTooltip("Selects month");
        cboValues.setEditable(true);
        lstValues = FXCollections.observableArrayList();
        bIncludeAllMonths = false; 
    }

    protected void loadMonths() {
        lstValues.clear();
        if (bIncludeAllMonths) {
            lstValues.add(new Month(0, "All Months"));
        }
        lstValues.add(new Month(1, "January"));
        lstValues.add(new Month(2, "February"));
        lstValues.add(new Month(3, "March"));
        lstValues.add(new Month(4, "April"));
        lstValues.add(new Month(5, "May"));
        lstValues.add(new Month(6, "June"));
        lstValues.add(new Month(7, "July"));
        lstValues.add(new Month(8, "August"));
        lstValues.add(new Month(9, "September"));
        lstValues.add(new Month(10, "October"));
        lstValues.add(new Month(11, "November"));
        lstValues.add(new Month(12, "December"));
    }

    @Override
    public void populateControl() {
        loadMonths();

        cboValues.setItems(lstValues);

        //TODO
        //MAKE IT REMEMBER WHAT THE PREVIOUS DIALOG HAD SELECTED INSTEAD OF CURRENT SYSTEM CLOCK MONTH???
        setValue(CalendarDateUtil.getCurrentMonth());
    }

    @Override
    public Integer getSelectedValue() {
        return getValue();
    }

    @Override
    public String getSelectedMember() {
        return lstValues.get(cboValues.getSelectionModel().getSelectedIndex()).monthName;
    }

    @Override
    public Month getSelectedItem() {
        return lstValues.get(cboValues.getSelectionModel().getSelectedIndex());
    }

    @Override
    public Integer getValue() {
        int index = indexOf(cboValues.getValue());
        if (index == -1) {
            return 0;
        } else {
            //there should be no duplicates for this control
            return lstValues.get(index).monthId;
        }//end outer if

    }

    @Override
    public void setValue(Object objValue) {
        if (objValue instanceof Integer) {
            int iValue = (int) objValue;
            for (int i = 0; i < lstValues.size(); i++) {
                if (lstValues.get(i).monthId == iValue) {
                    cboValues.getSelectionModel().select(i);
                    return;
                }
            }//end for loop
        } else {
            String strMember = String.valueOf(objValue);
            for (int i = 0; i < lstValues.size(); i++) {
                if (lstValues.get(i).monthName.equalsIgnoreCase(strMember)) {
                    cboValues.getSelectionModel().select(i);
                    return;
                }
            }//end for loop
        }//end if
    }//end method

    @Override
    public int indexOf(Object ObjValueToFind) {
        int index = -1;
        if (ObjValueToFind instanceof Integer) {
            int iValue = (int) ObjValueToFind;
            for (int i = 0; i < lstValues.size(); i++) {
                if (lstValues.get(i).monthId == iValue) {
                    index = i;
                    break;
                }
            }//end for loop
        } else {
            String strMember = String.valueOf(ObjValueToFind);
            for (int i = 0; i < lstValues.size(); i++) {
                if (lstValues.get(i).monthName.equalsIgnoreCase(strMember)) {
                    index = i;
                    break;
                }
            }//end for loop
        }//end inner if

        return index;
    }//end method

    public class Month {

        public final int monthId;
        public final String monthName;

        public Month(int monthId, String monthName) {
            this.monthId = monthId;
            this.monthName = monthName;
        }

        @Override
        public String toString() {
            return monthName;
        }

    }//end inner class

}
