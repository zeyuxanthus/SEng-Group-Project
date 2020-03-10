/**
 * Contains information about a single Bar in the histogram
 */
public class Bar {

    private int frequency;
    private double lowerBound;
    private double upperBound;

    /**
     *
     * @param frequency - number of costs that fit in the bounds
     * @param lowerBound - minimum cost (inclusive
     * @param upperBound - maximum cost
     */
    public Bar(int frequency, double lowerBound, double upperBound){
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