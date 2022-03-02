
package conloop.controls;

import conloop.CalendarDateUtil;
import javafx.scene.control.ComboBox; 

/**
 *
 * @author PatoWhiz 03/01/2021 10:13 PM at Machakos house
 */
public class UcrSelectorTerm extends UcrSelectorCombobox<UcrSelectorTerm.Term> {

    public boolean bIncludeItemAllTerms;

    public UcrSelectorTerm(ComboBox<Term> cbo) {
        super(cbo);
        setTooltip("Selects Term");
        bIncludeItemAllTerms = false;
    }

    @Override
    public void populateControl() {
        getItems().clear();
        if (bIncludeItemAllTerms) {
            getItems().add(new Term(0, "All Terms"));
        }
        getItems().add(new Term(1, "Term 1"));
        getItems().add(new Term(2, "Term 2"));
        getItems().add(new Term(3, "Term 3"));
        
        setValue(CalendarDateUtil.getCurrentKenyaAcademicTerm());
    }

    @Override
    public Integer getSelectedValue() {
      return  getSelectedItem().termId; 
    }

    @Override
    public String getSelectedMember() {
          return  getSelectedItem().termName;  
    }

    @Override
    public Term getSelectedItem() {
        return getControl().getSelectionModel().getSelectedItem();
    }

    @Override
    public void setValue(Object objValue) {
        if (objValue instanceof Integer) {
            int iValue = (int) objValue;
            for (int i = 0; i < getItems().size(); i++) {
                if (getItems().get(i).termId == iValue) {
                    select(i);
                    return;
                }
            }//end for loop
        } else {
            String strMember = String.valueOf(objValue);
            for (int i = 0; i < getItems().size(); i++) {
                if (getItems().get(i).termName.equalsIgnoreCase(strMember)) {
                    select(i);
                    return;
                }
            }//end for loop
        }//end if
    }

    public static class Term {

        public final int termId;
        public final String termName;

        public Term(int termId, String termName) {
            this.termId = termId;
            this.termName = termName;
        }

        @Override
        public String toString() {
            //this override is used by the combobox
            return termName;
        }

    }//end inner class

}

