/**
 * Translates user actions into operations on the campaign.
 */
public class Controller {

	private Campaign campaign;
	private GUI gui;

	public void setGUI(GUI gui){
		this.gui = gui;
	}

	/**
	 * Create new campaign by loading log Files
	 * @param bounceDefinition - how bounces are registered for this campaign
	 */
	public void loadNewCampaign(String serverFilePath, String clickFilePath, String impressionFilePath, int bounceDefinition){
		campaign = new Campaign(serverFilePath, clickFilePath, impressionFilePath, bounceDefinition);
	}

	public double getBounceRate(){
		return campaign.getBounceRate();
	}

	public int getTotalImpressions(){
		return campaign.getTotalImpressions();
	}

	public int getTotalClicks(){
		return campaign.getTotalClicks();
	}

	public int getTotalUnique(){
		return campaign.getTotalUnique();
	}

	public int getTotalBounces(){
		return campaign.getTotalBounces();
	}

	public int getTotalConversions(){
		return campaign.getTotalConversions();
	}

	public double getCTR(){
		return campaign.getCTR();
	}

	public double getCPA(){
		return campaign.getCPA();
	}

	public double getCPC(){
		return campaign.getCTR();
	}

	public double getCPM(){
		return campaign.getCTR();
	}

	public double getTotalCost(){
		return campaign.getTotalCost();
	}

	public double getConversionRate(){
		return campaign.getConversionRate();
	}

	/**
	 * @param metric - values shown on y axis
	 * @param timeInterval - interval over which each data point is computed
	 * @param filter - list of filters
	 */
	public void createLineGraph(Metric metric, TimeInterval timeInterval,  Filter filter){
		new LineGraph(metric, timeInterval, campaign, filter);
	}

	/**
	 * @param noBars - number of bars/classes in the histogram
	 * @param accuracy - number of decimal places the boundaries of bars are rounded to
	 * @param filter - list of filters
	 */
	public Histogram createHistogram(int noBars, int accuracy, Filter filter){
		return new Histogram(campaign, noBars, accuracy, filter);
	}


}