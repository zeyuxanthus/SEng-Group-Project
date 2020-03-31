import java.util.ArrayList;
import java.util.Collections;

/**
 * Contains data needed to draw histogram
 */
public class Histogram {

    private Campaign campaign;

    private int noBars;
    private int accuracy;
    private ArrayList<HistogramBar> histogramBars;
    private Filter filter;

    /**
     * @param campaign reference for accessing campaign's data
     * @param noBars - number of histogramBars/classes in the histogram
     * @param accuracy - number of decimal places the boundaries of histogramBars are rounded to
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
        ArrayList<HistogramBar> histogramBars = new ArrayList<HistogramBar>();
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

        // Calculate frequency for each HistogramBar
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
                    histogramBars.add(new HistogramBar(frequency, min, max));
                    min = max;
                    max = min + barWidth;
                    frequency = 0;
                }while(clickCost > max);
                frequency = 1;
            }
        }
        histogramBars.add(new HistogramBar(frequency, min, max));

        // For testing
//        for(HistogramBar bar : histogramBars){
//            System.out.println(bar.getLowerBound() + " - " + bar.getUpperBound() + " - " + bar.getFrequency());
//        }

        this.histogramBars = histogramBars;
    }

    public ArrayList<HistogramBar> getHistogramBars(){
    	return histogramBars;
    }

    public void setFilter(Filter filter){
        this.filter = filter;
    }

 

    public int getNoBars() {
        return noBars;
    }
}