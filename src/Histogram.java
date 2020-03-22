import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Contains data needed to draw histogram
 */
public class Histogram {

    private Campaign campaign;

    private int noBars;
    private int accuracy;
    private ArrayList<Bar> bars;
    private Filter filter;

    /**
     * @param campaign reference for accessing campaign's data
     * @param noBars - number of bars/classes in the histogram
     * @param accuracy - number of decimal places the boundaries of bars are rounded to
     * @param filter - a set of filters for this chart
     */
    public Histogram(Campaign campaign, int noBars, int accuracy, Filter filter){
        this.campaign = campaign;
        this.noBars = noBars;
        this.accuracy = accuracy;
        this.filter = filter;

        //Test filters to be removed
//        contexts.add("Blog");
//        contexts.add("News");
//        ageGroups.add("25-34");
//        ageGroups.add("<25");
//        incomes.add("High");
//        incomes.add("Low");
        calculateBars();
    }
    
    /**
     * Calculate bar ranges and their frequencies to draw them on the histogram
     * Trigger Update for Observers to fetch data
     * //TODO Rounding values according to accuracy
     */
    private void calculateBars(){
        ArrayList<Bar> bars = new ArrayList<Bar>();
        ArrayList<Click> clicks = campaign.filterClickLog(filter);
        Collections.sort(clicks);

        // Find range of values
        double lowestCost = clicks.get(0).getClickCost();
        double highestCost = clicks.get(clicks.size() - 1).getClickCost();

        // Calculate bar width
        double lowerBound = (highestCost - lowestCost) / noBars;
        double upperBound = (highestCost - lowestCost) / (noBars - 1);
        double round = 1.0;
        for(int i = 0; i < accuracy; i++){
            round *= 10.0;
        }
        double barWidth = Math.round(((upperBound + lowerBound) / 2) * round) / round;

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

        // For testing
//        for(Bar bar : bars){
//            System.out.println(bar.getLowerBound() + " - " + bar.getUpperBound() + " - " + bar.getFrequency());
//        }

        this.bars = bars;
    }

    public ArrayList<Bar> getBars(){
    	return bars;
    }

    public void setFilter(Filter filter){
        this.filter = filter;
    }

 

    public int getNoBars() {
        return noBars;
    }
}