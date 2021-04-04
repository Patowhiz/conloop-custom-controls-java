package conloop.controls;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;

/**
 *
 * @author PatoWhiz 15/08/2018 12:17 PM
 */
public class UcrSelectorQuarterMonth extends UcrSelectorMonth {

    private int quarter;

    public UcrSelectorQuarterMonth(ComboBox<Object> cbo) {
        super(cbo);
        quarter = 0; 
    }

    @SuppressWarnings("unchecked")
    public void bindQuarterControl(UcrSelectorQuarter ucrSelectorQuarter) {
        ucrSelectorQuarter.getControl().valueProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
                quarter = ucrSelectorQuarter.getValue();
                populateControl();
            }
        });
    }

    @Override
    protected void loadMonths() {
        lstValues.clear();
        switch (quarter) {
            case 1:
                lstValues.add(new Month(1, "January"));
                lstValues.add(new Month(2, "February"));
                lstValues.add(new Month(3, "March"));
                break;
            case 2:
                lstValues.add(new Month(4, "April"));
                lstValues.add(new Month(5, "May"));
                lstValues.add(new Month(6, "June"));
                break;
            case 3:
                lstValues.add(new Month(7, "July"));
                lstValues.add(new Month(8, "August"));
                lstValues.add(new Month(9, "September"));
                break;
            case 4:
                lstValues.add(new Month(10, "October"));
                lstValues.add(new Month(11, "November"));
                lstValues.add(new Month(12, "December"));
                break;
            default:
                super.loadMonths();
                break;
        }

    }//end method

}//end class
