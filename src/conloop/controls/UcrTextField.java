package conloop.controls;

import conloop.StringsUtil;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 *
 * @author PatoWhiz 11/04/2018
 */
public class UcrTextField extends UcrTextInput {

    protected TextField txtField;
    protected double dMinimum;
    protected double dMaximum;
    private boolean bToUpper;
    private boolean bToLower;
    private boolean bToFirstLetterCapital;
    protected boolean bIsReadOnly;
    protected ValidationTypes clsValidationType;

    public UcrTextField(TextField newTxtField) {
        this.txtField = newTxtField;
        bToUpper = false;
        bToLower = false;
        bToFirstLetterCapital = false;
        bIsReadOnly = false;
        clsValidationType = ValidationTypes.NONE;
        bValidate = true;
        bValidateSilently = true;
        bValidateEmpty = false;
        setListeners();
    }

    private void setListeners() {

//        this.txtField.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//              
//            }
//        });
        getControl().textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
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
        } //end method
        );

        getControl().focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            //this also needs to happen on press enter
            //change the case appropriately
            ChangeCase();
        });

    }//end method

    @Override
    public TextField getControl() {
        return this.txtField;
    }

    @Override
    public void setValue(Object objValue) {
        if (objValue == null) {
            txtField.setText("");
        } else if (objValue instanceof Integer) {
            txtField.setText(Integer.toString((int) objValue));
        } else if (objValue instanceof Double) {
            txtField.setText(Double.toString((double) objValue));
        } else {
            txtField.setText(String.valueOf(objValue));
        }
    }

    @Override
    public String getValue() {
        return txtField.getText().trim();
    }

    @Override
    public String getDBValue() {
        return dbValue == null ? "" : String.valueOf(dbValue);
    }

    public boolean ValueEqualsDBValueIgnoreCase() {
        return getValue().equalsIgnoreCase(getDBValue());
    }

    public double getValueAsDouble() {
        if (isEmpty()) {
            return 0;
        } else {
            return Double.parseDouble(getValue());
        }
    }

    public String getValueAsCommaSeparatedNumber() {
        if (isEmpty()) {
            return "";
        } else {
            return StringsUtil.formatToCommaNumber(getValueAsDouble());
        }
    }

    public int getValueAsInteger() {
        if (isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(getValue());
        }
    }

    public long getValueAsLong() {
        if (isEmpty()) {
            return 0;
        } else {
            return Long.parseLong(getValue());
        }
    }

    public void setEditable(boolean value) {
        txtField.setEditable(value);
    }

    public void setTextToUpper() {
        bToUpper = true;
        bToLower = false;
        bToFirstLetterCapital = false;
    }

    public void setTextToLower() {
        bToLower = true;
        bToUpper = false;
        bToFirstLetterCapital = false;
    }

    public void setTextToFirstLetterCapital() {
        bToLower = false;
        bToUpper = false;
        bToFirstLetterCapital = true;
    }

    public void setValidationTypeAsNone() {
        clsValidationType = ValidationTypes.NONE;
    }

    public void setValidationTypeAsNumeric() {
        setValidationTypeAsNumeric(0, Double.MAX_VALUE);
    }

    public void setValidationTypeAsPhoneNoKE() {
        setValidationTypeAsPhoneNo("ke");
    }

    public void setValidationTypeAsPhoneNo(String phoneNoformat) {
        clsValidationType = ValidationTypes.PHONENO;
        //todo. More functionailty to be added to this
    }

    public void setValidationTypeAsEmail() {
        //todo
    }

    public void setValidationTypeAsNumeric(double dcmMin, double dcmMax) {
        clsValidationType = ValidationTypes.NUMERIC;
        dMinimum = dcmMin;
        dMaximum = dcmMax;
    }

    public void setMinimumNumericValue(double dcmMin) {
        dMinimum = dcmMin;
    }

    public void setMaximumNumericValue(double dcmMax) {
        dMaximum = dcmMax;
    }

    @Override
    public boolean validateValue() {
        if (bValidateEmpty && isEmpty()) {
            validValueProperty.set(false);
        } else if (isEmpty()) {
            validValueProperty.set(true);
        } else {
            int iType = getValidationCode(getValue());
            switch (iType) {
                case 0:
                    //do nothing
                    break;
                case 1:
                    if (!bValidateSilently) {
                        CustomAlerts.showErrorAlert(null, "Entry", null, "Number expected!");
                    }
                    break;
                case 2:
                    //check if it was lower and upper limit violation
                    if (!(dMinimum <= Double.parseDouble(getValue()))) {
                        if (!bValidateSilently) {
                            CustomAlerts.showWarningAlert(null, "Entry", null, "Value lower than lowerlimit of: " + dMinimum);
                        }
                    } else if (!(dMaximum >= Double.parseDouble(getValue()))) {
                        if (!bValidateSilently) {
                            CustomAlerts.showWarningAlert(null, "Entry", null, "Value higher than upperlimit of: " + dMaximum);
                        }
                    }
                    break;
                case 3:
                    if (!bValidateSilently) {
                        CustomAlerts.showErrorAlert(null, "Entry", null, "Valid Phone Number expected!");
                    }
                    break;
                default:
                    break;
            }//end switch
            validValueProperty.set(iType == 0);
        }//end outer if

        return validValueProperty.get();
    }

    /**
     * returns 0 if strText is valid, 1 if strText is invalid
     *
     * @param strText
     * @return
     */
    public int getValidationCode(String strText) {
        int iType;
        switch (clsValidationType) {
            case NONE:
                iType = 0;
                break;
            case NUMERIC:
                iType = validateNumeric(strText);
                break;
            case PHONENO:
                iType = validatePhoneNo(strText);
                break;
            default:
                iType = 0;//by defaults validation is set to none
                break;
        }
        return iType;
    }

    /**
     * Returns integer as code for validation; 0 : string is valid. 1 : string
     * is not numeric. 2 : string is outside range
     *
     * @param strText
     * @return
     */
    public int validateNumeric(String strText) {
        double dcmText;
        int iType = 0;

        if (StringsUtil.isNotNullOrEmpty(strText)) {
            if (StringsUtil.isNotNumeric(strText)) {
                iType = 1;
            } else {
                dcmText = Double.parseDouble(strText);
                if (dcmText < dMinimum || dcmText > dMaximum) {
                    iType = 2;
                }
            }
        } else {
            iType = 1;
        }
        return iType;
    }

    public int validatePhoneNo(String strText) {
        int iType = 0;
        if (StringsUtil.isValidPhoneNumber(strText)) {
            iType = 3;
        }
        return iType;
    }

    public boolean validateText(String strText) {
        int iValidationCode;

        iValidationCode = getValidationCode(strText);

        switch (iValidationCode) {
            case 0:
                //this is for none. No validation
                break;
            case 1:
                switch (clsValidationType) {
                    case NUMERIC:
                        CustomAlerts.showMessageAlert(null, "Entry", null, "Entry must be numeric.");
                        break;
                }
                break;
            case 2:
                switch (clsValidationType) {
                    case NUMERIC:
                        CustomAlerts.showMessageAlert(null, "Entry", null, "This number must be: " + getNumericRange());
                        break;
                }
                break;
            default:
                break;
        }

        return (iValidationCode == 0);

    }

    public String getNumericRange() {
        String strRange = "";
        if (clsValidationType == ValidationTypes.NUMERIC) {
            if (dMinimum != Double.MIN_VALUE) {
                strRange = ">= " + dMinimum;
                if (dMaximum != Double.MAX_VALUE) {
                    strRange += " and ";
                }
            }
            if (dMaximum != Double.MAX_VALUE) {
                strRange = strRange + "<= " + dMaximum;
            }

        }
        return strRange;
    }

    public void ChangeCase() {
        if (!isEmpty()) {
            if (bToLower) {
                setValue(getValue().toLowerCase());
            } else if (bToUpper) {
                setValue(getValue().toUpperCase());
            } else if (bToFirstLetterCapital) {
                //input capitalised
                setValue(getValue().substring(0, 1).toUpperCase() + getValue().substring(1));
            }
        }//end if

    }//end method

    public boolean isEmpty() {
        return getValue().length() <= 0;
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public boolean hasNumericValue() {
        return StringsUtil.isNumeric(getValue());
    }

    /**
     * Please note, this method won't raise any events and no validations will
     * be done. The textbox validation colour will be white. if you want
     * validations and events to be raised use setValue("")
     */
    public void Clear() {
        getControl().clear();
        setBackColor(EnumColorType.WHITE);
    }

    protected enum ValidationTypes {
        NONE,
        NUMERIC,
        PHONENO
    }

}//end class
