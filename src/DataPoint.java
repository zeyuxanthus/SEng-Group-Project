public class DataPoint<M, LocalDateTime> {

    private M metric; // double or int
    private LocalDateTime startTime;

    public DataPoint(M metric, LocalDateTime startTime){
        this.metric = metric;
        this.startTime = startTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public M getMetric() {
        return metric;
    }
}
