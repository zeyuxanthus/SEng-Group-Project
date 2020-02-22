import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *  Domain of the application. (Basically backend)
 *  Model of MVC
 *  Should be independent of the View (Observer) and the Controller.
 */
public class Campaign implements Observable {

	private List<Observer> observers = new LinkedList<Observer>();

	// Log information
	private ArrayList<Impression> impressions; // Impression Log
	private ArrayList<Click> clicks; // Click Log
	private ArrayList<ServerEntry> serverEntries; // Server Log

	/**
	 * Should be called whenever anything in the model changes.
	 * It updates the all the observers, e.g. View
	 */
	private void triggerUpdate() {
		for (Observer observer : observers) {
			observer.observableChanged(this);
		}
	}

	public void addObserver(Observer observer) {
		observers.add(observer);
	}
}