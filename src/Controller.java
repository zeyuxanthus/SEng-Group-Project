/**
 * Translates user actions into operations on the campaign.
 */
public class Controller {

	private Campaign campaign;

	public Controller(Campaign campaign) {
		this.campaign = campaign;
	}

	public Campaign getCampaign() {
		return campaign;
	}

	/**
	 * @param metric - values shown on y axis
	 * @param timeInterval - interval over which each data point is computed
	 * @param campaign - reference for data
	 * @param filter - list of filters
	 */
	public void createLineGraph(Metric metric, TimeInterval timeInterval, Campaign campaign, Filter filter){
		new LineGraph(metric, timeInterval, campaign, filter);
	}

	/**
	 * @param campaign - reference for data
	 * @param noBars - number of bars/classes in the histogram
	 * @param accuracy - number of decimal places the boundaries of bars are rounded to
	 * @param filter - list of filters
	 */
	public void createHistogram(Campaign campaign, int noBars, int accuracy, Filter filter){
		new Histogram(campaign, noBars, accuracy, filter);
	}


}