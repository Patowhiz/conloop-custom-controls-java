package conloop;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 *
 * @author Patowhiz 04/03/2019 05:19 PM
 */
public class UcrSelectorGeneral extends UcrSelectorCombobox {

    public UcrSelectorGeneral(ComboBox<Object> cbo) {
        super(cbo); 
    }

    public void setItems(ObservableList<Object> values) {
        getControl().setItems(values);
    }

    public void addAll(Object... elements) {
        getControl().getItems().addAll(elements);
    }

    public void clear() {
        getControl().getItems().clear();
    }

    @Override
    public void populateControl() {
    }

    @Override
    public Object getSelectedValue() {
        return getControl().getSelectionModel().getSelectedItem();
    }

    @Override
    public Object getSelectedMember() {
        return getControl().getSelectionModel().getSelectedItem();
    }

    @Override
    public Object getSelectedItem() {
        return getControl().getSelectionModel().getSelectedItem();
    }

    @Override
    public Object getValue() {
        int index = indexOf(getControl().getValue());
        if (index == -1) {
            return getControl().getValue();
        } else {
            //there should be no duplicates for this control
            return getControl().getItems().get(index);
        }//end outer if
    }
    
    public String getValueAsString() {
       return getValue().toString();
    }

    @Override
    public void setValue(Object objValue) {
//        for (int i = 0; i < lstValues.size(); i++) {
//            if (lstValues.get(i).equals(objValue)) {
//                cboValues.getSelectionModel().select(i);
//                return;
//            }
//        }//end for loop

        if (getControl().getItems().contains(objValue)) {
            getControl().getSelectionModel().select(objValue);
            return;
        }

        getControl().setValue(objValue);
    }//end method

    @Override
    public int indexOf(Object ObjValueToFind) {
        // int index = -1;
//        for (int i = 0; i < lstValues.size(); i++) {
//            if (lstValues.get(i).equals(ObjValueToFind)) {
//                index = i;
//                break;
//            }
//        }//end for loop

        // return index;
        return getControl().getItems().indexOf(getControl().getValue());
    }//end method

}//end class
