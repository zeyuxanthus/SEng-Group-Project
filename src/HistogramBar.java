/**
 * Contains information about a single HistogramBar in the histogram
 */
public class HistogramBar {

    private int frequency;
    private double lowerBound;
    private double upperBound;

    /**
     * @param frequency - number of costs that fit in the bounds
     * @param lowerBound - minimum cost
     * @param upperBound - maximum cost
     */
    public HistogramBar(int frequency, double lowerBound, double upperBound){
        this.frequency = frequency;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public int getFrequency() {
        return frequency;
    }

    public double getLowerBound() {
        return lowerBound;
    }

    public double getUpperBound() {
        return upperBound;
    }
}