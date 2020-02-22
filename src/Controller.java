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
}