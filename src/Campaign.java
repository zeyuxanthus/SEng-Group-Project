
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 *  Domain of the application. (Basically backend)
 *  Model of MVC
 *  Should be independent of the View (Observer) and the Controller.
 */
public class Campaign implements Observable {

	private float totalImpressions;
	private float totalImpCost;
	private float totalClicks;
	private float totalClickCost;
	private float totalCost;
	private float totalConversions;
	private float conversionRate;
	private float bounces;
	private float bounceRate;
	private float totalUniques; // not sure how to calculate uniques
	private HashSet<String> uniquesHashset = new HashSet<String>();
	private float CTR; // click-through-rate
	private float CPA; // cost-per-acquisition
	private float CPC; // cost-per-click
	private float CPM; // cost-per-thousand impressions

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

	private void calcImpressions(ArrayList<Impression> impressionArray){
		totalImpressions = 0;
		totalImpressions = impressionArray.size();
	}

	private void calcTotalImpCost(ArrayList<Impression> impressionArray){
		totalImpCost = 0;
		for (Impression imp: impressionArray) {
			totalImpCost += imp.getImpressionCost();
		}
	}

	private void calcClicks(ArrayList<Click> clickArray){
		totalClicks = 0;
		totalClicks = clickArray.size();
	}

	private void calcTotalClickCost(ArrayList<Click> clickArray){
		totalClickCost = 0;
		for (Click click: clickArray) {
			totalClickCost += click.getClickCost();
		}
	}

	private void calcTotalCost(ArrayList<Impression> impressionArray, ArrayList<Click> clickArray){
		totalClickCost = 0;
		totalImpCost = 0;
		totalCost = 0;
		for (Impression imp: impressionArray) {
			totalImpCost += imp.getImpressionCost();
		}

		for (Click click: clickArray) {
			totalClickCost += click.getClickCost();
		}

		totalCost = totalClickCost + totalImpCost;
	}

	private void calcConversions(ArrayList<ServerEntry> serverEntryArray){
		totalConversions = 0;
		for (ServerEntry serverEntry: serverEntryArray) {
			if (serverEntry.getConversion().toString() == "YES"){
				totalConversions += 1;
			}
		}
	}

	private void calcConvRate(ArrayList<ServerEntry> serverEntryArray, ArrayList<Click> clickArray){
		totalConversions = 0;
		totalClicks = 0;
		totalClicks = clickArray.size();
		for (ServerEntry serverEntry: serverEntryArray) {
			if (serverEntry.getConversion().toString() == "YES") {
				totalConversions += 1;
			}
		}
		conversionRate = totalConversions / totalClicks;
	}

	private void calcBounces(ArrayList<ServerEntry> serverEntryArray){
		bounces = 0;
		for (ServerEntry serverEntry: serverEntryArray) {
			if (serverEntry.getPagesViewed() <= 1){
				bounces += 1;
			}
		}
	}

	private void calcBounceRate(ArrayList<ServerEntry> serverEntryArray, ArrayList<Click> clickArray){
		bounces = 0;
		bounceRate = 0;
		for (ServerEntry serverEntry: serverEntryArray) {
			if (serverEntry.getPagesViewed() <= 1){
				bounces += 1;
			}
		}
		totalClicks = clickArray.size();
		bounceRate = bounces / totalClicks;// change to proper divide function
	}

	private void calcUniques(ArrayList<Click> clickArray){
		totalUniques = 0;
		uniquesHashset.clear();
		for (Click click: clickArray) {
			uniquesHashset.add(click.getID());
		}
		totalUniques = uniquesHashset.size();
	}

	private void calcCTR(ArrayList<Click> clickArray, ArrayList<Impression> impressionArray){
		CTR = 0;
		CTR = clickArray.size() / impressionArray.size();
	}

	private void calcCPA(ArrayList<Impression> impressionArray, ArrayList<Click> clickArray, ArrayList<ServerEntry> serverEntryArray){
		CPA = 0;
		totalImpCost = 0;
		totalClickCost = 0;
		totalConversions = 0;
		totalCost = 0;
		for (Impression imp: impressionArray) {
			totalImpCost += imp.getImpressionCost();
		}

		for (Click click: clickArray) {
			totalClickCost += click.getClickCost();
		}

		totalCost = totalClickCost + totalImpCost;

		for (ServerEntry serverEntry: serverEntryArray) {
			if (serverEntry.getConversion().toString() == "YES"){
				totalConversions += 1;
			}
		}
		CPA = totalCost / totalConversions;
	}

	private void calcCPC(ArrayList<Click> clickArray){
		totalClickCost = 0;
		CPC = 0;
		for (Click click: clickArray) {
			totalClickCost += click.getClickCost();
		}
		CPC = totalClickCost / clickArray.size();
	}

	private void calcCPM(ArrayList<Impression> impressionArray){
		totalImpCost = 0;
		CPM = 0;
		for (Impression imp: impressionArray) {
			totalImpCost += imp.getImpressionCost();
		}
		CPM = (totalImpCost / impressionArray.size())*1000;
	}
	
	public Float getTotalImpressions() {
		return totalImpressions;
	}
	
	public Float getTotalImpressionCost() {
		return totalImpCost;
	}
	
	public Float getTotalClicks() {
		return totalClicks;
	}
	public Float getTotalClickCost() {
		return totalClickCost;
	}
	
	public Float getTotalConversions() {
		return totalConversions;
	}
	
	public Float getTotalBounces() {
		return bounces;
	}
	
	public Float getBounceRate() {
		return bounceRate;
	}
	
	public Float getTotalUnique() {
		return totalUniques;
	}
	
	public Float getCTR() {
		return CTR;
	}
	
	public Float getCPA() {
		return CPA;
	}
	
	public Float getCPC() {
		return CPC;
	}
	
	public Float getCPM() {
		return CPM;
	}
	
	public ArrayList<Impression> getImpressions() {
		return impressions;
	}
	
	public ArrayList<ServerEntry> getServerEntries() {
		return serverEntries;
	}
	
	public ArrayList<Click> getClicks(){
		return clicks;
	}
	
	public Float getConversionRate() {
		return conversionRate;
	}
	
}
