public class Calculations {

    private int impressions = 40000; // number of impressions
    private int clicks = 25000; // number of clicks
    private int uniques = 20000; // number of uniques i.e. number of unique users
    private int bounces = 15000; // number of bounces i.e. clicks on ad without interacting with website
    private int conversions = 10000; // number of conversions i.e. clicks and acts on an ad
    private int totalCost = 30000;
    private int ctr = clicks / impressions; // click-through-rate
    private int cpa = totalCost / conversions; // cost-per-acquisition
    private int cpc = totalCost / clicks; // cost-per-click
    private int cpm = totalCost / 1000; // cost-per-thousand impressions
    private int bounceRate = bounces / clicks;

}
