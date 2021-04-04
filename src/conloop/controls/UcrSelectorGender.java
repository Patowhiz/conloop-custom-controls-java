package conloop.controls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 *
 * @author PatoWhiz 15th May 2018 03:52 AM
 */
public class UcrSelectorGender extends UcrSelectorCombobox {

    private final ObservableList<String> lstValues;

    public UcrSelectorGender(ComboBox<Object> cbo) {
        super(cbo);
        setTooltip("Selects gender");
        lstValues = FXCollections.observableArrayList();
        cboValues.setEditable(true); 
    }

    private void loadGenders() {
        lstValues.clear();
        lstValues.add("Male");
        lstValues.add("Female");
        lstValues.add("Other");

    }//end method

    @Override
    public void populateControl() {
        loadGenders();
        cboValues.setItems(lstValues);

        //TODO
        //MAKE IT REMEMBER WHAT THE PREVIOUS DIALOG HAD SELECTED INSTEAD OF FIRST ITEM
        selectFirst();
    }

    @Override
    public String getSelectedValue() {
        return lstValues.get(cboValues.getSelectionModel().getSelectedIndex());
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
            return cboValues.getValue().toString();
        } else {
            //there should be no duplicates for this control
            return lstValues.get(index);
        }//end outer if

    }

    @Override
    public void setValue(Object objValue) {
        String strValue = String.valueOf(objValue);
        for (int i = 0; i < lstValues.size(); i++) {
            if (lstValues.get(i).equalsIgnoreCase(strValue)) {
                cboValues.getSelectionModel().select(i);
                return;
            }
        }//end for loop

        //TODO
        //a string that qualifies can be set as a gender??
        cboValues.setValue(strValue);

    }//end method

    @Override
    public int indexOf(Object ObjValueToFind) {
        int index = -1;
        String strValue = String.valueOf(ObjValueToFind);
        for (int i = 0; i < lstValues.size(); i++) {
            if (lstValues.get(i).equalsIgnoreCase(strValue)) {
                index = i;
                break;
            }
        }//end for loop

        return index;
    }//end method

}//end class
