import javafx.util.Pair;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Contains all data and methods needed to display the line chart.
 */
public class LineGraph implements Chart, Observable {

    private Campaign campaign;

    private Metric metric;
    private TimeInterval timeInterval;
    private ArrayList<Pair> dataPoints;

    private List<Observer> observers = new LinkedList<Observer>(); // this will contain the window that displays the chart

    // Filters (null/empty means all values are accepted)
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ArrayList<String> contexts = new ArrayList<String>();
    private String gender;
    private ArrayList<String> ageGroups = new ArrayList<String>();
    private ArrayList<String> incomes = new ArrayList<String>();

    public LineGraph(Metric metric, TimeInterval timeInterval, Observer observer, Campaign campaign){
        this.metric = metric;
        this.timeInterval = timeInterval;
        observers.add(observer);
        this.campaign = campaign;
        calculateDataPoints();
    }

    /**
     * Use campaign's reference to get Campaign's data and calculation methods.
     * Apply filters to campaign's data
     * Calculates data points to be displayed on the chart.
     * dataPoints' Pair contains value of the metric computed over some time and that time period
     * (e.g. <30 bounces, 9am - 10am on 29/02/2020>)
     * TriggerUpdate at the end so the View can get the dataPoints.
     */
    private void calculateDataPoints(){

        switch (metric){
            case TOTAL_IMPRESSIONS:
                //impression
                break;
            case TOTAL_IMPRESSION_COST:
                //impression
                break;
            case TOTAL_CLICKS:
                //click
                break;
            case TOTAL_CLICK_COST:
                //click
                break;
            case TOTAL_COST:
                //cost
                break;
            case TOTAL_CONVERSIONS:
                break;
            case CONVERSION_RATE:
                break;
            case BOUNCES:
                break;
            case BOUNCE_RATE:
                break;
            case TOTAL_UNIQUES:
                break;
            case CTR:
                break;
            case CPA:
                break;
            case CPC:
                break;
            case CPM:
                break;
        }



        triggerUpdate();
    }

    private ArrayList<Impression> filterImpressionLog(){

        return null;
    }

    private ArrayList<Click> filterServerLog(){

        return null;
    }

    private ArrayList<ServerEntry> filterClickLog(){

        return null;
    }

    public void setDateRange(LocalDateTime startDate, LocalDateTime endDate){
        this.startDate = startDate;
        this.endDate = endDate;
        calculateDataPoints();
    }

    public void removeDateRange(){
        startDate = null;
        endDate = null;
    }

    public void addContext(String context){
        contexts.add(context);
        calculateDataPoints();
    }

    public void removeContext(String context){
        contexts.remove(context);
        calculateDataPoints();
    }

    public void setGender(String gender){
        this.gender = gender;
        calculateDataPoints();
    }

    public void addAgeGroup(String ageGroup){
        ageGroups.add(ageGroup);
        calculateDataPoints();
    }

    public void removeAgeGroup(String ageGroup){
        ageGroups.remove(ageGroup);
        calculateDataPoints();
    }

    public void addIncome(String income){
        incomes.add(income);
        calculateDataPoints();
    }

    public void removeIncome(String income){
        incomes.remove(income);
        calculateDataPoints();
    }

    public ArrayList<Pair> getDataPoints(){ return dataPoints; }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Observers should respond to this by calling getDataPoints()
     */
    private void triggerUpdate() {
        for (Observer observer : observers) {
            observer.observableChanged(this);
        }
    }
}