package conloop.controls;

import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * TODO. WE NEED TO SIT AND THINK ABOUT HOW TO IMPLEMENT THIS FOR ALL CASES
 * SCENARIO. THUS IT HAS BEEN LEFT FOR NOW AS AT 06/03/2019 01:41 PM
 *
 * @author Patowhiz 06/03/2019 01:14 PM
 */
public class UcrTableView extends UcrCoreControl {

    private TableView tableView;

    public UcrTableView(TableView newTableView) {
        tableView = newTableView; 
        //setListeners();

    }

    public void setItems(ObservableList value) {
        tableView.setItems(value);
    }

    public ObservableList getItems() {
        return tableView.getItems();
    }

    //for some reason this doesn't work with refress. so it's been as private for now
    private void add(Object o) {
        getItems().add(0);
    }

    //for some reason this doesn't work with refress. so it's been as private for now
    private void remove(Object o) {
        getItems().remove(0);
    }

    public void remove(int index) {
        getItems().remove(index);
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
    public TableView getControl() {
        return this.tableView;
    }

    @Override
    public void setValue(Object objValue) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getValue() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //not used yet. TODO
    public <T> TableColumn<T, ?> getTableColumnByName(String name) {

        //todo
        for (Object col1 : tableView.getColumns()) {
            TableColumn col = (TableColumn) col1;

            if (col.getText().equals(name)) {
                return col;
            }
        }
        return getTableColumnByName(tableView, "");
    }

    private <T> TableColumn<T, ?> getTableColumnByName(TableView<T> tableView, String name) {
        for (TableColumn<T, ?> col : tableView.getColumns()) {
            if (col.getText().equals(name)) {
                return col;
            }
        }
        return null;
    }

}//end class
