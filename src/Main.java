import java.util.ArrayList;

/**
 * AD Auction Dashboard
 */
public class Main {

	public static void main(String[] args) {

		Controller controller = new Controller();
		controller.deserializeCampaign("SerialisedCampaign");
		controller.setGUI(new GUI(controller));

		System.out.println("CreateBarChart");

	}
}