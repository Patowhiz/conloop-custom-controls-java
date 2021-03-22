package conloop;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;

/**
 *
 * @author PatoWhiz 09/04/2018
 */
public abstract class UcrSelectorCombobox<S> extends UcrCoreControl {

    protected ComboBox<S> cboValues;
    //if this is true, the Create new item must be set as the last object otherwise
    //setCreateNewItemListener() won't function as expected
    public boolean bIncludeItemCreateNew;

    //we don't know the combobox type. It's only known during runtime so we use ?
    public UcrSelectorCombobox(ComboBox<S> cbo) {
        this.cboValues = cbo;
        //by default, all combobox should be validated silently and even when empty
        bValidate = true;
        bValidateSilently = true;
        bValidateEmpty = true;
        bIncludeItemCreateNew = false;
        setListeners();

    }

    protected void setListeners() {
//        getControl().valueProperty().addListener((ChangeListener<Object>) new ChangeListener<Object>() {
//            @Override
//            public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
//               
//            }
//        });

        //if the combobox ia editable then attach a listener to the text property else to the value pproperty 
        if (getControl().isEditable()) {
            getControl().getEditor().textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                if (bSuppressEvents) {
                    return;
                }
                if (bValidate) {
                    if (validateValue()) {
                        //if its a valid value then set as the value
                        //TODO
                        setValue(getControl().getEditor().getText());
                        raiseValueChanged(oldValue, newValue);
                        setBackColor(EnumColorType.WHITE);
                    } else {
                        setBackColor(EnumColorType.RED);
                    }//end inner if
                }//end if            
            });
        } else {

            getControl().valueProperty().addListener(new ChangeListener<S>() {
                @Override
                public void changed(ObservableValue<? extends S> observable, S oldValue, S newValue) {
                    if (bSuppressEvents) {
                        return;
                    }
                    if (bValidate) {
                        if (validateValue()) {
                            raiseValueChanged(oldValue, newValue);
                            setBackColor(EnumColorType.WHITE);
                        } else {
                            setBackColor(EnumColorType.RED);
                        }
                    } else {
                        raiseValueChanged(oldValue, newValue);
                    }//end if   
                }//end if
            });

        }//end if
    }//end method

    private void raiseValueChanged(Object oldValue, Object newValue) {
        //check for nulls carefully
        if (oldValue != null && newValue != null && !newValue.toString().equals(oldValue.toString())) {
            super.raiseValueChangedEvent();
        } else if ((oldValue == null && newValue != null) || (oldValue != null && newValue == null)) {
            super.raiseValueChangedEvent();
        }
    }//end method

    public abstract void populateControl();

    public abstract Object getSelectedValue();

    public abstract Object getSelectedMember();

    public abstract S getSelectedItem();

    public ObservableList<S> getItems() {
        return getControl().getItems();
    }

    public int getSelectedIndex() {
        return getControl().getSelectionModel().getSelectedIndex();
    }

    public int getLastIndex() {
        return getControl().getItems().size() - 1;
    }

    @Override
    public S getValue() {
        return getControl().getValue();
    }

    public void selectFirst() {
        if (!getControl().getItems().isEmpty()) {
            getControl().getSelectionModel().selectFirst();
        }
    }

    public void selectLast() {
        if (!getControl().getItems().isEmpty()) {
            getControl().getSelectionModel().selectLast();
        }
    }

    public void select(int index) {
        getControl().getSelectionModel().select(index);
    }
    
      public void select(S obj) {
        getControl().getSelectionModel().select(obj);
    }

      /**
       * deprecated
       * @param strMember 
       */
    public void selectValue(String strMember) {
        setValue(strMember);
    }

    /**
     * deprecated
     * @param obj 
     */
    public void selectValue(int obj) {
        setValue(obj);
    }


    //@Override
    //public void setValue(Object objValue) {
    //cboValues.setValue(objValue);
    //}
    @Override
    public Integer getDBValue() {
        return dbValue == null ? 0 : (int) dbValue;
    }

    @Override
    public boolean validateValue() {
        if (bValidateEmpty && isEmpty()) {
            validValueProperty.set(false);
        } else if (isEmpty()) {
            validValueProperty.set(true);
        } else {
            Object obj;
            if (getControl().isEditable()) {
                obj = getControl().getEditor().getText();
            } else {
                obj = getControl().getValue();
            }

            if ((indexOf(obj) == -1)) {
                validValueProperty.set(false);
            } else {
                validValueProperty.set(true);
            }
        }//end if

        return validValueProperty.get();
    }//end method

    /**
     * returns the index of the first occurrence of the specified element in
     * this list, -1 if this list does not contain the element
     *
     * @param objValueToFind
     * @return
     */
    public int indexOf(Object objValueToFind) {
        return getControl().getItems().indexOf(objValueToFind);
    }//end method

    public boolean isEmpty() {
        if (getControl().isEditable()) {
            return getControl().getEditor().getText().trim().length() <= 0;
        } else {
            return getControl().getValue() == null || getControl().getValue().toString().length() <= 0;
        }

    }

    @Override
    public ComboBox<S> getControl() {
        return cboValues; //TODO. THIS METHOD SHOULD BE SET AS ABSTRACT TOO
    }

    protected void setCreateNewItemListener() {
        getControl().addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (bIncludeItemCreateNew) {
                    //use selected index here to prevent null pointer exceptions
                    if (getSelectedIndex() != -1 && getSelectedIndex() == getLastIndex()) {
                        bIncludeItemCreateNew = false;//switch off
                        selectFirst();//remove the create new immediately
                        bIncludeItemCreateNew = true;
                        showNewEntryForm();
                    } //end if
                }//end outer if
            }
        });
    }//end method

    protected void showNewEntryForm() {
        //To be overriden by the chcildren
    }

    public void setEditable(boolean editable) {
        getControl().setEditable(editable);
    }

}//end class
