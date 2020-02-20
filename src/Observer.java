/*
Receives notifications of Observable (Model)
 */
@FunctionalInterface
public interface Observer {
	public void observableChanged(Observable observable);
}