import java.time.LocalDateTime;

/**
 * A single entry from a Server Log
 * Contains information about user's interaction with website after clicking an add
 */
public class ServerEntry implements Comparable<ServerEntry>{
    private LocalDateTime entryDate;
    private String ID;
    private LocalDateTime exitDate;
    private int pagesViewed;
    private String conversion;

  public ServerEntry(LocalDateTime entryDate, String id, LocalDateTime exitDate, int pagesViewed, String conversion) {
        this.entryDate = entryDate;
        this.ID = id;
        this.exitDate = exitDate;
        this.pagesViewed = pagesViewed;
        this.conversion = conversion;
    }

    @Override
    public int compareTo(ServerEntry i) {
        return entryDate.compareTo(i.getEntryDate());
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

    public int getPagesViewed(){
        return pagesViewed;
    }

    public String getConversion(){
        return conversion;
    }
    
    @Override
    public String toString() {
        return entryDate + " " + ID + " " + exitDate +
                " " + pagesViewed + " " + conversion + System.lineSeparator();
    }

}

