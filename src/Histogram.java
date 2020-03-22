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
        ArrayList<Click> clicks = filterClickLog();
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

    /**
     * Fetch click log from campaign
     * Remove costs = 0
     * Apply all filters
     * @return filtered click log
     */
    private ArrayList<Click> filterClickLog(){
        //for testing
        //long startTime = System.nanoTime();

        // Predicate for 0 costs
        //TODO discuss if its needed
        //Predicate<Click> clickCostPredicate = c -> c.getClickCost() > 0;

        // Predicate for startDate
        Predicate<Click> startDatePredicate;
        if(filter.getStartDate() != null){
            startDatePredicate = c -> c.getDateTime().isAfter(filter.getStartDate()); // TODO Discuss if it needs to be inclusive
        }
        else{
            System.out.println("startDate is null");
            startDatePredicate = c -> true;
        }

        // Predicate for endDate
        Predicate<Click> endDatePredicate;
        if(filter.getEndDate() != null){
            endDatePredicate = c -> c.getDateTime().isBefore(filter.getEndDate());
        }
        else{
            System.out.println("endDate is null");
            endDatePredicate = c -> true;
        }

        // Predicates for contexts
        Predicate<Click> contextsPredicate;
        if(!filter.getContexts().isEmpty()){
            contextsPredicate = c -> matchContext(c.getContext());
        }
        else{
            System.out.println("contexts are empty");
            contextsPredicate = c -> true;
        }

        // Predicate for gender
        Predicate<Click> genderPredicate;
        if(filter.getGender() != null){
            genderPredicate = c -> c.getGender().equals(filter.getGender());
        }
        else{
            System.out.println("gender is null");
            genderPredicate = c -> true;
        }

        // Predicates for ageGroups
        Predicate<Click> ageGroupPredicate;
        if(!filter.getAgeGroups().isEmpty()){
            ageGroupPredicate = c -> matchAgeGroup(c.getAgeGroup());
        }
        else{
            System.out.println("ageGroup is empty");
            ageGroupPredicate = c -> true;
        }

        // Predicate for incomes
        Predicate<Click> incomePredicate;
        if(!filter.getIncomes().isEmpty()){
            incomePredicate = c -> matchIncome(c.getIncome());
        }
        else{
            System.out.println("income is empty");
            incomePredicate = c -> true;
        }

        ArrayList<Click> clicks = campaign.getClicks();
        ArrayList<Click> filteredClicks = (ArrayList<Click>) clicks.stream().filter
                (startDatePredicate.and(endDatePredicate.and(contextsPredicate).and(genderPredicate).and(ageGroupPredicate).and(incomePredicate))).collect(Collectors.toList());

        //for testing
        //long endTime = System.nanoTime();
        //System.out.println("Method took:" + (endTime - startTime) / 1000000);

        return filteredClicks;
    }

    /**
     * Checks if there exists a context in a list of contexts (filters) that matches given context
     * @param context - given context
     * @return - true if there is a match
     */
    private boolean matchContext(String context){
        for(String c : filter.getContexts()){
            if(c.equals(context))
                return true;
        }
        return false;
    }

    /**
     * Checks if there exists an ageGroup in a list of ageGroups that matches given ageGroup
     * @param ageGroup - given ageGroup
     * @return - true if there is a match
     */
    private boolean matchAgeGroup(String ageGroup){
        for(String a : filter.getAgeGroups()){
            if(a.equals(ageGroup))
                return true;
        }
        return false;
    }

    /**
     * Checks if there exists an income in a list of incomes that matches given income
     * @param income - given income
     * @return - true if there is a match
     */
    private boolean matchIncome(String income){
        for(String i : filter.getIncomes()){
            if(i.equals(income))
                return true;
        }
        return false;
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