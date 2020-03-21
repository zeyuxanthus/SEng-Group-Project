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
    private String conversion;



    private String gender;
    private String income;
    private String ageGroup;
    private String context;
    private float impressionCost;



    public ServerEntry(LocalDateTime entryDate, String id, LocalDateTime exitDate, int pagesViewed, String conversion) {
        this.entryDate = entryDate;
        this.ID = id;
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
