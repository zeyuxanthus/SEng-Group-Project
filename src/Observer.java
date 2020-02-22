/**
 * Receives notifications of Observable (Campaign)
 */
@FunctionalInterface
public interface Observer {
	public void observableChanged(Observable observable);
}