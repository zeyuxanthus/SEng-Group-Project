import java.time.LocalDateTime;

/**
 * Information about a single Click
 */
public class Click implements Comparable<Click> {

    private LocalDateTime dateTime; // date and time
    private String ID; // uniquely identifies a user
    private float clickCost; // in pence (float is up to 6 or 7 digits )

    // Impression Log stuff
    private String gender;
    private String income;
    private String ageGroup;
    private String context;
    private float impressionCost;


    // @TODO check if data doesn't contain longer values

    public Click(LocalDateTime dateTime, String ID, float clickCost){
        this.dateTime = dateTime;
        this.ID = ID;
        this.clickCost = clickCost;

    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public void setIncome(String income){
        this.income = income;
    }

    public void setAgeGroup(String ageGroup){
        this.ageGroup = ageGroup;
    }

    public void setContext(String context){
        this.context = context;
    }

    public void setImpressionCost(float impCost){
        this.impressionCost = impCost;
    }

    public String getGender(){
        return gender;
    }


    public LocalDateTime getDateTime(){
        return dateTime;
    }

    public String getID(){
        return ID;
    }

    public float getClickCost(){
        return  clickCost;
    }

    @Override
    public String toString() {
        return dateTime + " " + ID + " " + clickCost + System.lineSeparator();
    }

    @Override
    public int compareTo(Click click) {
        if(clickCost < click.getClickCost()){
            return -1;
        }
        else if (clickCost > click.getClickCost()){
            return 1;
        }
        return 0;
    }

    public String getIncome() {
        return income;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public String getContext() {
        return context;
    }

    public float getImpressionCost() {
        return impressionCost;
    }
}
