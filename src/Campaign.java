

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

				try{
					float clickCost = Float.parseFloat(clickValues[2]);
					if(clickCost < 0 ){
						throw new IllegalArgumentException("Cost can't be negative");
					} else {
						clicks.add(new Click(formatDateTime,id,clickCost));
					}
				} catch (IllegalArgumentException e ){
					e.printStackTrace();
				}
			}
			inputStream.close();
			if(!clicks.isEmpty()){
				this.clicks = clicks;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
		} catch (NumberFormatException e){
			e.printStackTrace();
		}

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
				try{
					float impressionCost = Float.parseFloat(impressionValues[6]);
					if(impressionCost < 0){
						throw new IllegalArgumentException("Cost can't be negative");
					}else {
						impressions.add(new Impression(dateTime,id,gender,ageGroup,income,context,impressionCost));
					}
				} catch (IllegalArgumentException e){
					e.printStackTrace();
				}
			}
			inputStream.close();
			if(!impressions.isEmpty()){
				this.impressions = impressions; 
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
		}
		System.out.println(impressions); //printout entire log

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

					try{
						int pagesViewed = Integer.parseInt(serverValues[3]);
						if(pagesViewed < 0){
							throw new IllegalArgumentException("Pages views can't be less than 0");
						} else {
							String conversion = serverValues[4];
							serverEntries.add(new ServerEntry(entryDate,id,exitDate,pagesViewed,conversion));
						}
						
					}catch (IllegalArgumentException e){
						e.printStackTrace();
					}
					


				} catch(NullPointerException e) {
					e.printStackTrace();
				}
			}
			inputStream.close();
			if(!serverEntries.isEmpty()){
				this.serverEntries = serverEntries; 
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
		}
		System.out.println(serverEntries); // entire log
	}


	public int calcImpressions(ArrayList<Impression> impressionArray){

//		String sql = "SELECT COUNT() FROM impressions";
//		float totalImpressions = 0;
//		try(Connection conn = c;
//			Statement stmt = conn.createStatement();
//			ResultSet rs = stmt.executeQuery(sql)) {
//
//			while(rs.next())
//				totalImpressions = rs.getInt(1);
//		}catch(SQLException e ){
//			e.printStackTrace();
//		}
//
//		return (float) totalImpressions;

		float mytotalImpressions = impressionArray.size();
		return  mytotalImpressions;
	}

	public float calcTotalImpCost(ArrayList<Impression> impressionArray){


//		float impsum = 0;
//		String sql = "SELECT SUM(i.cost) FROM Impressions i";
//		try(Connection conn = c; Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)){
//			while(rs.next()){
//				impsum = rs.getInt(1);
//			}
//		}catch (SQLException e){
//			e.printStackTrace();
//		}
//
//		return impsum;


		float mytotalImpCost = 0;
		for (Impression imp: impressionArray) {
			mytotalImpCost += imp.getImpressionCost();
		}
		return mytotalImpCost;
	}

	public int calcClicks(ArrayList<Click> clickArray){
//
//		String sql = "SELECT COUNT() FROM clickLog";
//		int totalClicks = 0;
//		try(Connection conn = c;
//			Statement stmt = conn.createStatement();
//			ResultSet rs = stmt.executeQuery(sql)) {
//
//			while(rs.next())
//				totalClicks = rs.getInt(1);
//		}catch(SQLException e ){
//			e.printStackTrace();
//		}
//		System.out.println(totalClicks);
//		this.totalClicks = totalClicks;
//		return totalClicks;


		int mytotalClicks = clickArray.size();
		return mytotalClicks;
	}

	public float calcTotalClickCost(ArrayList<Click> clickArray){


		//		float clicksum = 0;
//		String sql = "SELECT SUM(c.cost) FROM clickLog c";
//		try(Connection conn = c; Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)){
//			while(rs.next()){
//				clicksum = rs.getInt(1);
//			}
//		}catch (SQLException e){
//			e.printStackTrace();
//		}
//
//		return impsum;
		float mytotalClickCost = 0;
		for (Click click: clickArray) {
			mytotalClickCost += click.getClickCost();
		}
		return mytotalClickCost;
	}

	public float calcTotalCost(ArrayList<Impression> impressionArray, ArrayList<Click> clickArray){
//		float clicksum = 0;
//		float impsum = 0;
//		String sql = "SELECT SUM(i.cost), SUM(c.cost) FROM Impressions i, clickLog c";
//		try(Connection conn = c; Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)){
//			while(rs.next()){
//				impsum = rs.getInt(1);
//				clicksum = rs.getInt(2);
//			}
//		}catch (SQLException e){
//			e.printStackTrace();
//		}
//
//		return impsum + clicksum;


		totalClickCost = 0;
		totalImpCost = 0;
		for (Impression imp: impressionArray) {
			totalImpCost += imp.getImpressionCost();
		}
		for (Click click: clickArray) {
			totalClickCost += click.getClickCost();
		}


		return totalClickCost + totalImpCost;
	}

	public int calcConversions(ArrayList<ServerEntry> serverEntryArray){

//		int totalConversions = 0;
////		String sql = "SELECT COUNT() FROM serverEntries WHERE conversions = 'Yes' ";
////		try(Connection conn = c; Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)){
////			while(rs.next()){
////				totalConversions = rs.getInt(1);
////			}
////		}catch (SQLException e){
////			e.printStackTrace();
////		}
////		return totalConversions;


		int mytotalConversions = 0;
		for (ServerEntry serverEntry: serverEntryArray) {
			if (serverEntry.getConversion().equals("Yes")){
				mytotalConversions += 1;
			}
		}
		return mytotalConversions;
	}

	public double calcConvRate(ArrayList<ServerEntry> serverEntryArray, ArrayList<Click> clickArray){

//		return calcConversions(serverEntryArray) / calcClicks(clickArray);

		totalConversions = 0;
		totalClicks = 0;
		totalClicks = clickArray.size();
		for (ServerEntry serverEntry: serverEntryArray) {
			if (serverEntry.getConversion().equals("Yes")) {
				totalConversions += 1;
			}
		}
		System.out.println(totalConversions);
		conversionRate =   totalConversions / totalClicks;
		return (double) totalConversions / totalClicks;
	}

	public int calcBounces(ArrayList<ServerEntry> serverEntryArray){
//		int bounces = 0;
//		String sql = "SELECT COUNT() FROM serverEntries s WHERE numPages = 0";
//		try(Connection conn = c; Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)){
//			while(rs.next()){
//				bounces = rs.getInt(1);
//			}
//		}catch (SQLException e){
//			e.printStackTrace();
//		}
//		return bounces;

		int myBounces = 0;
		for (ServerEntry serverEntry: serverEntryArray) {
			if (serverEntry.getPagesViewed() <= 1){
				myBounces += 1;
			}
		}
		return myBounces;
	}

	public double calcBounceRate(ArrayList<ServerEntry> serverEntryArray, ArrayList<Click> clickArray){

	//	return calcBounces(serverEntryArray)/calcClicks(clickArray);
		bounces = 0;
		bounceRate = 0;
		for (ServerEntry serverEntry: serverEntryArray) {
			if (serverEntry.getPagesViewed() <= 1){
				bounces += 1;
			}
		}
		totalClicks = clickArray.size();
		bounceRate =  bounces / totalClicks;// change to proper divide function
		return (double) bounces/ totalClicks;
	}

	public float calcUniques(ArrayList<Click> clickArray){
		HashSet<String> uniquesHashset = new HashSet<String>();
		for (Click click: clickArray) {
			uniquesHashset.add(click.getID());
		}
		totalUniques = uniquesHashset.size();
		return uniquesHashset.size();
	}

	public float calcCTR(ArrayList<Click> clickArray, ArrayList<Impression> impressionArray){
		CTR = (float) clickArray.size() / impressionArray.size();
		return (float) clickArray.size() / impressionArray.size();
	}

	public double calcCPA(ArrayList<Impression> impressionArray, ArrayList<Click> clickArray, ArrayList<ServerEntry> serverEntryArray){
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
		return (double) totalCost / totalConversions;
	}

	public double calcCPC(ArrayList<Click> clickArray){
		totalClickCost = 0;
		for (Click click: clickArray) {
			totalClickCost += click.getClickCost();
		}
		CPC = (float) totalClickCost / clickArray.size();
		return (double) totalClickCost / clickArray.size();
	}

	public double calcCPM(ArrayList<Impression> impressionArray){
		totalImpCost = 0;
		for (Impression imp: impressionArray) {
			totalImpCost += imp.getImpressionCost();
		}
		CPM = (float) (totalImpCost / impressionArray.size()) * 1000;
		return (double) (totalImpCost / impressionArray.size()) * 1000;
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
		return impressions;
	}

	public ArrayList<ServerEntry> getServerEntries() {
		return serverEntries;
	}

	public ArrayList<Click> getClicks(){
		return clicks;
	}
	
	public String testIsConnected() {
		return ("connected");
	}


}
