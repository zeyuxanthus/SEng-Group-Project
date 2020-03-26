import java.io.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *  Contains data about single campaign
 *  Handles all data manipulation
 */
public class Campaign implements Serializable {
	private static final long serialVersionUID = 4518483514452733851L;

	// Log information
	private ArrayList<Impression> impressions; // Impression Log
	private ArrayList<Click> clicks; // Click Log
	private ArrayList<ServerEntry> serverEntries; // Server Log
	private HashMap<String, Impression> impressionSet;

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

	private int bounceDefinition = 1;

	public Campaign(){

	}
	//--LOADING---------------------------------------------------------------------------------------------------------
	/**
	 * Loads all the data from CSV files into arrayLists and calculates the metrics
	 * @param bounceDefinition - how bounces are registered
	 */
	public Campaign(String serverFilePath, String clickFilePath, String impressionFilePath, int bounceDefinition){
		this.bounceDefinition = bounceDefinition;
		loadImpressionLog(impressionFilePath);
		loadSeverlog(serverFilePath);
		loadClickLog(clickFilePath);
		calculateMetrics(clicks, impressions, serverEntries);
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

	//--CALCULATIONS----------------------------------------------------------------------------------------------------
	/**
	 * Calculates and updates all metrics given lists of campaign's data
	 */
	public void calculateMetrics(ArrayList<Click> clickList, ArrayList<Impression> impList, ArrayList<ServerEntry> serverEntries) {
		totalImpressions = calcImpressions(impList);
		totalImpCost = calcTotalImpCost(impList);
		totalClicks = calcClicks(clickList);
		totalClickCost = calcTotalClickCost(clickList);
		totalCost = calcTotalCost(impList, clickList);
		totalConversions = calcConversions(serverEntries);
		conversionRate = calcConvRate(serverEntries, clickList);
		bounces = calcBounces(serverEntries);
		bounceRate = calcBounceRate(serverEntries, clickList);
		totalUniques = calcUniques(clickList);
		CTR = calcCTR(clickList, impList);
		CPA = calcCPA(impList, clickList, serverEntries);
		CPC = calcCPC(clickList);
		CPM = calcCPM(impList);
	}


	/**
	 * These calculations are directly for the filter methods to use when they have made their own data sets and arraylists
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
		double conversionRate =   (double) totalConversions / totalClicks;
		return  conversionRate;
	}

	public int calcBounces(ArrayList<ServerEntry> serverEntryArray){

		int myBounces = 0;
		for (ServerEntry serverEntry: serverEntryArray) {
			if (serverEntry.getPagesViewed() <= bounceDefinition){
				myBounces += 1;
			}
		}
		return myBounces;
	}

	public double calcBounceRate(ArrayList<ServerEntry> serverEntryArray, ArrayList<Click> clickArray){
		int bounces = 0;

		for (ServerEntry serverEntry: serverEntryArray) {
			if (serverEntry.getPagesViewed() <= bounceDefinition){
				bounces += 1;
			}
		}
		int totalClicks = clickArray.size();
		double bounceRate = (double) bounces / totalClicks;
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
		return  CTR;
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
		double CPM =   ((double) totalImpCost / impressionArray.size()) * 1000;
		return CPM;
	}

	//--FILTERS---------------------------------------------------------------------------------------------------------

	public ArrayList<Impression> filterImpressionLog(Filter filter){
		//for testing
		//long startTime = System.nanoTime();

		// Predicate for 0 costs
		//TODO discuss if its needed
		//Predicate<Click> clickCostPredicate = c -> c.getClickCost() > 0;

		// Predicate for startDate
		Predicate<Impression> startDatePredicate;
		if(filter.getStartDate() != null){
			startDatePredicate = c -> c.getDateTime().isAfter(filter.getStartDate()); // TODO Discuss if it needs to be inclusive
		}
		else{
			System.out.println("startDate is null");
			startDatePredicate = c -> true;
		}

		// Predicate for endDate
		Predicate<Impression> endDatePredicate;
		if(filter.getEndDate() != null){
			endDatePredicate = c -> c.getDateTime().isBefore(filter.getEndDate());
		}
		else{
			System.out.println("endDate is null");
			endDatePredicate = c -> true;
		}

		// Predicates for contexts
		Predicate<Impression> contextsPredicate;
		if(!filter.getContexts().isEmpty()){
			contextsPredicate = c -> matchContext(c.getContext(), filter);
		}
		else{
			System.out.println("contexts are empty");
			contextsPredicate = c -> true;
		}

		// Predicate for gender
		Predicate<Impression> genderPredicate;
		if(filter.getGender() != null){
			genderPredicate = c -> c.getGender().equals(filter.getGender());
		}
		else{
			System.out.println("gender is null");
			genderPredicate = c -> true;
		}

		// Predicates for ageGroups
		Predicate<Impression> ageGroupPredicate;
		if(!filter.getAgeGroups().isEmpty()){
			ageGroupPredicate = c -> matchAgeGroup(c.getAgeGroup(), filter);
		}
		else{
			System.out.println("ageGroup is empty");
			ageGroupPredicate = c -> true;
		}

		// Predicate for incomes
		Predicate<Impression> incomePredicate;
		if(!filter.getIncomes().isEmpty()){
			incomePredicate = c -> matchIncome(c.getIncome(), filter);
		}
		else{
			System.out.println("income is empty");
			incomePredicate = c -> true;
		}

		ArrayList<Impression> filteredImpressions = (ArrayList<Impression>) impressions.stream().filter
				(startDatePredicate.and(endDatePredicate.and(contextsPredicate).and(genderPredicate).and(ageGroupPredicate).and(incomePredicate))).collect(Collectors.toList());

		//for testing
		//long endTime = System.nanoTime();
		//System.out.println("Method took:" + (endTime - startTime) / 1000000);

		return filteredImpressions;
	}

	public ArrayList<ServerEntry> filterServerLog(Filter filter){
		//for testing
		//long startTime = System.nanoTime();

		// Predicate for 0 costs
		//TODO discuss if its needed
		//Predicate<Click> clickCostPredicate = c -> c.getClickCost() > 0;

		// Predicate for startDate
		Predicate<ServerEntry> startDatePredicate;
		if(filter.getStartDate() != null){
			startDatePredicate = c -> c.getEntryDate().isAfter(filter.getStartDate()); // TODO Discuss if it needs to be inclusive
		}
		else{
			System.out.println("startDate is null");
			startDatePredicate = c -> true;
		}

		// Predicate for endDate
		Predicate<ServerEntry> endDatePredicate;
		if(filter.getEndDate() != null){
			endDatePredicate = c -> c.getEntryDate().isBefore(filter.getEndDate());
		}
		else{
			System.out.println("endDate is null");
			endDatePredicate = c -> true;
		}

		// Predicates for contexts
		Predicate<ServerEntry> contextsPredicate;
		if(!filter.getContexts().isEmpty()){
			contextsPredicate = c -> matchContext(c.getContext(), filter);
		}
		else{
			System.out.println("contexts are empty");
			contextsPredicate = c -> true;
		}

		// Predicate for gender
		Predicate<ServerEntry> genderPredicate;
		if(filter.getGender() != null){
			genderPredicate = c -> c.getGender().equals(filter.getGender());
		}
		else{
			System.out.println("gender is null");
			genderPredicate = c -> true;
		}

		// Predicates for ageGroups
		Predicate<ServerEntry> ageGroupPredicate;
		if(!filter.getAgeGroups().isEmpty()){
			ageGroupPredicate = c -> matchAgeGroup(c.getAgeGroup(), filter);
		}
		else{
			System.out.println("ageGroup is empty");
			ageGroupPredicate = c -> true;
		}

		// Predicate for incomes
		Predicate<ServerEntry> incomePredicate;
		if(!filter.getIncomes().isEmpty()){
			incomePredicate = c -> matchIncome(c.getIncome(), filter);
		}
		else{
			System.out.println("income is empty");
			incomePredicate = c -> true;
		}

		ArrayList<ServerEntry> filteredServerEntries = (ArrayList<ServerEntry>) serverEntries.stream().filter
				(startDatePredicate.and(endDatePredicate.and(contextsPredicate).and(genderPredicate).and(ageGroupPredicate).and(incomePredicate))).collect(Collectors.toList());

		//for testing
		//long endTime = System.nanoTime();
		//System.out.println("Method took:" + (endTime - startTime) / 1000000);

		return filteredServerEntries;
	}

	public ArrayList<Click> filterClickLog(Filter filter){
		//for testing
		//long startTime = System.nanoTime();

		// Predicate for 0 costs
		//TODO discuss if its needed
		//Predicate<Click> clickCostPredicate = c -> c.getClickCost() > 0;

		// Predicate for startDate
		Predicate<Click> startDatePredicate;
		if(filter.getStartDate() != null){
			startDatePredicate = c -> c.getDateTime().isAfter(filter.getStartDate()); // TODO Discuss if it needs to be inclusive
		}
		else{
			System.out.println("startDate is null");
			startDatePredicate = c -> true;
		}

		// Predicate for endDate
		Predicate<Click> endDatePredicate;
		if(filter.getEndDate() != null){
			endDatePredicate = c -> c.getDateTime().isBefore(filter.getEndDate());
		}
		else{
			System.out.println("endDate is null");
			endDatePredicate = c -> true;
		}

		// Predicates for contexts
		Predicate<Click> contextsPredicate;
		if(!filter.getContexts().isEmpty()){
			contextsPredicate = c -> matchContext(c.getContext(), filter);
		}
		else{
			System.out.println("contexts are empty");
			contextsPredicate = c -> true;
		}

		// Predicate for gender
		Predicate<Click> genderPredicate;
		if(filter.getGender() != null){
			genderPredicate = c -> c.getGender().equals(filter.getGender());
		}
		else{
			System.out.println("gender is null");
			genderPredicate = c -> true;
		}

		// Predicates for ageGroups
		Predicate<Click> ageGroupPredicate;
		if(!filter.getAgeGroups().isEmpty()){
			ageGroupPredicate = c -> matchAgeGroup(c.getAgeGroup(), filter);
		}
		else{
			System.out.println("ageGroup is empty");
			ageGroupPredicate = c -> true;
		}

		// Predicate for incomes
		Predicate<Click> incomePredicate;
		if(!filter.getIncomes().isEmpty()){
			incomePredicate = c -> matchIncome(c.getIncome(), filter);
		}
		else{
			System.out.println("income is empty");
			incomePredicate = c -> true;
		}

		ArrayList<Click> filteredClicks = (ArrayList<Click>) clicks.stream().filter
				(startDatePredicate.and(endDatePredicate.and(contextsPredicate).and(genderPredicate).and(ageGroupPredicate).and(incomePredicate))).collect(Collectors.toList());

		//for testing
		//long endTime = System.nanoTime();
		//System.out.println("Method took:" + (endTime - startTime) / 1000000);

		return filteredClicks;
	}

	/**
	 * Checks if there exists a context in a list of contexts (filters) that matches given context
	 * @param context - given context
	 * @return - true if there is a match
	 */
	private boolean matchContext(String context, Filter filter){
		for(String c : filter.getContexts()){
			if(c.equals(context))
				return true;
		}
		return false;
	}

	/**
	 * Checks if there exists an ageGroup in a list of ageGroups that matches given ageGroup
	 * @param ageGroup - given ageGroup
	 * @return - true if there is a match
	 */
	private boolean matchAgeGroup(String ageGroup, Filter filter){
		for(String a : filter.getAgeGroups()){
			if(a.equals(ageGroup))
				return true;
		}
		return false;
	}

	/**
	 * Checks if there exists an income in a list of incomes that matches given income
	 * @param income - given income
	 * @return - true if there is a match
	 */
	private boolean matchIncome(String income, Filter filter){
		for(String i : filter.getIncomes()){
			if(i.equals(income))
				return true;
		}
		return false;
	}

	//--GETTERS---------------------------------------------------------------------------------------------------------

	public int getTotalImpressions() {
		return totalImpressions;
	}

	public double getTotalImpressionCost() {
		return totalImpCost;
	}

	public int getTotalClicks() {
		return totalClicks;
	}

	public double getTotalClickCost() {
		return totalClickCost;
	}

	public int getTotalConversions() {
		return totalConversions;
	}

	public int getTotalBounces() {
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

	public double getTotalCost() {
		return totalCost;
	}

	//--SETTERS---------------------------------------------------------------------------------------------------------

	public void setBounceDefinition(int bounceDefinition){
		this.bounceDefinition = bounceDefinition;
	}
}
