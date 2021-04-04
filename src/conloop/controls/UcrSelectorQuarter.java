package conloop.controls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 *
 * @author PatoWhiz 15/08/2018 12:01 PM
 */
public class UcrSelectorQuarter extends UcrSelectorCombobox {

    private final ObservableList<Quarter> lstValues;
    public boolean bIncludeAllQuarters;

    public UcrSelectorQuarter(ComboBox<Object> cbo) {
        super(cbo);
        setTooltip("Selects quarter");
        cboValues.setEditable(true);
        lstValues = FXCollections.observableArrayList();
        bIncludeAllQuarters = false; 
    }

    private void loadQuarters() {
        lstValues.clear();
        if (bIncludeAllQuarters) {
            lstValues.add(new Quarter(0, "All Quaters"));
        }
        lstValues.add(new Quarter(1, "1st Quarter"));
        lstValues.add(new Quarter(2, "2nd Quarter"));
        lstValues.add(new Quarter(3, "3rd Quarter"));
        lstValues.add(new Quarter(4, "4th Quarter"));
    }

    @Override
    public void populateControl() {
        loadQuarters();

        cboValues.setItems(lstValues);

        //TODO
        //MAKE IT REMEMBER WHAT THE PREVIOUS DIALOG HAD SELECTED INSTEAD OF SELECXTING FIRST
        selectFirst();

    }

    @Override
    public Integer getSelectedValue() {
        return getValue();
    }

    @Override
    public String getSelectedMember() {
        return lstValues.get(cboValues.getSelectionModel().getSelectedIndex()).quarterName;
    }

    @Override
    public Quarter getSelectedItem() {
        return lstValues.get(cboValues.getSelectionModel().getSelectedIndex());
    }

    @Override
    public Integer getValue() {
        int index = indexOf(cboValues.getValue());
        if (index == -1) {
            return 0;
        } else {
            //there should be no duplicates for this control
            return lstValues.get(index).quarterId;
        }//end outer if

    }

    @Override
    public void setValue(Object objValue) {
        if (objValue instanceof Integer) {
            int iValue = (int) objValue;
            for (int i = 0; i < lstValues.size(); i++) {
                if (lstValues.get(i).quarterId == iValue) {
                    cboValues.getSelectionModel().select(i);
                    return;
                }
            }//end for loop
        } else {
            String strMember = String.valueOf(objValue);
            for (int i = 0; i < lstValues.size(); i++) {
                if (lstValues.get(i).quarterName.equalsIgnoreCase(strMember)) {
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
                if (lstValues.get(i).quarterId == iValue) {
                    index = i;
                    break;
                }
            }//end for loop
        } else {
            String strMember = String.valueOf(ObjValueToFind);
            for (int i = 0; i < lstValues.size(); i++) {
                if (lstValues.get(i).quarterName.equalsIgnoreCase(strMember)) {
                    index = i;
                    break;
                }
            }//end for loop
        }//end inner if

        return index;
    }//end method

    public class Quarter {

        public final int quarterId;
        public final String quarterName;

        public Quarter(int quarterId, String quarterName) {
            this.quarterId = quarterId;
            this.quarterName = quarterName;
        }

        @Override
        public String toString() {
            return quarterName;
        }

    }//end inner class

}//end class
