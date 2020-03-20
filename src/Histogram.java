import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private int accuracy;
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
     * @param noBars - number of bars/classes in the histogram
     * @param accuracy - number of decimal places the boundaries of bars are rounded to
     */
    public Histogram(Campaign campaign, int noBars, int accuracy, String filters){
        //addObserver(observer);
        this.campaign = campaign;
        this.noBars = noBars;
        this.accuracy = accuracy;
        calculateBars(filters);
    }

    /**
     * Calculate bar ranges and their frequencies to draw them on the histogram
     * Trigger Update for Observers to fetch data
     * //TODO Rounding values according to accuracy
     */
    private void calculateBars(String filters){
        ArrayList<Bar> bars = new ArrayList<Bar>();
        List<Click> clicks = filterClickLog(filters);
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
        for(Bar bar : bars){
            System.out.println(bar.getLowerBound() + " - " + bar.getUpperBound() + " - " + bar.getFrequency());
        }

        this.bars = bars;
        //triggerUpdate();
    }

    /**
     * Fetch click log from campaign
     * Remove costs = 0
     * Apply all filters
     * @return filtered click log
     */
    public ArrayList<Click> filterClickLog(String predicate){

        ArrayList<Click> clickList = new ArrayList<>();
        String[] predicates = predicate.split(",");
        long startTime = System.nanoTime();
        

        try {
            Connection conn = campaign.connect();
            String sqlString = "SELECT * FROM (SELECT  clickLog.entry_date, clickLog.id, clickLog.click_cost, impressionLog.age, impressionLog.context, impressionLog.gender, impressionLog.income, impressionLog.impression_cost  FROM clickLog INNER JOIN  impressionLog ON impressionLog.id = clickLog.id  GROUP BY  clickLog.id, clickLog.entry_date) WHERE 1 = 1 ";

            for(int i = 1; i < predicates.length + 1; i++) {

                if (predicates[i - 1].contains(":") && predicates[i - 1].contains("+")) {
                    String[] dates = predicates[i - 1].split(" \\+ ");
                    System.out.println(dates[0]);
                    sqlString += " and entry_date > ";
                    sqlString += "\'" + dates[0] + "\'";
                    sqlString += " and entry_date < ";
                    sqlString += "\'" + dates[1] + "\'";
                }
                switch (predicates[i - 1]) {
                    case "Male":
                        sqlString += " and gender='Male'";
                        break;

                    case "Female":
                        sqlString += " and gender='Female'";
                        break;

                    case "Social Media":
                        sqlString += " and context='Social Media'";
                        break;

                    case "News":
                        sqlString += " and context='News'";
                        break;

                    case "Shopping":
                        sqlString += " and context='Shopping'";
                        break;

                    case "Blog":
                        sqlString += " and context='Blog'";
                        break;

                    case "Low":
                        sqlString += " and income='Low'";
                        break;

                    case "Medium":
                        sqlString += " and income='Medium'";
                        break;

                    case "High":
                        sqlString += " and income='High'";
                        break;

                    case "<25":
                        sqlString += " and age='<25'";
                        break;

                    case "25-34":
                        sqlString += " and age='25-34'";
                        break;

                    case "35-44":
                        sqlString += " and age='35-44'";
                        break;

                    case "44-54":
                        sqlString += " and age='45-54'";
                        break;

                    case ">54":
                        sqlString += " and age='>54'";
                        break;


                }

            }

                PreparedStatement staten = conn.prepareStatement(sqlString);
                System.out.println(sqlString);
                ResultSet rs = staten.executeQuery();


                while (rs.next()) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.0");
                    LocalDateTime dateTime = LocalDateTime.parse(rs.getString("entry_date"), formatter);
                    Click click = new Click(dateTime, rs.getString("id"), rs.getFloat("click_cost"));
                    clickList.add(click);
                }


        }catch(SQLException e){
            System.out.println(e.getErrorCode());
        }
        long endTime = System.nanoTime();
        System.out.println("Method took:" + (endTime - startTime) / 1000000);
        return clickList;
    }

    /**
     * Should be called whenever dataPoints have been recalculated
     */
//    private void triggerUpdate() {
//        for (Observer observer : observers) {
//                if(observer != null) observer.observableChanged(this);
//                // TODO remove check after testing
//        }
//    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public int getNoBars() {
        return noBars;
    }
    
    public ArrayList<Bar> getBars(){
    	return bars;
    }
}