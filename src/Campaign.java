

//import com.sun.security.ntlm.Server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
	private float totalImpCost;
	private int totalClicks;
	private float totalClickCost;
	private float totalCost;
	private int totalConversions;
	private float conversionRate;
	private int bounces;
	private float bounceRate;
	private int totalUniques;
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

				float clickCost = Float.parseFloat(clickValues[2]);

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
		totalImpCost = 0;
		for (Impression imp: impressionArray) {
			totalImpCost += imp.getImpressionCost();
		}
	}

	private void calcClicks(ArrayList<Click> clickArray){
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
			if (serverEntry.getConversion().equals("Yes")){
				totalConversions += 1;
			}
		}
	}

	private void calcConvRate(ArrayList<ServerEntry> serverEntryArray, ArrayList<Click> clickArray){
		totalConversions = 0;
		totalClicks = 0;
		totalClicks = clickArray.size();
		for (ServerEntry serverEntry: serverEntryArray) {
			if (serverEntry.getConversion().equals("Yes")) {
				totalConversions += 1;
			}
		}
		conversionRate = (float) totalConversions / totalClicks;
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
		bounceRate = (float) bounces / totalClicks;// change to proper divide function
	}

	private void calcUniques(ArrayList<Click> clickArray){
		HashSet<String> uniquesHashset = new HashSet<String>();
		for (Click click: clickArray) {
			uniquesHashset.add(click.getID());
		}
		totalUniques = uniquesHashset.size();
	}

	private void calcCTR(ArrayList<Click> clickArray, ArrayList<Impression> impressionArray){
		CTR = (float) clickArray.size() / impressionArray.size();
	}

	private void calcCPA(ArrayList<Impression> impressionArray, ArrayList<Click> clickArray, ArrayList<ServerEntry> serverEntryArray){
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
			if (serverEntry.getConversion().equals("Yes")){
				totalConversions += 1;
			}
		}
		CPA = totalCost / totalConversions;
	}

	private void calcCPC(ArrayList<Click> clickArray){
		totalClickCost = 0;
		for (Click click: clickArray) {
			totalClickCost += click.getClickCost();
		}
		CPC = (float) totalClickCost / clickArray.size();
	}

	private void calcCPM(ArrayList<Impression> impressionArray){
		totalImpCost = 0;
		for (Impression imp: impressionArray) {
			totalImpCost += imp.getImpressionCost();
		}
		CPM = (float) totalImpCost / impressionArray.size() / 1000;
	}
	
	public void calculateMetrics() {
		calcCPM(impressions);
		calcCPC(clicks);
		calcCPA(impressions, clicks, serverEntries);
		calcCTR(clicks, impressions);
		calcBounceRate(serverEntries, clicks);
		calcBounces(serverEntries);
		calcClicks(clicks);
		calcConversions(serverEntries);
		calcConvRate(serverEntries, clicks);
		calcImpressions(impressions);
		calcTotalClickCost(clicks);
		calcTotalCost(impressions, clicks);
		calcTotalImpCost(impressions);
		calcUniques(clicks);
	}
	
	public int getTotalImpressions() {
		return totalImpressions;
	}
	
	public float getTotalImpressionCost() {
		return totalImpCost;
	}
	
	public float getTotalClicks() {
		return totalClicks;
	}

	public float getTotalClickCost() {
		return totalClickCost;
	}
	
	public float getTotalConversions() {
		return totalConversions;
	}
	
	public float getTotalBounces() {
		return bounces;
	}
	
	public float getBounceRate() {
		return bounceRate;
	}
	
	public float getTotalUnique() {
		return totalUniques;
	}
	
	public float getCTR() {
		return CTR;
	}
	
	public float getCPA() {
		return CPA;
	}
	
	public float getCPC() {
		return CPC;
	}
	
	public float getCPM() {
		return CPM;
	}

	public float getConversionRate() {
		return conversionRate;
	}
	
	public ArrayList<Impression> getImpressions() {
		System.out.println(this.impressions.size());
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


}
