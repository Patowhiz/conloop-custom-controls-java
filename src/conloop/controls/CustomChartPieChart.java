package conloop.controls;

import conloop.StringsUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.chart.PieChart;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author PatoWhiz 25/04/2018 06:28 AM
 */
public class CustomChartPieChart {

    private final PieChart piechart;
    public boolean bShowPopup;
    private int tempBlickingCounter = 0;

    public CustomChartPieChart() {
        this.piechart = new PieChart();
    }

    public CustomChartPieChart(boolean bLegendVisble, boolean bShowPopup) {
        this();
        setLegendVisible(bLegendVisble);
        this.bShowPopup = bShowPopup; 
    }

    //this should never be default. Because the charts will not always be clickable.
    public void changeChartCursorToHand() {
        piechart.setCursor(Cursor.HAND);
    }

    public void setData(ObservableList<CustomData> customData) {
        //this.customData = newCustomData;
        //add elements to the real piechart data
        final ObservableList<PieChart.Data> realChartData = FXCollections.observableArrayList();
        for (CustomData data1 : customData) {
            realChartData.add(new PieChart.Data(data1.getPieName(), data1.getPieValue()));
        }
        //set the data for the piechart
        piechart.setData(realChartData);

        //if set to show custom popup then show it
        if (bShowPopup) {
            CustomPopupPieChart popup = new CustomPopupPieChart();

            int counter = 0;
            for (PieChart.Data pieData : piechart.getData()) {

                final CustomData clsCustomData = customData.get(counter);

                pieData.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        tempBlickingCounter = -1;
                        popup.setPieTag(clsCustomData.getPieTag());
                        popup.setPieValue(StringsUtil.formatToCommaNumber(clsCustomData.getPieValue()));
                        popup.getPopup().show(pieData.getNode(), event.getScreenX(), event.getScreenY());
                    }
                });

                pieData.getNode().addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (tempBlickingCounter == 2) {
                            return;
                        }

                        tempBlickingCounter += 1;
                        if (event.getScreenX() != popup.getPopup().getAnchorX() && event.getScreenY() != popup.getPopup().getAnchorY()) {
                            if (popup.getPopup().isShowing()) {
                                popup.getPopup().setAnchorX(event.getScreenX());
                                popup.getPopup().setAnchorY(event.getScreenY());
                            } //end if 
                        }//end if
                    }
                });

                pieData.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        popup.getPopup().hide();
                    }
                });

                counter++;

            }//end for loop

        }//end if
    }//end method

    public PieChart getPiechart() {
        return piechart;
    }

    public void setLegendVisible(boolean bLegendVisble) {
        getPiechart().setLegendVisible(bLegendVisble);
    }

    public static class CustomData {

        private final String pieTag;
        private final String pieName;
        private final double pieValue;

        public CustomData(String pieTag, String pieName, double pieValue) {
            this.pieTag = pieTag;
            this.pieName = pieName;
            this.pieValue = pieValue;
        }

        public String getPieTag() {
            return pieTag;
        }

        public String getPieName() {
            return pieName;
        }

        public double getPieValue() {
            return pieValue;
        }

    }

}//end class
