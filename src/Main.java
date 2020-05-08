import java.util.ArrayList;

/**
 * AD Auction Dashboard
 */
public class Main {

	public static void main(String[] args) {

		Controller controller = new Controller();
//		controller.deserializeCampaign("SerialisedCampaign");
		GUI gui = new GUI(controller);
		controller.setGUI(gui);
		gui.start();
	}
}