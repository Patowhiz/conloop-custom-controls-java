package conloop.controls;

import javafx.beans.property.BooleanProperty;

/**
 *
 * @author PatoWhiz 13/04/2018
 */
public class ControlCondition {

    private String strCoditionType = "BOOLEAN";
    private boolean bPositive = true;
    private boolean bControllerValue;
    private BooleanProperty ucrControllerProperty;
    private BooleanProperty ucrControlledProperty;

    /**
     * bControllerValue is the value to compare to ucrControllerProperty for
     * satisfied condition and setting the ucrControlledProperty.set(bControllerValue)
     * e.g ucrControllerProperty.get() == bControllerValue
     * bPositive is used to check for positive conditions if set
     * to true and negative otherwise
     *
     * @param ucrControllerProperty
     * @param ucrControlledProperty
     * @param bControllerValue
     * @param bPositive
     */
    public ControlCondition(BooleanProperty ucrControllerProperty, BooleanProperty ucrControlledProperty, boolean bControllerValue, boolean bPositive) {
        this.ucrControllerProperty = ucrControllerProperty;
        this.ucrControlledProperty = ucrControlledProperty;
        this.bControllerValue = bControllerValue;
        this.bPositive = bPositive;
        this.strCoditionType = "BOOLEAN";
    }

    public void updateControlled() {
        if (strCoditionType.equals("BOOLEAN")) {
            updateControlledBoolean();
        } else {

        }
    }

    public void updateControlledBoolean() {
        if (bPositive) {
            if (isSatisfied()) {
                ucrControlledProperty.set(bControllerValue);
            } else {
                ucrControlledProperty.set(false);
            }//end if
        } else {
            if (isSatisfied()) {
                ucrControlledProperty.set(false);
            } else {
                ucrControlledProperty.set(bControllerValue);
            }//end if
        }

    }//end method

    private boolean isSatisfied() {
        return ucrControllerProperty.get() == bControllerValue; //end if
    }//end method

}
