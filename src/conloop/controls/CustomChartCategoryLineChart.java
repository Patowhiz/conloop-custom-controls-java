package conloop.controls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 *
 * @author PatoWhiz 26/04/2018
 */
public class CustomChartCategoryLineChart extends CustomChartLineChart {

    private final LineChart<String, Number> lineChart;

    public CustomChartCategoryLineChart() { 
        lineChart = new LineChart<>(new CategoryAxis(), new NumberAxis());
        defaultSeriesName = "Series 1";
    }

    public CustomChartCategoryLineChart(String strXAxisLabel, String strYAxisLabel) {
        this();
        setXAxisLabel(strXAxisLabel);
        setYAxisLabel(strYAxisLabel);
    }

    public CustomChartCategoryLineChart(String strXAxisLabel, String strYAxisLabel, String defaultSeriesName) {
        this(strXAxisLabel, strYAxisLabel);
        setDefaultSeriesName(defaultSeriesName);
    }

    @Override
    public void setDefaultSeriesName(String seriesName) {
        defaultSeriesName = seriesName;
        if (getChart().getData().isEmpty()) {
            setUpDefaultSeries(defaultSeriesName);
        } else {
            getChart().getData().get(0).setName(defaultSeriesName);
        }
    }

    private void setUpDefaultSeries(String seriesName) {
        defaultSeriesName = seriesName;
        addSeriesData(new LineChart.Series<>(defaultSeriesName, FXCollections.observableArrayList()));
    }

    public void addDataToDefaultSeries(String xValue, Number yValue) {
        if (getChart().getData().isEmpty()) {
            setUpDefaultSeries(defaultSeriesName);
        }
        getChart().getData().get(0).getData().add(new XYChart.Data<>(xValue, yValue));
    }

    
    public void addSeriesData(LineChart.Series<String, Number> lineSeries) {
        getChart().getData().add(lineSeries);
    }

    public void setData(ObservableList<XYChart.Series<String, Number>> customData) {
        getChart().setData(customData);
    }

    @Override
    public LineChart<String, Number> getChart() {
        return lineChart;
    }
}//end class
