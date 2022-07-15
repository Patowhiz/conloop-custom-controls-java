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

    public UcrSelectorQuarterMonth(ComboBox<Month> cbo) {
        super(cbo);
        quarter = 0; 
    }

    @SuppressWarnings("unchecked")
    public void bindQuarterControl(UcrSelectorQuarter ucrSelectorQuarter) {
        ucrSelectorQuarter.getControl().valueProperty().addListener(new ChangeListener<Month>() {
            @Override
            public void changed(ObservableValue<? extends Month> observable, Month oldValue, Month newValue) {
                quarter = ucrSelectorQuarter.getValue();
                populateControl();
            }
        });
    }

    @Override
    protected void loadMonths() {
        getControl().getItems().clear();
        switch (quarter) {
            case 1:
                getControl().getItems().add(new Month(1, "January"));
                getControl().getItems().add(new Month(2, "February"));
                getControl().getItems().add(new Month(3, "March"));
                break;
            case 2:
                getControl().getItems().add(new Month(4, "April"));
                getControl().getItems().add(new Month(5, "May"));
                getControl().getItems().add(new Month(6, "June"));
                break;
            case 3:
                getControl().getItems().add(new Month(7, "July"));
                getControl().getItems().add(new Month(8, "August"));
                getControl().getItems().add(new Month(9, "September"));
                break;
            case 4:
                getControl().getItems().add(new Month(10, "October"));
                getControl().getItems().add(new Month(11, "November"));
                getControl().getItems().add(new Month(12, "December"));
                break;
            default:
                super.loadMonths();
                break;
        }

    }//end method

}//end class
