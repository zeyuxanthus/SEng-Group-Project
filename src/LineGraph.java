import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Contains all data and methods needed to display the line chart.
 */

public class LineGraph {

    private Campaign campaign;

    private Metric metric;
    private TimeInterval timeInterval;
    private ArrayList dataPoints;

    private Filter filter;

    public LineGraph(Metric metric, TimeInterval timeInterval, Campaign campaign, Filter filter){
        this.metric = metric;
        this.timeInterval = timeInterval;
        this.campaign = campaign;
        this.filter = filter;
        calculateDataPoints();
    }

    //--CALCULATIONS----------------------------------------------------------------------------------------------------

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
                dataPoints = calculateTotalImpressions();
                break;
            case TOTAL_IMPRESSION_COST:
                dataPoints = calculateImpressionCosts();
                break;
            case TOTAL_CLICKS:
                dataPoints = calculateTotalClicks();
                break;
            case TOTAL_CLICK_COST:
                dataPoints = calculateClickCosts();
                break;
            case TOTAL_COST:
                dataPoints = calculateTotalCosts();
                break;
            case TOTAL_CONVERSIONS:
                dataPoints = calculateTotalConversions();
                break;
            case CONVERSION_RATE:
                dataPoints = calculateConversionRates();
                break;
            case BOUNCES:
                dataPoints = calculateBounces();
                break;
            case BOUNCE_RATE:
                dataPoints = calculateBounceRates();
                break;
            case TOTAL_UNIQUES:
                dataPoints = calculateTotalUniques();
                break;
            case CTR:
                dataPoints = calculateCTRs();
                break;
            case CPA:
                dataPoints = calculateCPAs();
                break;
            case CPC:
                dataPoints = calculateCPCs();
                break;
            case CPM:
                // impression
                break;
        }

    }

    private ArrayList<DataPoint<Integer, LocalDateTime>> calculateTotalImpressions(){
        ArrayList<DataPoint<Integer, LocalDateTime>> dataPoints = new ArrayList<DataPoint<Integer, LocalDateTime>>();
        ArrayList<Impression> impressionLog = campaign.filterImpressionLog(filter);
        Collections.sort(impressionLog);
        LocalDateTime startDateTime = impressionLog.get(0).getDateTime();

        LocalDateTime endDateTime = getEndDateTime(startDateTime);
        int i = 0;
        ArrayList<Impression> impressions = new ArrayList<Impression>();
        while(impressionLog.size() > i){
            Impression impression = impressionLog.get(i);
            if(impression.getDateTime().isBefore(endDateTime)){
                impressions.add(impression);
            }
            else{
                dataPoints.add(new DataPoint<Integer, LocalDateTime>(campaign.calcImpressions(impressions), startDateTime));

                startDateTime = endDateTime;
                endDateTime = getEndDateTime(startDateTime);
                impressions = new ArrayList<Impression>();
                i--;
            }
            i++;
        }
        return dataPoints;
    }

    private ArrayList<DataPoint<Double, LocalDateTime>> calculateImpressionCosts() {
        ArrayList<DataPoint<Double, LocalDateTime>> dataPoints = new ArrayList<DataPoint<Double, LocalDateTime>>();
        ArrayList<Impression> impressionLog = campaign.filterImpressionLog(filter);
        Collections.sort(impressionLog);
        LocalDateTime startDateTime = impressionLog.get(0).getDateTime();

        LocalDateTime endDateTime = getEndDateTime(startDateTime);
        int i = 0;
        ArrayList<Impression> impressions = new ArrayList<Impression>();
        while(impressionLog.size() > i){
            Impression impression = impressionLog.get(i);
            if(impression.getDateTime().isBefore(endDateTime)){
                impressions.add(impression);
            }
            else{
                dataPoints.add(new DataPoint<Double, LocalDateTime>(campaign.calcTotalImpCost(impressions), startDateTime));

                startDateTime = endDateTime;
                endDateTime = getEndDateTime(startDateTime);
                impressions = new ArrayList<Impression>();
                i--;
            }
            i++;
        }
        return dataPoints;
    }

    private ArrayList<DataPoint<Integer, LocalDateTime>> calculateTotalClicks() {
        ArrayList<DataPoint<Integer, LocalDateTime>> dataPoints = new ArrayList<DataPoint<Integer, LocalDateTime>>();
        ArrayList<Click> clickLog = campaign.filterClickLog(filter);
        clickLog.sort(getClickComparator());

        LocalDateTime startDateTime = clickLog.get(0).getDateTime();
        LocalDateTime endDateTime = getEndDateTime(startDateTime);
        int i = 0;
        ArrayList<Click> clicks = new ArrayList<Click>();
        while(clickLog.size() > i){
            Click click = clickLog.get(i);
            if(click.getDateTime().isBefore(endDateTime)){
                clicks.add(click);
            }
            else{
                dataPoints.add(new DataPoint<Integer, LocalDateTime>(campaign.calcClicks(clicks), startDateTime));
                startDateTime = endDateTime;
                endDateTime = getEndDateTime(startDateTime);
                clicks = new ArrayList<Click>();
                i--;
            }
            i++;
        }
        return dataPoints;
    }

    private ArrayList<DataPoint<Double, LocalDateTime>> calculateClickCosts() {
        ArrayList<DataPoint<Double, LocalDateTime>> dataPoints = new ArrayList<DataPoint<Double, LocalDateTime>>();
        ArrayList<Click> clickLog = campaign.filterClickLog(filter);
        clickLog.sort(getClickComparator());

        LocalDateTime startDateTime = clickLog.get(0).getDateTime();
        LocalDateTime endDateTime = getEndDateTime(startDateTime);
        int i = 0;
        ArrayList<Click> clicks = new ArrayList<Click>();
        while(clickLog.size() > i){
            Click click = clickLog.get(i);
            if(click.getDateTime().isBefore(endDateTime)){
                clicks.add(click);
            }
            else{
                dataPoints.add(new DataPoint<Double, LocalDateTime>(campaign.calcTotalClickCost(clicks), startDateTime));
                startDateTime = endDateTime;
                endDateTime = getEndDateTime(startDateTime);
                clicks = new ArrayList<Click>();
                i--;
            }
            i++;
        }
        return dataPoints;
    }

    private ArrayList<DataPoint<Double, LocalDateTime>> calculateTotalCosts() {
        ArrayList<DataPoint<Double, LocalDateTime>> dataPoints = new ArrayList<DataPoint<Double, LocalDateTime>>();
        ArrayList<DataPoint<Double, LocalDateTime>> clickCosts = calculateClickCosts();
        ArrayList<DataPoint<Double, LocalDateTime>> impressionCosts = calculateImpressionCosts();

        for(int i = 0; i < clickCosts.size(); i++){
            dataPoints.add(new DataPoint<Double, LocalDateTime>((clickCosts.get(i).getMetric() + impressionCosts.get(i).getMetric()), clickCosts.get(i).getStartTime()));
        }

        return dataPoints;
    }

    private ArrayList<DataPoint<Integer, LocalDateTime>> calculateTotalConversions() {
        ArrayList<DataPoint<Integer, LocalDateTime>> dataPoints = new ArrayList<DataPoint<Integer, LocalDateTime>>();
        ArrayList<ServerEntry> serverEntriesLog = campaign.filterServerLog(filter);
        Collections.sort(serverEntriesLog);
        LocalDateTime startDateTime = serverEntriesLog.get(0).getEntryDate();

        LocalDateTime endDateTime = getEndDateTime(startDateTime);
        int i = 0;
        ArrayList<ServerEntry> serverEntries = new ArrayList<ServerEntry>();
        while(serverEntriesLog.size() > i){
            ServerEntry serverEntry = serverEntriesLog.get(i);
            if(serverEntry.getEntryDate().isBefore(endDateTime)){
                serverEntries.add(serverEntry);
            }
            else{
                dataPoints.add(new DataPoint<Integer, LocalDateTime>(campaign.calcConversions(serverEntries), startDateTime));

                startDateTime = endDateTime;
                endDateTime = getEndDateTime(startDateTime);
                serverEntries = new ArrayList<ServerEntry>();
                i--;
            }
            i++;
        }
        return dataPoints;
    }

    private  ArrayList<DataPoint<Double, LocalDateTime>> calculateConversionRates() {
        ArrayList<DataPoint<Double, LocalDateTime>> dataPoints = new ArrayList<DataPoint<Double, LocalDateTime>>();
        ArrayList<DataPoint<Integer, LocalDateTime>> totalConversions = calculateTotalConversions();
        ArrayList<DataPoint<Integer, LocalDateTime>> totalClicks = calculateTotalClicks();

        for(int i = 0; i < totalConversions.size(); i++){
            dataPoints.add(new DataPoint<Double, LocalDateTime>((double)(totalConversions.get(i).getMetric() / totalClicks.get(i).getMetric()), totalConversions.get(i).getStartTime()));
        }

        return dataPoints;
    }

    private ArrayList<DataPoint<Integer, LocalDateTime>> calculateBounces() {
        ArrayList<DataPoint<Integer, LocalDateTime>> dataPoints = new ArrayList<DataPoint<Integer, LocalDateTime>>();
        ArrayList<ServerEntry> serverEntriesLog = campaign.filterServerLog(filter);
        Collections.sort(serverEntriesLog);
        LocalDateTime startDateTime = serverEntriesLog.get(0).getEntryDate();

        LocalDateTime endDateTime = getEndDateTime(startDateTime);
        int i = 0;
        ArrayList<ServerEntry> serverEntries = new ArrayList<ServerEntry>();
        while(serverEntriesLog.size() > i){
            ServerEntry serverEntry = serverEntriesLog.get(i);
            if(serverEntry.getEntryDate().isBefore(endDateTime)){
                serverEntries.add(serverEntry);
            }
            else{
                dataPoints.add(new DataPoint<Integer, LocalDateTime>(campaign.calcBounces(serverEntries), startDateTime));

                startDateTime = endDateTime;
                endDateTime = getEndDateTime(startDateTime);
                serverEntries = new ArrayList<ServerEntry>();
                i--;
            }
            i++;
        }
        return dataPoints;
    }

    private ArrayList<DataPoint<Double, LocalDateTime>> calculateBounceRates() {
        ArrayList<DataPoint<Double, LocalDateTime>> dataPoints = new ArrayList<DataPoint<Double, LocalDateTime>>();
        ArrayList<DataPoint<Integer, LocalDateTime>> bounces = calculateBounces();
        ArrayList<DataPoint<Integer, LocalDateTime>> impressionCosts = calculateTotalImpressions();

        for(int i = 0; i < bounces.size(); i++){
            dataPoints.add(new DataPoint<Double, LocalDateTime>((double)(bounces.get(i).getMetric() / impressionCosts.get(i).getMetric()), bounces.get(i).getStartTime()));
        }

        return dataPoints;
    }

    private ArrayList<DataPoint<Integer, LocalDateTime>> calculateTotalUniques() {
        ArrayList<DataPoint<Integer, LocalDateTime>> dataPoints = new ArrayList<DataPoint<Integer, LocalDateTime>>();
        ArrayList<Click> clickLog = campaign.filterClickLog(filter);
        clickLog.sort(getClickComparator());

        LocalDateTime startDateTime = clickLog.get(0).getDateTime();
        LocalDateTime endDateTime = getEndDateTime(startDateTime);
        int i = 0;
        ArrayList<Click> clicks = new ArrayList<Click>();
        while(clickLog.size() > i){
            Click click = clickLog.get(i);
            if(click.getDateTime().isBefore(endDateTime)){
                clicks.add(click);
            }
            else{
                dataPoints.add(new DataPoint<Integer, LocalDateTime>(campaign.calcUniques(clicks), startDateTime));
                startDateTime = endDateTime;
                endDateTime = getEndDateTime(startDateTime);
                clicks = new ArrayList<Click>();
                i--;
            }
            i++;
        }
        return dataPoints;
    }

    private ArrayList<DataPoint<Double, LocalDateTime>> calculateCTRs() {
        ArrayList<DataPoint<Double, LocalDateTime>> dataPoints = new ArrayList<DataPoint<Double, LocalDateTime>>();
        ArrayList<DataPoint<Integer, LocalDateTime>> clicks = calculateTotalClicks();
        ArrayList<DataPoint<Integer, LocalDateTime>> impressions = calculateTotalImpressions();

        for(int i = 0; i < clicks.size(); i++){ //TODO add protection when arraylists have different sizes
            dataPoints.add(new DataPoint<Double, LocalDateTime>((double)(clicks.get(i).getMetric() / impressions.get(i).getMetric()), clicks.get(i).getStartTime()));
        }
        return dataPoints;
    }

    private ArrayList<DataPoint<Double, LocalDateTime>> calculateCPAs() {
        ArrayList<DataPoint<Double, LocalDateTime>> dataPoints = new ArrayList<DataPoint<Double, LocalDateTime>>();
        ArrayList<DataPoint<Double, LocalDateTime>> totalCosts = calculateTotalCosts();
        ArrayList<DataPoint<Integer, LocalDateTime>> totalConversions = calculateTotalConversions();

        for(int i = 0; i < totalCosts.size(); i++){
            dataPoints.add(new DataPoint<Double, LocalDateTime>((double)(totalCosts.get(i).getMetric() / totalConversions.get(i).getMetric()), totalCosts.get(i).getStartTime()));
        }

        return dataPoints;
    }

    private ArrayList<DataPoint<Double, LocalDateTime>> calculateCPCs() {
        ArrayList<DataPoint<Double, LocalDateTime>> dataPoints = new ArrayList<DataPoint<Double, LocalDateTime>>();
        ArrayList<Click> clickLog = campaign.filterClickLog(filter);
        clickLog.sort(getClickComparator());

        LocalDateTime startDateTime = clickLog.get(0).getDateTime();
        LocalDateTime endDateTime = getEndDateTime(startDateTime);
        int i = 0;
        ArrayList<Click> clicks = new ArrayList<Click>();
        while(clickLog.size() > i){
            Click click = clickLog.get(i);
            if(click.getDateTime().isBefore(endDateTime)){
                clicks.add(click);
            }
            else{
                dataPoints.add(new DataPoint<Double, LocalDateTime>(campaign.calcCPC(clicks), startDateTime));
                startDateTime = endDateTime;
                endDateTime = getEndDateTime(startDateTime);
                clicks = new ArrayList<Click>();
                i--;
            }
            i++;
        }
        return dataPoints;
    }

    /**
     * Increments the startDate of the interval by the amount corresponding to timeInterval
     * @param startDateTime starting dateTime of the interval
     * @return ending dateTime of the interval
     */
    private LocalDateTime getEndDateTime(LocalDateTime startDateTime){
        LocalDateTime endDateTime;
        switch(timeInterval){
            case HOUR:
                endDateTime = startDateTime.plusHours(1);
                break;
            case DAY:
                endDateTime = startDateTime.plusDays(1);
                break;
            case WEEK:
                endDateTime= startDateTime.plusWeeks(1);
                break;
            case MONTH:
                endDateTime = startDateTime.plusMonths(1);
                break;
            default:
                endDateTime = startDateTime; //TODO handle this case differently
                 break;
        }
        return endDateTime;
    }

    public ArrayList<DataPoint> getDataPoints(){ return dataPoints; }


    private Comparator<Click> getClickComparator(){
        Comparator<Click> dateComparator = new Comparator<Click>(){

            @Override
            public int compare(Click c1, Click c2) {
                return c1.getDateTime().compareTo(c2.getDateTime());
            }
        };
        return dateComparator;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }
}