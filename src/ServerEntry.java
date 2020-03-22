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

    // From impression log
    private String gender;
    private String income;
    private String ageGroup;
    private String context;
    private double impressionCost;

    public ServerEntry(LocalDateTime entryDate, String id, LocalDateTime exitDate, int pagesViewed, String conversion) {
        this.entryDate = entryDate;
        this.ID = id;
        this.exitDate = exitDate;
        this.pagesViewed = pagesViewed;
        this.conversion = conversion;
    }


    public String getGender(){
        return gender;
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

    public double getImpressionCost() {
        return impressionCost;
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

    @Override
    public String toString() {
        return entryDate + " " + ID + " " + exitDate +
                " " + pagesViewed + " " + conversion + System.lineSeparator();
    }

    @Override
    public int compareTo(ServerEntry i) {
        return entryDate.compareTo(i.getEntryDate());
    }
}