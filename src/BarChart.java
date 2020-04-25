import java.util.ArrayList;

public class BarChart {
    private ArrayList<Bar> bars;
    private Metric metric;
    private BarChartType barChartType;
    private Filter filter;

    private Controller controller;

    public BarChart(Metric metric, BarChartType barChartType, Filter filter, Controller controller){
        this.metric = metric;
        this.barChartType = barChartType;
        this.filter = filter;
        this.controller = controller;
        calculateBars();
    }

    /**
     * Calculate bars to be displayed on chart
     */
    private void calculateBars(){
        ArrayList<Impression> impressions = controller.filterImpressionLog(filter);
        ArrayList<Click> clicks = controller.filterClickLog(filter);
        ArrayList<ServerEntry> serverEntries = controller.filterServerLog(filter);
        switch (barChartType) {
            case DAY_OF_WEEK:
                bars = calculatePerDayOfWeek(impressions, clicks, serverEntries);
                break;
            case TIME_OF_DAY:
                bars = calculatePerTimeOfDay(impressions, clicks, serverEntries);
                break;
        }
    }

    private ArrayList<Bar> calculatePerTimeOfDay(ArrayList<Impression> impressions, ArrayList<Click> clicks, ArrayList<ServerEntry> serverEntries) {
        ArrayList<Bar> bars = new ArrayList<Bar>();
        return bars;
    }

    private ArrayList<Bar> calculatePerDayOfWeek(ArrayList<Impression> impressions, ArrayList<Click> clicks, ArrayList<ServerEntry> serverEntries) {
        ArrayList<Bar> bars = new ArrayList<Bar>();
        return bars;
    }

    public ArrayList<Bar> getBars() {
        return bars;
    }
}