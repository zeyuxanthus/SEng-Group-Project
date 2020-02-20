public class Calculations {

    public int impressions = 40000; // number of impressions
    public int clicks = 25000; // number of clicks
    public int uniques = 20000; // number of uniques i.e. number of unique users
    public int bounces = 15000; // number of bounces i.e. clicks on ad without interacting with website
    public int conversions = 10000; // number of conversions i.e. clicks and acts on an ad
    public int totalCost = 30000;
    public int ctr = clicks / impressions; // click-through-rate
    public int cpa = totalCost / conversions; // cost-per-acquisition
    public int cpc = totalCost / clicks; // cost-per-click
    public int cpm = totalCost / 1000; // cost-per-thousand impressions
    public int bounceRate = bounces / clicks;

}
