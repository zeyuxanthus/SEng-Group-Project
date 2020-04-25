/**
 * Element of Bar Chart to display categorical data
 */
public class Bar<M, String> {
    private M metric;// e.g.: 100 or 12.124 - to be displayed on y axis
    private String category;// e.g.: Monday or 2pm - to be displayed on x axis

    public Bar(M metric, String category){
        this.metric = metric;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public M getMetric() {
        return metric;
    }
}
