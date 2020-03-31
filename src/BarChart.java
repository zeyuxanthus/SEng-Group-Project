import java.util.ArrayList;

public class BarChart {
    private ArrayList<Bar> bars;
    private Metric metric;
    private BarChartType barChartType;
    private Filter filter;

    public BarChart(Metric metric, BarChartType barChartType, Filter filter){
        this.metric = metric;
        this.barChartType = barChartType;
        this.filter = filter;
        calculateBars();
    }

    /**
     * Calculate bars to be displayed on chart
     */
    private void calculateBars(){
        switch (barChartType) {
            case DAY_OF_WEEK:
                bars = calculatePerDayOfWeek();
                break;
            case TIME_OF_DAY:
                bars = calculatePerTimeOfDay();
                break;
        }
    }

    private ArrayList<Bar> calculatePerTimeOfDay() {
        ArrayList<Bar> bars = new ArrayList<Bar>();
        return bars;
    }

    private ArrayList<Bar> calculatePerDayOfWeek() {
        ArrayList<Bar> bars = new ArrayList<Bar>();
        return bars;
    }

    public ArrayList<Bar> getBars() {
        return bars;
    }
}