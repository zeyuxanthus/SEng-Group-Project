public class Calculations {
    public static void main(String[] args) {

        Values figures = new Values();

        int impressions = figures.getImpressions();
        int clicks = figures.getClicks();
        int uniques = figures.getUniques();
        int bounces = figures.getBounces();
        int conversions = figures.getConversions();
        int totalCost = figures.getTotalCost();

        System.out.println(impressions);

    }
}
