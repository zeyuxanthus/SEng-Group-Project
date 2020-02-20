public class Values {

    public int impressions = 100; // number of impressions
    public int clicks; // number of clicks
    public int uniques; // number of uniques i.e. number of unique users
    public int bounces; // number of bounces i.e. clicks on ad without interacting with website
    public int conversions; // number of conversions i.e. clicks and acts on an ad
    public int totalCost;
    public int ctr; // click-through-rate
    public int cpa; // cost-per-acquisition
    public int cpc; // cost-per-click
    public int cpm; // cost-per-thousand impressions
    public int bounceRate;

   public Values(int impressions, int clicks, int bounces, int conversions, int totalCost){
       this.impressions = impressions;
       this.clicks = clicks;
       this.bounces = bounces;
       this.conversions = conversions;
       this.totalCost = totalCost;
   }

    public Values() {

    }

    public void setImpressions(int impressions) {
        this.impressions = impressions;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public void setUniques(int uniques) {
        this.uniques = uniques;
    }

    public void setBounces(int bounces) {
        this.bounces = bounces;
    }

    public void setConversions(int conversions) {
        this.conversions = conversions;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public int getBounces() {
        return bounces;
    }

    public int getClicks() {
        return clicks;
    }

    public int getConversions() {
        return conversions;
    }

    public int getImpressions() {
        return impressions;
    }

    public int getUniques() {
        return uniques;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public int getCpa() {
        return cpa;
    }

    public int getCtr() {
        return ctr;
    }

    public int getCpc() {
        return cpc;
    }

    public int getBounceRate() {
        return bounceRate;
    }

    public int getCpm() {
        return cpm;
    }
}
