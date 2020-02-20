/*
Maintains a list of Observers and notifies them of any changes.
 */
@FunctionalInterface
public interface Observable {
	public void addObserver(Observer observer);
}