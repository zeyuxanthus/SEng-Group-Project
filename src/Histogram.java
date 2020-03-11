import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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
        calculateBars();
    }

    /**
     * Calculate bar ranges and their frequencies to draw them on the histogram
     * Trigger Update for Observers to fetch data
     * //TODO Rounding values according to accuracy
     */
    private void calculateBars(){
        ArrayList<Bar> bars = new ArrayList<Bar>();
        ArrayList<Click> clicks = filterClickLog();
        Collections.sort(clicks);

        // Find range of values
        double lowestCost = clicks.get(0).getClickCost();
        double highestCost = clicks.get(clicks.size() - 1).getClickCost();

        //For partitioning the range
        double lowerBound = (highestCost - lowestCost) / noBars;
        double upperBound = (highestCost - lowestCost) / (noBars - 1);
        double barWidth = (upperBound + lowerBound) / 2;

        // Calculate frequency for each Bar
        double min = lowestCost;
        double max = min + barWidth;
        int frequency = 0;
        for(Click click : clicks){

            double clickCost = click.getClickCost();
            if(clickCost >= min && clickCost < max){
                ++frequency;
            }
            else{
                do{
                    bars.add(new Bar(frequency, min, max));
                    min = max;
                    max = min + barWidth;
                    frequency = 0;
                }while(clickCost > max);
                frequency = 1;
            }
        }
        bars.add(new Bar(frequency, min, max));
        for(Bar bar : bars){
            System.out.println(bar.getLowerBound() + " - " + bar.getUpperBound() + " - " + bar.getFrequency());
        }

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
                if(observer != null) observer.observableChanged(this);
                // TODO remove check after testing
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