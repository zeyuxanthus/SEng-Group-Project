/**
 * AD Auction Dashboard
 */
public class Main {

	public static void main(String[] args) {
		Campaign campaign = new Campaign();
		campaign.loadClickLog(System.getProperty("user.dir") + "\\click_log.csv");
		campaign.loadSeverlog(System.getProperty("user.dir") + "\\server_log.csv");
		campaign.loadImpressionLog(System.getProperty("user.dir") + "\\impression_log.csv");
		campaign.calculateMetrics();
		Controller controller = new Controller(campaign);
		View view = new View(controller);
	}
}