package conloop.controls;

import conloop.CalendarDateUtil;
import java.util.List;
import javafx.scene.control.ComboBox; 

/**
 *
 * @author PatoWhiz 03/01/2021 09:50 PM at Machakos house
 */
public class UcrSelectorYear extends UcrSelectorCombobox<Integer> {

    public UcrSelectorYear(ComboBox<Integer> cbo) {
        super(cbo);
        setTooltip("Selects year");
    }

    @Override
    public void populateControl() {
        try {
            getControl().getItems().clear();
            //get a list of the last 7 years incuding this one
            final List<Integer> yrMnth = CalendarDateUtil.getListofLastYears(CalendarDateUtil.getCurrentYear(), 5, true);
            for (int i = yrMnth.size() - 1; i >= 0; i--) {
                getControl().getItems().add(yrMnth.get(i));
            }

            //temporary check for 2021 academic COVID-19 calendar changes
            //todo. remove once academic year resumes to be like normal year
//            int currentYear = CalendarDateUtil.getCurrentYear();
//            if (currentYear == 2021 && CalendarDateUtil.getCurrentTerm() < 3) {
//                currentYear = currentYear - 1;//reset back to 2020 as selected year
//            }

            setValue(CalendarDateUtil.getCurrentKenyaAcademicYear());
        } catch (Exception ex) {
            CustomAlerts.showDeveloperErrorAlert("Error occurred in loading teachers into the combobox", ex);
        }//end try
    }

    @Override
    public Integer getSelectedValue() {
        return getControl().getSelectionModel().getSelectedItem();
    }

    @Override
    public String getSelectedMember() {
        return getControl().getSelectionModel().getSelectedItem().toString();
    }

    @Override
    public Integer getSelectedItem() {
        return getControl().getSelectionModel().getSelectedItem();
    }

    @Override
    public void setValue(Object objValue) {
        if (objValue instanceof Integer) {
            int iValue = (Integer) objValue;
            for (int i = 0; i < getItems().size(); i++) {
                if (getItems().get(i) == iValue) {
                    select(i);
                    return;
                }
            }//end for loop
            //an integer that qualifies can be set as a year
            getControl().setValue(iValue);
        }//end if
    }//end method

}
