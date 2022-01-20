package conloop.controls;

import conloop.StringsUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;
import org.controlsfx.control.spreadsheet.Grid;
import org.controlsfx.control.spreadsheet.GridBase;
import org.controlsfx.control.spreadsheet.Picker;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetCellBase;
import org.controlsfx.control.spreadsheet.SpreadsheetCellType;
import org.controlsfx.control.spreadsheet.SpreadsheetColumn;
import org.controlsfx.control.spreadsheet.SpreadsheetView;

/**
 *
 * @author PatoWhiz 01/02/2018 07:52 PM
 */
public class UcrSpreadSheet extends UcrCoreControl {

    private SpreadsheetView spreadSheetView;
    protected ObservableList<String> lstRowMenuItems = FXCollections.observableArrayList();
    protected ObservableList<PickerEventListener> lstRowPickersMenuItemlistener = FXCollections.observableArrayList();
    protected PickerEventListener rowPickerMenuItemlistener;
    protected double mouseX;
    protected double mouseY;

    public UcrSpreadSheet(String... columnHeaders) {
        this(FXCollections.observableList(Arrays.asList(columnHeaders)));
    }

    public UcrSpreadSheet(ObservableList<String> columnHeaders) {
        this(new SpreadsheetView());
        setGrid(columnHeaders);
    }

    public UcrSpreadSheet() {
        spreadSheetView = new SpreadsheetView();
        setListeners();
        setEditable(true);
        setShowColumnHeader(true);
        setShowRowHeader(true);
        setSelectionModeAsMultiple();
    }

    public UcrSpreadSheet(SpreadsheetView newSpreadSheetView) {
        spreadSheetView = newSpreadSheetView;//doesn't modify any theme
        setListeners();
        setEditable(true);
        setShowColumnHeader(true);
        setShowRowHeader(true);
        setSelectionModeAsMultiple();
    }

    private void setListeners() {
        spreadSheetView.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
            mouseX = event.getScreenX();
            mouseY = event.getScreenY();
        });

        rowPickerMenuItemlistener = (CustomPicker sourcePicker) -> {
            for (PickerEventListener listener : lstRowPickersMenuItemlistener) {
                listener.onEvent(sourcePicker);
            }
        };
    }

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public void setSelectionModeAsSingle() {
        spreadSheetView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void setSelectionModeAsMultiple() {
        spreadSheetView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void setGrid(Grid grid) {
        spreadSheetView.setGrid(grid);
    }

    public void setGrid(ObservableList<String> columnHeaders) {
        setGrid(new GridBase(1, columnHeaders.size()));//rows count will be readjusted as its filled
        setColumnHeaderNames(columnHeaders);
    }

    public Grid getGrid() {
        return spreadSheetView.getGrid();
    }

    public void setEditable(boolean bEditable) {
        spreadSheetView.setEditable(bEditable);
    }

    public void setShowRowHeader(boolean bShowRowHeader) {
        spreadSheetView.setShowRowHeader(bShowRowHeader);
    }

    public void setShowColumnHeader(boolean bShowColumnHeader) {
        spreadSheetView.setShowColumnHeader(bShowColumnHeader);
    }

    public ObservableList<SpreadsheetColumn> getFixedColumns() {
        return spreadSheetView.getFixedColumns();
    }

    public ObservableList<Integer> getFixedRows() {
        return spreadSheetView.getFixedRows();
    }

    public void addFixedColumn(int iColumnIndex) {
        getFixedColumns().add(spreadSheetView.getColumns().get(iColumnIndex));
    }

    public void addFixedRow(int iRowIndex) {
        getFixedRows().add(iRowIndex);
    }

    public void setColumnWidth(int iColumnIndex, double dWidth) {
        spreadSheetView.getColumns().get(iColumnIndex).setPrefWidth(dWidth);
    }

    private void setColumnHeaderNames(ObservableList<String> columnHeaders) {
        getColumnHeaders().setAll(columnHeaders);
    }

    public ObservableList<String> getColumnHeaders() {
        return getGrid().getColumnHeaders();
    }

    public void setRows(ObservableList<ObservableList<SpreadsheetCell>> rows) {
        getGrid().setRows(rows);
    }

    public void setRows(ArrayList<ObservableList<SpreadsheetCell>> rows) {
        getGrid().setRows(rows);
    }

    public ObservableList<ObservableList<SpreadsheetCell>> getRows() {
        return getGrid().getRows();
    }

    public void addRowPickerMenuItems(String... menuElements) {
        this.lstRowMenuItems.addAll(menuElements);
    }

    public void addRowPickerMenuItems(int startPositionIndex, String... menuElements) {
        ObservableList<String> lstNewRowMenuItems = FXCollections.observableList(Arrays.asList(menuElements));
        for (String str : lstNewRowMenuItems) {
            this.lstRowMenuItems.add(startPositionIndex, str);
            startPositionIndex++;
        }
    }

    public void addRowPickerMenuItems(int positionIndex, String menuElement) {
        this.lstRowMenuItems.add(positionIndex, menuElement);
    }

    public void clearRowPickerMenuItems() {
        this.lstRowMenuItems.clear();
    }

    public void addRowPickerMenuItemsListener(PickerEventListener listener) {
        this.lstRowPickersMenuItemlistener.add(listener);
    }
    
     public void clearRowPickerMenuItemsListener() {
        this.lstRowMenuItems.clear();
    }

    public ObservableMap<Integer, Picker> getRowPickers() {
        return spreadSheetView.getRowPickers();
    }

    public void addRow(ObservableList<SpreadsheetCell> row) {
        getRows().add(row);

        //create the picker for the row if there is row menu items set
        if (!this.lstRowMenuItems.isEmpty()) {
            int rowIndex = getRows().size() - 1;
            CustomPicker picker = new CustomPicker(rowIndex, this);
            for (String mnuItem : this.lstRowMenuItems) {
                picker.addMenuItem(mnuItem, rowPickerMenuItemlistener);
            }
            getRowPickers().put(rowIndex, picker);
        }//end if
    }

    /**
     * TO BE DEPRECATED. THE ADDITION OF THE PICKERS IS DONE AUTOMATICALLY AFTER
     * SETTING UP THE MENU ITEMS TO BE USED
     *
     * @param rowIndex
     * @param row
     * @param picker
     */
    public void addRow(int rowIndex, ObservableList<SpreadsheetCell> row, Picker picker) {
        //getRows().add(row);
        addRow(row);
        getRowPickers().put(rowIndex, picker);
    }

    public void removeRow(int rowIndex) {
        getRows().remove(rowIndex);
    }

    public void clearRows() {
        getRows().clear();
    }

    public ObservableList<SpreadsheetColumn> getColumns() {
        return spreadSheetView.getColumns();
    }

    public boolean isEmpty() {
        return getRows().isEmpty();
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    /**
     * returns the contents of the grid in a printable format
     *
     * @return Object[][]
     */
    public Object[][] getPrintData() {
        //Initialise the array to hold the data
        Object[][] fileData = new Object[getRows().size()][getColumnHeaders().size()];
        int rowIndex = 0;
        int columnIndex;
        for (ObservableList<SpreadsheetCell> lstRow : getRows()) {
            columnIndex = 0;
            for (SpreadsheetCell columnCell : lstRow) {
                fileData[rowIndex][columnIndex] = columnCell.getText();
                columnIndex++;
            }
            rowIndex++;
        }
        return fileData;
    }

    private void setStyleSheet(String styleSheetFileName) {
        //TODO
        //spreadSheetView.getStylesheets().add(getClass().getResource("spreadsheetSample.css").toExternalForm());

    }

    @Override
    public SpreadsheetView getControl() {
        return this.spreadSheetView;
    }

    @Override
    public void setValue(Object objValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ObservableList getValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //OTHER UTIL FUNCTIONS FOR THE SPREAD SHEET
    public static SpreadsheetCell createSingleSpanCell(int rowIndex, int columnIndex, Object cellContent) {
        return createSingleSpanCell(rowIndex, columnIndex, cellContent, false);
    }

    public static SpreadsheetCell createSingleSpanCell(int rowIndex, int columnIndex, Object cellContent, boolean bSetTooltip) {
        return createSingleSpanCell(rowIndex, columnIndex, cellContent, bSetTooltip, true);
    }

    public static SpreadsheetCell createSingleSpanCell(int rowIndex, int columnIndex, Object cellContent, boolean bSetTooltip, boolean bSetEditable) {
        return createSingleSpanCell(rowIndex, columnIndex, cellContent, bSetTooltip, bSetEditable, null);
    }

    public static SpreadsheetCell createSingleSpanCell(int rowIndex, int columnIndex, Object cellContent, boolean bSetTooltip, boolean bSetEditable, String style) {
        SpreadsheetCell cell = null;
        if (cellContent == null) {
            cell = SpreadsheetCellType.STRING.createCell(rowIndex, columnIndex, 1, 1, null);
        } else if (cellContent instanceof String) {
            cell = SpreadsheetCellType.STRING.createCell(rowIndex, columnIndex, 1, 1, (String) cellContent);
        } else if (cellContent instanceof Integer) {
            cell = SpreadsheetCellType.INTEGER.createCell(rowIndex, columnIndex, 1, 1, (Integer) cellContent);
        } else if (cellContent instanceof Long) {
            cell = SpreadsheetCellType.STRING.createCell(rowIndex, columnIndex, 1, 1, String.valueOf((Long)cellContent) );
        } else if (cellContent instanceof Double) {
            cell = SpreadsheetCellType.DOUBLE.createCell(rowIndex, columnIndex, 1, 1, (Double) cellContent);
        } else if (cellContent instanceof java.sql.Date) {
            cell = SpreadsheetCellType.DATE.createCell(rowIndex, columnIndex, 1, 1, ((java.sql.Date) cellContent).toLocalDate());
        } else if (cellContent instanceof Boolean) {
            cell = SpreadsheetCellType.STRING.createCell(rowIndex, columnIndex, 1, 1, null);
            CheckBox chkSelected = new CheckBox();
            chkSelected.setSelected((Boolean) cellContent);
            cell.setGraphic(chkSelected);
        }

        //to do test this
        if (cell != null) {
            //set the tooltip
            if (bSetTooltip && cellContent != null) {
                setCellTooltip(cell, cell.getText());
            }
            //set if editable
            cell.setEditable(bSetEditable);

            //set the class
            if (StringsUtil.isNotNullOrEmpty(style)) {
                cell.getStyleClass().add(style);
            }
        }//end if

        return cell;
    }

    public static SpreadsheetCell createSingleSpanListCell(int rowIndex, int columnIndex, List<String> lstItems, String cellContent) {
        SpreadsheetCell cell;
        cell = SpreadsheetCellType.LIST(lstItems).createCell(rowIndex, columnIndex, 1, 1, cellContent);

        return cell;
    }

    public static void setCellTooltip(SpreadsheetCell cell, String toolTipContent) {
        ((SpreadsheetCellBase) cell).setTooltip(toolTipContent);
    }

    public static class CustomPicker extends Picker {

        private final UcrSpreadSheet ucrSpreadsheetView;
        private final ContextMenu context;
        private final int rowIndex;
        private String selectedMenuItem;

        public CustomPicker() {
            this(-1, null);
        }

        public CustomPicker(int rowIndex, UcrSpreadSheet ucrSpreadsheetView) {
            this.rowIndex = rowIndex;
            this.ucrSpreadsheetView = ucrSpreadsheetView;
            this.selectedMenuItem = "";
            this.context = new ContextMenu();
        }

        @Override
        public void onClick() {
            try {

                //select the row first, to show the user what he has selescted
                this.ucrSpreadsheetView.getControl().getSelectionModel().clearSelection();//clear any previous selection
                this.ucrSpreadsheetView.getControl().getSelectionModel().selectRange(rowIndex, this.ucrSpreadsheetView.getColumns().get(0), rowIndex, this.ucrSpreadsheetView.getColumns().get(this.ucrSpreadsheetView.getColumns().size() - 1));
                //use the spreadsheet context menu property. It helps us in manageing hiding of context menus
                //always restore back the spreadsheet context menu after closing the newly set menu
                final ContextMenu prevMenu = this.ucrSpreadsheetView.getControl().getContextMenu();
                this.ucrSpreadsheetView.getControl().setContextMenu(context);
                this.ucrSpreadsheetView.getControl().getContextMenu().show(this.ucrSpreadsheetView.getControl(),
                        this.ucrSpreadsheetView.getMouseX(), this.ucrSpreadsheetView.getMouseY());
                this.context.setOnHidden((WindowEvent event) -> {
                    this.ucrSpreadsheetView.getControl().setContextMenu(prevMenu);
                });
            } catch (Exception ex) {
                CustomAlerts.showErrorExceptionAlert("Error in showing context for picker", ex);
            }
        }

        public void addMenuItem(String item, PickerEventListener listener) {
            MenuItem mnuItem = new MenuItem(item);
            mnuItem.setOnAction((ActionEvent event) -> {
                CustomPicker.this.selectedMenuItem = item;
                listener.onEvent(CustomPicker.this);
            });

            context.getItems().add(mnuItem);
        }

        public int getRowIndex() {
            return this.rowIndex;
        }

        public String getSelectedMenuItem() {
            return this.selectedMenuItem;
        }

//        public Object getRowValue() {
//            return this.ucrSpreadsheetView.getValue().get(rowIndex);
//        }
    }

    public static interface PickerEventListener {

        public void onEvent(CustomPicker sourcePicker);
    }

}//end class
