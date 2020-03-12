import java.util.ArrayList;

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
	 * Called by the View when user loads file
	 * @param path - path to file + filename
	 */
	public void loadClickLog(String path){
		campaign.loadClickLog(path);
	}
	public void loadImpressionLog(String path){
		campaign.loadImpressionLog(path);
	}
	public void loadServerLog(String path){
		campaign.loadSeverlog(path);
	}

	/**
	 *
	 * @param metric - values shown on y axis
	 * @param timeInterval - interval over which each data point is computed
	 * @param observer - window/object displaying the graph
	 * @param campaign - reference for data
	 */
	public void createLineGraph(Metric metric, TimeInterval timeInterval, Observer observer, Campaign campaign){
		new LineGraph(metric, timeInterval, observer, campaign);
	}

	/**
	 *
	 * @param observer - window/object displaying the graph
	 * @param campaign - reference for data
	 * @param noBars - number of bars/classes in the histogram
	 * @param accuracy - number of decimal places the boundaries of bars are rounded to
	 */
	public void createHistogram(Observer observer, Campaign campaign, int noBars, int accuracy){
		new Histogram(observer, campaign, noBars, accuracy);
	}
}