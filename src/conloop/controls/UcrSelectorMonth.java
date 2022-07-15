package conloop.controls;

import conloop.CalendarDateUtil;
import javafx.scene.control.ComboBox; 

/**
 *
 * @author PatoWhiz 15/04/2018 12:05 AM
 */
public class UcrSelectorMonth extends UcrSelectorCombobox {

    public boolean bIncludeAllMonths;

    public UcrSelectorMonth(ComboBox<Month> cbo) {
        super(cbo);
        setTooltip("Selects month");  
        bIncludeAllMonths = false; 
    }

    protected void loadMonths() {
        getControl().getItems().clear();
        if (bIncludeAllMonths) {
             getControl().getItems().add(new Month(0, "All Months"));
        }
         getControl().getItems().add(new Month(1, "January"));
         getControl().getItems().add(new Month(2, "February"));
         getControl().getItems().add(new Month(3, "March"));
         getControl().getItems().add(new Month(4, "April"));
         getControl().getItems().add(new Month(5, "May"));
         getControl().getItems().add(new Month(6, "June"));
         getControl().getItems().add(new Month(7, "July"));
         getControl().getItems().add(new Month(8, "August"));
         getControl().getItems().add(new Month(9, "September"));
         getControl().getItems().add(new Month(10, "October"));
         getControl().getItems().add(new Month(11, "November"));
         getControl().getItems().add(new Month(12, "December"));
    }
    
    @Override
    public ComboBox<Month> getControl() {
        return cboValues; //TODO. THIS METHOD SHOULD BE SET AS ABSTRACT TOO
    }

    @Override
    public void populateControl() {
        loadMonths();

        //TODO
        //MAKE IT REMEMBER WHAT THE PREVIOUS DIALOG HAD SELECTED INSTEAD OF CURRENT SYSTEM CLOCK MONTH???
        setValue(CalendarDateUtil.getCurrentMonth());
    }

    @Override
    public Integer getSelectedValue() {
        return getControl().getSelectionModel().getSelectedItem().monthId;
    }

    @Override
    public String getSelectedMember() {
        return getControl().getSelectionModel().getSelectedItem().monthName;
    }

    @Override
    public Month getSelectedItem() {
        return getControl().getSelectionModel().getSelectedItem();
    }

    @Override
    public Integer getValue() {
         return getSelectedValue();
    }

    @Override
    public void setValue(Object objValue) {
        if (objValue instanceof Integer) {
            int iValue = (int) objValue;
            for (int i = 0; i < getControl().getItems().size(); i++) {
                if (getControl().getItems().get(i).monthId == iValue) {
                    cboValues.getSelectionModel().select(i);
                    return;
                }
            }//end for loop
        } else {
            String strMember = String.valueOf(objValue);
            for (int i = 0; i < getControl().getItems().size(); i++) {
                if (getControl().getItems().get(i).monthName.equalsIgnoreCase(strMember)) {
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
            for (int i = 0; i < getControl().getItems().size(); i++) {
                if (getControl().getItems().get(i).monthId == iValue) {
                    index = i;
                    break;
                }
            }//end for loop
        } else {
            String strMember = String.valueOf(ObjValueToFind);
            for (int i = 0; i < getControl().getItems().size(); i++) {
                if (getControl().getItems().get(i).monthName.equalsIgnoreCase(strMember)) {
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
