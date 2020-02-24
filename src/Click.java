import java.time.LocalDateTime;

/**
 * Information about a single Click
 */
public class Click {

    private LocalDateTime dateTime; // date and time
    private String ID; // uniquely identifies a user
    private float clickCost; // in pence (float is up to 6 or 7 digits )
                             // @TODO check if data doesn't contain longer values

    public Click(LocalDateTime dateTime, String ID, float clickCost){
        this.dateTime = dateTime;
        this.ID = ID;
        this.clickCost = clickCost;
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

}