import java.time.LocalDateTime;

/**
 * A single entry from a Server Log
 * Contains information about user's interaction with website after clicking an add
 */
public class ServerEntry {
    private LocalDateTime entryDate;
    private String ID;
    private LocalDateTime exitDate;
    private float pagesViewed;
    private Conversion conversion;

    public ServerEntry(LocalDateTime entryDate, String ID, LocalDateTime exitDate, float pagesViewed, Conversion conversion){
        this.entryDate = entryDate;
        this.ID = ID;
        this.exitDate = exitDate;
        this.pagesViewed = pagesViewed;
        this.conversion = conversion;
    }

    enum Conversion{
        YES("Yes"),
        NO("No");

        String conversion;

        Conversion(String conversion){
            this.conversion = conversion;
        }
    }

    public LocalDateTime getEntryDate(){
        return entryDate;
    }

    public String getID(){
        return ID;
    }

    public LocalDateTime getExitDate(){
        return exitDate;
    }

    public float getPagesViewed(){
        return pagesViewed;
    }

    public Conversion getConversion(){
        return conversion;
    }

}

