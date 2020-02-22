import java.time.LocalDateTime;

/**
 * Contains information about a single impression.
 * An impression occurs	whenever an ad is shown to a user, regardless of whether
 * they	click onit.
 */
public class Impression {

    private LocalDateTime dateTime; // date and time
    private String ID; // Uniquely identifies a user
    private Gender gender;
    private AgeGroup ageGroup;
    private Income income; // Low, Medium or High
    private Context context; // Context of ad, e.g. Blog, News, Shopping
    private float impressionCost; // in pence
                                  // @TODO check if data doesn't contain longer values

    public Impression(String ID, AgeGroup ageGroup, LocalDateTime dateTime, Gender gender, Income income, Context context, float impressionCost){

        this.ID = ID;
        this.ageGroup = ageGroup;
        this.dateTime = dateTime;
        this.gender = gender;
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
}