import java.time.LocalDateTime;

/**
 * Information about a single Click
 */
public class Click {

    private LocalDateTime dateTime; // date and time
    private String ID; // uniquely identifies a user
    private float clickCost; // in pence (float is up to 6 or 7 digits )
                             // @TODO check if data doesn't contain longer values

}