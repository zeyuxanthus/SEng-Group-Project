import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Displays Campaign's information by responding to updates.
 * Passes user input to the controller.
 */
public class View extends JFrame implements Observer {

	private Controller controller;
	private Campaign campaign;

	/**
	 * Display initial GUI
	 * Currently uses Swing (to be changed)
	 */
	public View(Controller controller) {
		super("Detail View");
		this.controller = controller;
		this.campaign = this.controller.getCampaign();
		this.campaign.addObserver(this);
		updateData();
		setSize(new Dimension(200, 170));

		// Overall pane
		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(pane);
		setVisible(true);
	}

	/**
	 * Requests data from a campaign and updates GUI accordingly
	 */
	private void updateData() {
		repaint();
	}

	@Override
	public void observableChanged(Observable observable) {
		updateData();
	}
}