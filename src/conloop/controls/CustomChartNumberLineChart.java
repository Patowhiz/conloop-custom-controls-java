package conloop.controls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 *
 * @author Patowhiz 23/05/2019 07:31 PM
 */
public class CustomChartNumberLineChart extends CustomChartLineChart {

    private final LineChart<Number, Number> lineChart;

    public CustomChartNumberLineChart() {
        lineChart = new LineChart<>(new NumberAxis(), new NumberAxis());
        defaultSeriesName = "Series 1";
    }

    public CustomChartNumberLineChart(String strXAxisLabel, String strYAxisLabel) {
        this();
        setXAxisLabel(strXAxisLabel);
        setYAxisLabel(strYAxisLabel);
    }

    public CustomChartNumberLineChart(String strXAxisLabel, String strYAxisLabel, String defaultSeriesName) {
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

    public void addDataToDefaultSeries(Number xValue, Number yValue) {
        if (getChart().getData().isEmpty()) {
            setUpDefaultSeries(defaultSeriesName);
        }
        getChart().getData().get(0).getData().add(new XYChart.Data<>(xValue, yValue));
    }

    public void addSeriesData(LineChart.Series<Number, Number> lineSeries) {
        getChart().getData().add(lineSeries);
    }

    public void setData(ObservableList<XYChart.Series<Number, Number>> customData) {
        getChart().setData(customData);
    } 
    
    @Override
    public LineChart<Number, Number> getChart() {
        return lineChart;
    }
}
