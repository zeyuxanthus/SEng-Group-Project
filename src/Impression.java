import java.time.LocalDateTime;

/**
 * Contains information about a single impression.
 * An impression occurs	whenever an ad is shown to a user, regardless of whether
 * they	click onit.
 */
public class Impression implements Comparable<Impression> {

    private LocalDateTime dateTime; // date and time
    private String ID; // Uniquely identifies a user
    private String gender;
    private String ageGroup;
    private String income; // Low, Medium or High
    private String context; // Context of ad, e.g. Blog, News, Shopping
    private float impressionCost; // in pence
                                  // @TODO check if data doesn't contain longer values

   public Impression(LocalDateTime dateTime, String ID, String gender,String ageGroup,String income,String context,Float impressionCost) {
        this.dateTime = dateTime;
        this.ID = ID;
        this.gender = gender;
        this.ageGroup = ageGroup;
        this.income = income;
        this.context = context;
        this.impressionCost = impressionCost;
    }

    public String getID(){
        return ID;
    }

    public String getAgeGroup(){
        return ageGroup;
    }

    public String getGender(){
        return gender;
    }

    public String getContext(){
        return context;
    }

    public LocalDateTime getDateTime(){
        return dateTime;
    }

    public String getIncome(){
        return income;
    }

    public float getImpressionCost(){
        return impressionCost;
    }
    
     @Override
    public String toString() {
        return dateTime+" "+ID+" "+gender+" "+ageGroup+" "+income+" "+context+" "+impressionCost+System.lineSeparator();
    }

    @Override
    public int compareTo(Impression i) {
        return dateTime.compareTo(i.getDateTime());
    }
}
