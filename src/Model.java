import java.util.LinkedList;
import java.util.List;

/**
 *  Domain of the application. (Basically backend)
 *  Should be independent of the View (Observer) and the Controller.
 */
public class Model implements Observable {

	private List<Observer> observers = new LinkedList<Observer>();

	/* Should be called whenever anything in the model changes.
	   It updates the all the observers, e.g. View
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