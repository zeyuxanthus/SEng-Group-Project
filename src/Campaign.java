

//import com.sun.security.ntlm.Server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 *  Domain of the application. (Basically backend)
 *  Model of MVC
 *  Should be independent of the View (Observer) and the Controller.
 */
public class Campaign {

	// Campaign's metrics
	private int totalImpressions;
	private double totalImpCost;
	private int totalClicks;
	private double totalClickCost;
	private double totalCost;
	private int totalConversions;
	private double conversionRate;
	private int bounces;
	private double bounceRate;
	private int totalUniques;
	private double CTR; // click-through-rate
	private double CPA; // cost-per-acquisition
	private double CPC; // cost-per-click
	private double CPM; // cost-per-thousand impressions

	private List<Observer> observers = new LinkedList<Observer>();

	// Log information
	private ArrayList<Impression> impressions; // Impression Log
	private ArrayList<Click> clicks; // Click Log
	private ArrayList<ServerEntry> serverEntries; // Server Log

	/**
	 * Should be called whenever anything in the model changes.
	 * It updates the all the observers, e.g. View
	 */
//	private void triggerUpdate() {
//		for (Observer observer : observers) {
//			observer.observableChanged(this);
//		}
//	}
//
//	public void addObserver(Observer observer) {
//		observers.add(observer);
//	}

	/**
	 * @TODO for other loaders:
	 * 	1. create local ArrayList to override previous document
	 * 	2. remove replaceAll()
	 * 	3. change the pattern of the formatter (to account for space)
	 * 	4. Campaign's ArrayLists don't need to be initialised
	 * @param clickFileName - path to the file + its name
	 */
	public void loadClickLog (String clickFileName){
		ArrayList<Click> clicks = new ArrayList<Click>();
		String clickLog = clickFileName;
		File clickLogFile = new File(clickLog);
		String clickLine = "";
		try {
			Scanner inputStream = new Scanner(clickLogFile);
			//To remove the first clickLine (headings)
			inputStream.nextLine();

			while (inputStream.hasNext()){
				clickLine = inputStream.nextLine();
				//seperating colums based on comma
				String[] clickValues = clickLine.split(",");

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				LocalDateTime formatDateTime = LocalDateTime.parse(clickValues[0],formatter);

				String id = clickValues[1];

				Float clickCost = Float.parseFloat(clickValues[2]);

				clicks.add(new Click(formatDateTime,id,clickCost));
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.clicks = clicks;
	}
	
	public void loadImpressionLog (String impressionFileName){
		ArrayList<Impression> impressions = new ArrayList<Impression>();
		String impressionLog = impressionFileName;
		File impressionLogFile = new File(impressionLog);
		String impressionLine = "";

		//Catch block to check if the impressionLogFile is there
		try {
			Scanner inputStream = new Scanner(impressionLogFile);
			//To remove the first impressionLine (headings)
			inputStream.nextLine();

			while (inputStream.hasNext()) {
				impressionLine = inputStream.nextLine();

				//seperating columns based on comma
				String[] impressionValues = impressionLine.split(",");

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

				LocalDateTime dateTime = LocalDateTime.parse(impressionValues[0],formatter);
				String id = impressionValues[1];
				String gender = impressionValues[2];
				String ageGroup = impressionValues[3];
				String income = impressionValues[4];
				String context = impressionValues[5];
				Float impressionCost = Float.parseFloat(impressionValues[6]);

				impressions.add(new Impression(dateTime,id,gender,ageGroup,income,context,impressionCost));
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.impressions = impressions;
		//System.out.println(this.impressions.size()); //printout entire log

	}
	
	public void loadSeverlog (String serverFileName){
		ArrayList<ServerEntry> serverEntries = new ArrayList<ServerEntry>();
		File serverLogFile = new File(serverFileName);
		String serverLine = "";

		//Catch block to check if the serverLogFile is there
		try {
			Scanner inputStream = new Scanner(serverLogFile);
			//To remove the first serverLine (headings)
			inputStream.nextLine();

			while (inputStream.hasNext()) {
				try{
					serverLine = inputStream.nextLine();
					//seperating columns based on comma
					String[] serverValues = serverLine.split(",");

					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					LocalDateTime entryDate = LocalDateTime.parse(serverValues[0], formatter);

					String id = serverValues[1];

					LocalDateTime exitDate;
					if(serverValues[2].equals("n/a")){
						exitDate = null;
					}
					else exitDate = LocalDateTime.parse(serverValues[2],formatter);

					int pagesViewed = Integer.parseInt(serverValues[3]);

					String conversion = serverValues[4];


					serverEntries.add(new ServerEntry(entryDate,id,exitDate,pagesViewed,conversion));
				} catch(NullPointerException e) {
					e.printStackTrace();
				}
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.serverEntries = serverEntries;
		//System.out.println(serverEntries); // entire log
	}

	private void calcImpressions(ArrayList<Impression> impressionArray){
		totalImpressions = impressionArray.size();
	}

	private void calcTotalImpCost(ArrayList<Impression> impressionArray){		
		totalImpCost = (float) impressionArray.stream().mapToDouble(x -> x.getImpressionCost()).sum();
	}

	private void calcClicks(ArrayList<Click> clickArray){
		totalClicks = clickArray.size();
	}

	private void calcTotalClickCost(ArrayList<Click> clickArray){
		totalClickCost = (float) clickArray.stream().mapToDouble(x -> x.getClickCost()).sum();
	}

	private void calcTotalCost(ArrayList<Impression> impressionArray, ArrayList<Click> clickArray){
		totalCost = totalClickCost + totalImpCost;
	}

	private void calcConversions(ArrayList<ServerEntry> serverEntryArray){		
		totalConversions = (int) serverEntryArray.stream().filter(x -> x.getConversion().equals("Yes")).count();
	}

	private void calcConvRate(ArrayList<ServerEntry> serverEntryArray, ArrayList<Click> clickArray){
		conversionRate = (float) totalConversions / totalClicks;
	}

	private void calcBounces(ArrayList<ServerEntry> serverEntryArray){
		bounces = (int) serverEntryArray.stream().filter(x -> x.getPagesViewed() <= 1).count();
	}

	private void calcBounceRate(ArrayList<ServerEntry> serverEntryArray, ArrayList<Click> clickArray){		
		bounceRate = (float) bounces / totalClicks;// change to proper divide function
	}

	private void calcUniques(ArrayList<Click> clickArray){
		totalUniques = (int) clickArray.stream().map(x -> x.getID()).distinct().count();
	}

	private void calcCTR(ArrayList<Click> clickArray, ArrayList<Impression> impressionArray){
		CTR = (float) clickArray.size() / impressionArray.size();
	}

	private void calcCPA(ArrayList<Impression> impressionArray, ArrayList<Click> clickArray, ArrayList<ServerEntry> serverEntryArray){
		CPA = totalCost / totalConversions;
	}

	private void calcCPC(ArrayList<Click> clickArray){
		CPC = (float) totalClickCost / clickArray.size();
	}

	private void calcCPM(ArrayList<Impression> impressionArray){
		CPM = (float) totalImpCost / impressionArray.size() * 1000;
	}
	
	public void calculateMetrics() {
		calcUniques(clicks);
		calcTotalImpCost(impressions);
		calcTotalClickCost(clicks);
		calcClicks(clicks);
		calcConversions(serverEntries);
		calcImpressions(impressions);
		calcBounces(serverEntries);
		calcConvRate(serverEntries, clicks);
		calcTotalCost(impressions, clicks);
		calcBounceRate(serverEntries, clicks);
		calcCPM(impressions);
		calcCPC(clicks);
		calcCPA(impressions, clicks, serverEntries);
		calcCTR(clicks, impressions);
		
	}
	
	public int getTotalImpressions() {
		return totalImpressions;
	}

	public double getTotalImpressionCost() {
		return totalImpCost;
	}

	public double getTotalClicks() {
		return totalClicks;
	}

	public double getTotalClickCost() {
		return totalClickCost;
	}

	public double getTotalConversions() {
		return totalConversions;
	}

	public double getTotalBounces() {
		return bounces;
	}

	public double getBounceRate() {
		return bounceRate;
	}

	public int getTotalUnique() {
		return totalUniques;
	}

	public double getCTR() {
		return CTR;
	}

	public double getCPA() {
		return CPA;
	}

	public double getCPC() {
		return CPC;
	}

	public double getCPM() {
		return CPM;
	}

	public double getConversionRate() {
		return conversionRate;
	}

	public ArrayList<Impression> getImpressions() {
		return this.impressions;
	}
	
	public ArrayList<ServerEntry> getServerEntries() {
		return this.serverEntries;
	}
	
	public ArrayList<Click> getClicks(){
		return this.clicks;
	}
	
	public String testIsConnected() {
		return ("connected");
	}
	
	public ArrayList filterArray(ArrayList array, Predicate predicate){
		Stream filtered = array.stream().filter(predicate);
		List list = (List) filtered.collect(Collectors.toList());
		ArrayList filt = new ArrayList(list);
		return array;
	}
	
}
