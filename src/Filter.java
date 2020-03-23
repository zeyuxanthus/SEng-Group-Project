import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * A set of filters to be used by charts
 * Filters represent what the predicates will accept (null values means it accepts everything)
 */
public class Filter {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ArrayList<String> contexts;
    private String gender;
    private ArrayList<String> ageGroups;
    private ArrayList<String> incomes;

    public Filter(LocalDateTime startDate, LocalDateTime endDate, ArrayList<String> contexts, String gender, ArrayList<String> ageGroups,  ArrayList<String> incomes){
        this.startDate = startDate;
        this.endDate = endDate;
        this.contexts = contexts;
        this.gender = gender;
        this.ageGroups = ageGroups;
        this.incomes = incomes;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public ArrayList<String> getContexts() {
        return contexts;
    }

    public String getGender() {
        return gender;
    }

    public ArrayList<String> getAgeGroups() {
        return ageGroups;
    }

    public ArrayList<String> getIncomes() {
        return incomes;
    }
}