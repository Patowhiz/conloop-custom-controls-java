package conloop.controls;

import javafx.scene.control.ComboBox;

/**
 *
 * @author Patowhiz 28/02/2022 01:34 pm at Machakos house
 */
public class UcrSelectorYearTerm {

    private final UcrSelectorYear ucrSelectorYear;
    private final UcrSelectorTerm ucrSelectorTerm;

    public UcrSelectorYearTerm(ComboBox<Integer> cboYear, ComboBox<UcrSelectorTerm.Term> cboTerm) {
        this(cboYear, cboTerm, false);
    }

    public UcrSelectorYearTerm(ComboBox<Integer> cboYear, ComboBox<UcrSelectorTerm.Term> cboTerm, boolean bIncludeItemAllTerms) {
        ucrSelectorYear = new UcrSelectorYear(cboYear);
        ucrSelectorYear.populateControl();

        ucrSelectorTerm = new UcrSelectorTerm(cboTerm);
        ucrSelectorTerm.bIncludeItemAllTerms = bIncludeItemAllTerms;
        ucrSelectorTerm.populateControl();
    }

    public UcrSelectorYear getUcrSelectorYear() {
        return ucrSelectorYear;
    }

    public UcrSelectorTerm getUcrSelectorTerm() {
        return ucrSelectorTerm;
    }

    public int getSelectedYear() {
        return ucrSelectorYear.getSelectedValue();
    }

    public int getSelectedTerm() {
        return ucrSelectorTerm.getSelectedValue();
    }

    public String getYearTermSelectionString() {
        return getSelectedYear() + "  " + ucrSelectorTerm.getSelectedMember();
    }

    public void setSelectedYearTerm(int yearId, int termId) {
        ucrSelectorYear.setValue(yearId);
        ucrSelectorTerm.setValue(termId);
    }
    
    public void setValueChangedEventListener(CustomChangeEventListener listener) {
        ucrSelectorYear.setValueChangedEventListener(listener);
        ucrSelectorTerm.setValueChangedEventListener(listener);
    }
}
