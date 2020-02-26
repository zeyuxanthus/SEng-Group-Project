package com.company;

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
}