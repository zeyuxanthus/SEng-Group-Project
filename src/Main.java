/**
 * AD Auction Dashboard
 */
public class Main {

	public static void main(String[] args) {
		Campaign campaign = new Campaign();
		
		Controller controller = new Controller(campaign);
		GUI gui = new GUI(controller);
	}
}