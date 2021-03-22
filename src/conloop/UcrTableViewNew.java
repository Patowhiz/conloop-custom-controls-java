package conloop;

import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 * TODO. THIS COULD LATER BE IMPROVED TO USE GENERICS THUS IT HAS BEEN LEFT FOR
 * NOW AS AT 22/12/2020 05:59 AM
 *
 * @author Patowhiz 22/12/2020 05:59 PM
 */
public class UcrTableViewNew<S> extends UcrCoreControl {

    private final TableView<S> tableView;
    private CustomContextMenu contextMenu;
    public boolean bShowContextMenu;
    private MouseClickEventListener<S> mouseClickEventlistener;
    private MouseSingleClickEventListener<S> mouseSingleClickEventlistener;
    private MouseDoubleClickEventListener<S> mouseDoubleClickEventlistener;
    private MouseSecondaryClickEventListener<S> mouseSecondaryClickEventlistener;

    public UcrTableViewNew(TableView<S> newTableView) {
        tableView = newTableView;
        bShowContextMenu = false;
        //setListeners();

    }

    public void setItems(ObservableList<S> value) {
        getControl().setItems(value);
    }

    public ObservableList<S> getItems() {
        return getControl().getItems();
    }

    public boolean isEmpty() {
        return getItems().isEmpty();
    }

    //todo. confirm this comment. for some reason this doesn't work with refresh. so it's been as private for now
    public void add(S object) {
        getItems().add(object);
    }

    //todo. confirm this comment.for some reason this doesn't work with refresh. so it's been as private for now
    public void remove(S object) {
        getItems().remove(object);
    }

    public void remove(int index) {
        getItems().remove(index);
    }

    public CustomContextMenu getContextMenu() {
        if (contextMenu == null) {
            contextMenu = new CustomContextMenu();
        }
        return contextMenu;
    }

    public void refresh() {
        tableView.refresh();
    }

    //sets the property values based on the cell index order
    public void setCellValueFactory(int colIndex, String propertyValueName) {
        ((TableColumn) tableView.getColumns().get(colIndex)).setCellValueFactory(new PropertyValueFactory<>(propertyValueName));
    }

    //sets the property values based on the cell indexes
    public void setCellValueFactory(ObservableList<String> lstPropertyValueNames) {
        for (int i = 0; i < lstPropertyValueNames.size(); i++) {
            setCellValueFactory(i, lstPropertyValueNames.get(i));
        }//end for loop
    }

    //sets the property values based on the cell indexes
    public void setCellValueFactory(String... propertyValueNames) {
        setCellValueFactory(FXCollections.observableList(Arrays.asList(propertyValueNames)));
    }

    @Override
    public TableView<S> getControl() {
        return this.tableView;
    }

    public S getSelectedItem() {
        return getControl().getSelectionModel().getSelectedItem();
    }
    
    public void selectFirst(){
        getControl().getSelectionModel().selectFirst();
    }

    @Override
    public void setValue(Object objValue) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getValue() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public TableColumn<S, ?> getTableColumnByName(String name) {
        for (TableColumn<S, ?> col : getControl().getColumns()) {
            if (col.getText().equals(name)) {
                return col;
            }
        }
        return null;
    }

    public void setTableRowFactory() {
        getControl().setRowFactory(new Callback<TableView<S>, TableRow<S>>() {
            @Override
            public TableRow<S> call(TableView<S> tv) {
                TableRow<S> row = new TableRow<>();
                row.setOnMouseClicked((MouseEvent event) -> {
                    if (row.isEmpty()) {
                        return;
                    }

                    //rasie the necessary events
                    if (event.getButton() == MouseButton.PRIMARY) {
                        if (event.getClickCount() == 1) {
                            if (mouseSingleClickEventlistener != null) {
                                mouseSingleClickEventlistener.onMouseClickEvent(row);
                            }
                        } else if (event.getClickCount() == 2) {
                            if (mouseDoubleClickEventlistener != null) {
                                mouseDoubleClickEventlistener.onMouseClickEvent(row);
                            }
                        }
                    } else if (event.getButton() == MouseButton.SECONDARY) {
                        if (bShowContextMenu) {
                            getContextMenu().show(row, event);
                        }

                        if (mouseSecondaryClickEventlistener != null) {
                            mouseSecondaryClickEventlistener.onMouseClickEvent(row);
                        }
                    }//end  if

                    if (mouseClickEventlistener != null) {
                        mouseClickEventlistener.onMouseClickEvent(event, row);
                    }//end if

                });

                return row;
            }
        });
    }//end method

    public void setOnMouseClickEventListener(MouseClickEventListener<S> listener) {
        this.mouseClickEventlistener = listener;
    }

    public void setOnMouseSingleClickEventListener(MouseSingleClickEventListener<S> listener) {
        this.mouseSingleClickEventlistener = listener;
    }

    public void setOnMouseDoubleClickEventListener(MouseDoubleClickEventListener<S> listener) {
        this.mouseDoubleClickEventlistener = listener;
    }

    public void setOnMouseSecondaryClickEventListener(MouseSecondaryClickEventListener<S> listener) {
        this.mouseSecondaryClickEventlistener = listener;
    }

    public interface MouseClickEventListener<S> {

        public void onMouseClickEvent(MouseEvent event, TableRow<S> row);
    }

    public interface MouseSingleClickEventListener<S> {

        public void onMouseClickEvent(TableRow<S> row);
    }

    public interface MouseDoubleClickEventListener<S> {

        public void onMouseClickEvent(TableRow<S> row);
    }

    public interface MouseSecondaryClickEventListener<S> {

        public void onMouseClickEvent(TableRow<S> row);
    }

}//end class
