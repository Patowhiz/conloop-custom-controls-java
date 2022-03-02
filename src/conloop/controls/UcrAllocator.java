package conloop.controls;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Patowhiz 28/02/2022 02:13 PM at Machakos house
 */
public class UcrAllocator<S> {

    public final ListView<S> lstViewSource;
    public final ListView<S> lstViewTarget;
    public final Button btnAddAll;
    public final Button btnAddSingle;
    public final Button btnReturnAll;
    public final Button btnReturnSingle;
    private final Label lblAll;
    private final Label lblSelected;
    private final String strPreTextAll;
    private ActionsEventListener<S> sourceActions;
    private ActionsEventListener<S> targetActions;

    public UcrAllocator(ListView<S> lstViewAll, ListView<S> lstViewSelected,
            Button btnAddAll, Button btnAddSingle, Button btnReturnAll, Button btnReturnSingle,
            Label lblAll, Label lblSelected, String strPreTextAll) {
        this.lstViewSource = lstViewAll;
        this.lstViewTarget = lstViewSelected;
        this.btnAddAll = btnAddAll;
        this.btnAddSingle = btnAddSingle;
        this.btnReturnAll = btnReturnAll;
        this.btnReturnSingle = btnReturnSingle;
        this.lblAll = lblAll;
        this.lblSelected = lblSelected;
        this.strPreTextAll = strPreTextAll.trim() + " ";

        setListeners();

    }

    public void setItems(List<S> lst) {
        this.lstViewSource.getItems().clear();
        this.lstViewTarget.getItems().clear();
        //use add all to prevents  modification of the original passed list 
        this.lstViewSource.getItems().addAll(lst);
    }

    public void setSourceActionEventListener(ActionsEventListener<S> sourceActions) {
        this.sourceActions = sourceActions;
    }

    public void setTargetActionEventListener(ActionsEventListener<S> targetActions) {
        this.targetActions = targetActions;
    }

    private void setListeners() {
        this.btnAddAll.setOnAction((ActionEvent event) -> {
            moveItems(lstViewSource, lstViewTarget, sourceActions);
        });

        this.btnAddSingle.setOnAction((ActionEvent event) -> {
            moveItems(lstViewSource, lstViewTarget, sourceActions);
        });

        this.btnReturnAll.setOnAction((ActionEvent event) -> {
            moveItems(lstViewTarget, lstViewSource, targetActions);
        });

        this.btnReturnSingle.setOnAction((ActionEvent event) -> {
            moveItems(lstViewTarget, lstViewSource, targetActions);
        });

        this.lstViewSource.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                moveItems(lstViewSource, lstViewTarget, sourceActions);
            }

        });

        this.lstViewTarget.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                moveItems(lstViewTarget, lstViewSource, targetActions);
            }
        });

    }

    public interface ActionsEventListener<S> {

        public void onEvent(List<S> lstItemsMoved);
    }

    private void moveItems(ListView<S> lstViewSource, ListView<S> lstViewTarget, ActionsEventListener<S> actionListener) {
        List<S> lstItemsMoved = new ArrayList<>();

        if (lstViewSource.getItems().isEmpty()) {
            return;
        }

        if (lstViewSource.getSelectionModel().isEmpty()) {
            return;
        }

        for (int index : lstViewSource.getSelectionModel().getSelectedIndices()) {
            lstItemsMoved.add(lstViewSource.getItems().get(index));
        }
        lstViewTarget.getItems().addAll(lstItemsMoved);
        lstViewSource.getItems().removeAll(lstItemsMoved);

        lblAll.setText(strPreTextAll + lstViewSource.getItems().size());
        lblSelected.setText(lstViewTarget.getItems().size() + " Selected");

        if (actionListener != null) {
            actionListener.onEvent(lstItemsMoved);
        }

    }

}
