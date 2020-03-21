


import java.io.*;

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
	private HashMap<String, Impression> impressionSet;


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





	public void loadLogs(String serverName, String clickName, String impressionName){

		loadImpressionLog(impressionName);
		loadSeverlog(serverName);
		loadClickLog(clickName);

	}


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

		long startTime = System.nanoTime();
		for(Click c : clicks){

			String ageGroup = impressionSet.get(c.getID()).getAgeGroup();
			String context = impressionSet.get(c.getID()).getContext();
			String income = impressionSet.get(c.getID()).getIncome();
			String gender = impressionSet.get(c.getID()).getGender();
			float impCost = impressionSet.get(c.getID()).getImpressionCost();

			c.setAgeGroup(ageGroup);
			c.setContext(context);
			c.setGender(gender);
			c.setIncome(income);
			c.setImpressionCost(impCost);

		}
		this.clicks = clicks;
		long endTime = System.nanoTime();
		System.out.println("Method took:" + (endTime - startTime) / 1000000);
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
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		this.impressions = impressions;
		impressionSet = new HashMap<>();
		for(Impression i: impressions){
			impressionSet.put(i.getID(), i);
		}


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
		for(ServerEntry s : serverEntries){
			String ageGroup = impressionSet.get(s.getID()).getAgeGroup();
			String context = impressionSet.get(s.getID()).getContext();
			String income = impressionSet.get(s.getID()).getIncome();
			String gender = impressionSet.get(s.getID()).getGender();
			float impCost = impressionSet.get(s.getID()).getImpressionCost();

			s.setAgeGroup(ageGroup);
			s.setContext(context);
			s.setGender(gender);
			s.setIncome(income);
			s.setImpressionCost(impCost);
		}


		this.serverEntries = serverEntries;

	}


	public void calculateMetrics(ArrayList<Click> clickList, ArrayList<Impression> impList, ArrayList<ServerEntry> serverEntries) {

		calcClicks(clickList);
		calcTotalImpCost(impList);
		calcImpressions(impList);
		calcBounces(serverEntries);
		calcUniques(clickList);
		calcConversions(serverEntries);

		calcTotalCost(impList, clickList);
		calcTotalClickCost(clickList);

		calcConvRate(serverEntries, clickList);
		calcBounceRate(serverEntries, clickList);

		calcCPM(impList);
		calcCPC(clickList);
		calcCPA(impList, clickList, serverEntries);
		calcCTR(clickList, impList);

	}


	/**
	 * These calculations are directly for the filter methods to use when they have made their own data sets and arraylists
	 * @return
	 */


	public int calcImpressions(ArrayList<Impression> impressionArray){

		int mytotalImpressions = impressionArray.size();
		return  mytotalImpressions;
	}

	public double calcTotalImpCost(ArrayList<Impression> impressionArray){

		double mytotalImpCost = 0;
		for (Impression imp: impressionArray) {
			mytotalImpCost += imp.getImpressionCost();
		}
		return (double) mytotalImpCost;
	}

	public int calcClicks(ArrayList<Click> clickArray){

		int mytotalClicks = clickArray.size();
		return mytotalClicks;

	}


	public double calcTotalClickCost(ArrayList<Click> clickArray){

		double mytotalClickCost = 0;
		for (Click click: clickArray) {
			mytotalClickCost += click.getClickCost();
		}
		return  mytotalClickCost;
	}


	public double calcTotalCost(ArrayList<Impression> impressionArray, ArrayList<Click> clickArray){

		double totalClickCost = 0;
		double totalImpCost = 0;
		for (Impression imp: impressionArray) {
			totalImpCost += imp.getImpressionCost();
		}
		for (Click click: clickArray) {
			totalClickCost += click.getClickCost();
		}


		return totalClickCost + totalImpCost;
	}

	public int calcConversions(ArrayList<ServerEntry> serverEntryArray){

		int mytotalConversions = 0;
		for (ServerEntry serverEntry: serverEntryArray) {
			if (serverEntry.getConversion().equals("Yes")){
				mytotalConversions += 1;
			}
		}
		return mytotalConversions;
	}

	public double calcConvRate(ArrayList<ServerEntry> serverEntryArray, ArrayList<Click> clickArray){

		int totalConversions = 0;
		int totalClicks = 0;
		totalClicks = clickArray.size();
		for (ServerEntry serverEntry: serverEntryArray) {
			if (serverEntry.getConversion().equals("Yes")) {
				totalConversions += 1;
			}
		}
		System.out.println(totalConversions);
		double conversionRate =   totalConversions / totalClicks;
		return  conversionRate;
	}

	public int calcBounces(ArrayList<ServerEntry> serverEntryArray){

		int myBounces = 0;
		for (ServerEntry serverEntry: serverEntryArray) {
			if (serverEntry.getPagesViewed() <= 1){
				myBounces += 1;
			}
		}
		return myBounces;
	}

	public double calcBounceRate(ArrayList<ServerEntry> serverEntryArray, ArrayList<Click> clickArray){

		int bounces = 0;

		for (ServerEntry serverEntry: serverEntryArray) {
			if (serverEntry.getPagesViewed() <= 1){
				bounces += 1;
			}
		}
		int totalClicks = clickArray.size();
		double bounceRate =  bounces / totalClicks;// change to proper divide function
		return bounceRate;
	}

	public int calcUniques(ArrayList<Click> clickArray){
		HashSet<String> uniquesHashset = new HashSet<String>();
		for (Click click: clickArray) {
			uniquesHashset.add(click.getID());
		}
		int totalUniques = uniquesHashset.size();
		return totalUniques;
	}

	public double calcCTR(ArrayList<Click> clickArray, ArrayList<Impression> impressionArray){
		double CTR =  ((double) clickArray.size() / impressionArray.size());
		return  clickArray.size() / impressionArray.size();
	}

	public double calcCPA(ArrayList<Impression> impressionArray, ArrayList<Click> clickArray, ArrayList<ServerEntry> serverEntryArray){
		double totalImpCost = 0;
		double totalClickCost = 0;
		int totalConversions = 0;
		double totalCost = 0;
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
		double CPA = totalCost / totalConversions;
		return CPA;
	}

	public double calcCPC(ArrayList<Click> clickArray){
		double totalClickCost = 0;
		for (Click click: clickArray) {
			totalClickCost += click.getClickCost();
		}
		double CPC = (totalClickCost / clickArray.size());
		return CPC;
	}

	public double calcCPM(ArrayList<Impression> impressionArray){
		double totalImpCost = 0;
		for (Impression imp: impressionArray) {
			totalImpCost += imp.getImpressionCost();
		}
		double CPM =  (totalImpCost / impressionArray.size()) * 1000;
		return CPM;
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
		return impressions;
	}

	public ArrayList<ServerEntry> getServerEntries() {
		return serverEntries;
	}

	public ArrayList<Click> getClicks(){
		return clicks;
	}

}
