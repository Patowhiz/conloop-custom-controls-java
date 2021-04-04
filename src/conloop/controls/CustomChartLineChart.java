package conloop.controls;

import javafx.scene.Cursor;
import javafx.scene.chart.LineChart;

/**
 *
 * @author Patowhiz 23/05/2019 07:31 PM
 */
public abstract class CustomChartLineChart {

    protected String defaultSeriesName;

    public abstract LineChart getChart();

    public void setXAxisLabel(String strLabel) {
        getChart().getXAxis().setLabel(strLabel);
    }

    public void setYAxisLabel(String strLabel) {
        getChart().getYAxis().setLabel(strLabel);
    }

    public abstract void setDefaultSeriesName(String seriesName);

    public void clearData() {
        getChart().getData().clear();
    }

    public void changeChartCursorToHand() {
        getChart().setCursor(Cursor.HAND);
    }

}
