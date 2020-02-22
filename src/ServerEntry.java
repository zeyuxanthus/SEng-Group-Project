import java.time.LocalDateTime;

/**
 * A single entry from a Server Log
 * Contains information about user's interaction with website after clicking an add
 */
public class ServerEntry {
    private LocalDateTime entryDate;
    private String ID;
    private LocalDateTime exitDate;
    private int pagesViewed;
    private Conversion conversion;

    enum Conversion{
        YES("Yes"),
        NO("No");

        String conversion;

        Conversion(String conversion){
            this.conversion = conversion;
        }
    }
}

