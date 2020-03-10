import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Contains data needed to draw histogram
 */
public class Histogram implements Chart, Observable {

    private Campaign campaign;
    private List<Observer> observers = new LinkedList<Observer>();

    private int noBars;
    private ArrayList<Bar> bars = new ArrayList<Bar>();

    // Filters (null/empty means all values are accepted)
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ArrayList<String> contexts = new ArrayList<String>();
    private String gender;
    private ArrayList<String> ageGroups = new ArrayList<String>();
    private ArrayList<String> incomes = new ArrayList<String>();

    /**
     * @param observer the view that displays the chart
     * @param campaign reference for accessing campaign's data
     */
    public Histogram(Observer observer, Campaign campaign, int noBars){
        addObserver(observer);
        this.campaign = campaign;
        this.noBars = noBars;
        calculateDataPoints();
    }

    /**
     * Calculate data Points to draw bars on the histogram
     * Trigger Update for Observers to fetch data
     */
    private void calculateDataPoints(){
        ArrayList<Bar> bars = new ArrayList<Bar>();
        ArrayList<Click> clicks = filterClickLog();
        //TODO implement calculation
        this.bars = bars;
        triggerUpdate();
    }

    /**
     * Fetch click log from campaign
     * Remove costs = 0
     * Apply all filters
     * @return filtered click log
     */
    private ArrayList<Click> filterClickLog(){
        ArrayList<Click> clicks = campaign.getClicks();
        //TODO implement filtering
        return clicks;
    }

    /**
     * Should be called whenever dataPoints have been recalculated
     */
    private void triggerUpdate() {
        for (Observer observer : observers) {
                observer.observableChanged(this);
        }
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public int getNoBars() {
        return noBars;
    }
}