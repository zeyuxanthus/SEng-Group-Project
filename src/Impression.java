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

    enum AgeGroup{
        YOUNGER("<25"),
        YOUNG("25-34"),
        MID("35-44"),
        OLD("45-54"),
        OLDER(">54");

        String ageGroup;

        AgeGroup(String ageGroup) {
            this.ageGroup = ageGroup;
        }
    }

    enum Context{
        NEWS("News"),
        SHOPPING("Shopping"),
        SOCIAL_MEDIA("Social Media"),
        BLOG("Blog"),
        HOBBIES("Hobbies"),
        TRAVEL("Travel");

        String context;

        Context(String context){
            this.context = context;
        }
    }

    enum Gender{
        MALE("Male"),
        FEMALE("Female");

        String gender;

        Gender(String gender){
            this.gender = gender;
        }
    }

    enum Income{
        LOW("Low"),
        MEDIUM("Medium"),
        HIGH("High");

        String income;

        Income(String income){
            this.income = income;
        }
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
