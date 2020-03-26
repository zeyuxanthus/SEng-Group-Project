/**
 * AD Auction Dashboard
 */
public class Main {

	public static void main(String[] args) {
		Controller controller = new Controller();
		controller.setGUI(new GUI(controller));
	}
}