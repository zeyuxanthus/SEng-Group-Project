import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MyTests {


    ArrayList<ServerEntry> serverEntryArrayList9 = new ArrayList<>() {{

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime formatDateTime = LocalDateTime.parse("2019-12-12 12:30:30",formatter);
        ServerEntry entry1 = new ServerEntry(formatDateTime, "1", formatDateTime, 1, "Yes");
        ServerEntry entry2 = new ServerEntry(formatDateTime, "1", formatDateTime, 2, "Yes");
        ServerEntry entry3 = new ServerEntry(formatDateTime, "1", formatDateTime, 3, "Yes");
        ServerEntry entry4 = new ServerEntry(formatDateTime, "1", formatDateTime, 4, "Yes");
        ServerEntry entry5 = new ServerEntry(formatDateTime, "1", formatDateTime, 5, "Yes");
        ServerEntry entry6 = new ServerEntry(formatDateTime, "1", formatDateTime, 6, "Yes");
        ServerEntry entry7 = new ServerEntry(formatDateTime, "1", formatDateTime, 7, "Yes");
        ServerEntry entry8 = new ServerEntry(formatDateTime, "1", formatDateTime, 8, "No");
        ServerEntry entry9 = new ServerEntry(formatDateTime, "1", formatDateTime, 9, "Yes");
        ServerEntry entry10 = new ServerEntry(formatDateTime, "1", formatDateTime, 10, "Yes");


        add(entry1);
        add(entry2);
        add(entry3);
        add(entry4);
        add(entry5);
        add(entry6);
        add(entry7);
        add(entry8);
        add(entry9);
        add(entry10);

    }};

    ArrayList<ServerEntry> serverEntryArrayList2 = new ArrayList<>() {{

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime formatDateTime = LocalDateTime.parse("2019-12-12 12:30:30",formatter);
        ServerEntry entry1 = new ServerEntry(formatDateTime, "1", formatDateTime, 1, "No");
        ServerEntry entry2 = new ServerEntry(formatDateTime, "1", formatDateTime, 1, "No");
        ServerEntry entry3 = new ServerEntry(formatDateTime, "1", formatDateTime, 3, "No");
        ServerEntry entry4 = new ServerEntry(formatDateTime, "1", formatDateTime, 4, "No");
        ServerEntry entry5 = new ServerEntry(formatDateTime, "1", formatDateTime, 5, "Yes");
        ServerEntry entry6 = new ServerEntry(formatDateTime, "1", formatDateTime, 6, "No");
        ServerEntry entry7 = new ServerEntry(formatDateTime, "1", formatDateTime, 7, "Yes");
        ServerEntry entry8 = new ServerEntry(formatDateTime, "1", formatDateTime, 8, "No");
        ServerEntry entry9 = new ServerEntry(formatDateTime, "1", formatDateTime, 9, "No");
        ServerEntry entry10 = new ServerEntry(formatDateTime, "1", formatDateTime, 10, "No");


        add(entry1);
        add(entry2);
        add(entry3);
        add(entry4);
        add(entry5);
        add(entry6);
        add(entry7);
        add(entry8);
        add(entry9);
        add(entry10);

    }};

    ArrayList<Click> clicks = new ArrayList<>() {{

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime formatDateTime = LocalDateTime.parse("2019-12-12 12:30:30",formatter);
        Click click1 = new Click(formatDateTime, "1", 1);
        Click click2 = new Click(formatDateTime, "2", 2);
        Click click3 = new Click(formatDateTime, "3", 3);
        Click click4 = new Click(formatDateTime, "4", 4);
        Click click5 = new Click(formatDateTime, "5", 5);
        Click click6 = new Click(formatDateTime, "6", 6);
        Click click7 = new Click(formatDateTime, "7", 7);
        Click click8 = new Click(formatDateTime, "8", 8);
        Click click9 = new Click(formatDateTime, "9", 9);
        Click click10 = new Click(formatDateTime, "1", 10);
        add(click1);
        add(click2);
        add(click3);
        add(click4);
        add(click5);
        add(click6);
        add(click7);
        add(click8);
        add(click9);
        add(click10);
    }};


    ArrayList<Impression> impressions = new ArrayList<>(){{
        //load them with different data
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime formatDateTime = LocalDateTime.parse("2019-12-12 12:30:30",formatter);
        Impression imp1 = new Impression(formatDateTime, "1", "Male", "<25", "High", "Travel", 1);
        Impression imp2 = new Impression(formatDateTime, "1", "Male", "<25", "High", "Travel", 2);
        Impression imp3 = new Impression(formatDateTime, "1", "Male", "<25", "High", "Travel", 3);
        Impression imp4 = new Impression(formatDateTime, "1", "Male", "<25", "High", "Travel", 4);
        Impression imp5 = new Impression(formatDateTime, "1", "Male", "<25", "High", "Travel", 5);
        Impression imp6 = new Impression(formatDateTime, "1", "Male", "<25", "High", "Travel", 6);
        Impression imp7 = new Impression(formatDateTime, "1", "Male", "<25", "High", "Travel", 7);
        Impression imp8 = new Impression(formatDateTime, "1", "Male", "<25", "High", "Travel", 8);
        Impression imp9 = new Impression(formatDateTime, "1", "Male", "<25", "High", "Travel", 9);
        Impression imp10 = new Impression(formatDateTime, "1", "Male", "<25", "High", "Travel", 10);
        add(imp1);
        add(imp2);
        add(imp3);
        add(imp4);
        add(imp5);
        add(imp6);
        add(imp7);
        add(imp8);
        add(imp9);
        add(imp10);
    }};

    ArrayList<Impression> incorrectImpressionsCosts = new ArrayList<>(){{
        //load them with different data
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime formatDateTime = LocalDateTime.parse("2019-12-12 12:30:30",formatter);
        Impression imp1 = new Impression(formatDateTime, "1", "Male", "<25", "High", "Travel", 1);
        Impression imp2 = new Impression(formatDateTime, "1", "Male", "<25", "High", "Travel", -2);
        Impression imp3 = new Impression(formatDateTime, "1", "Male", "<25", "High", "Travel", -3);
        Impression imp4 = new Impression(formatDateTime, "1", "Male", "<25", "High", "Travel", 4);
        Impression imp5 = new Impression(formatDateTime, "1", "Male", "<25", "High", "Travel", 5);
        Impression imp6 = new Impression(formatDateTime, "1", "Male", "<25", "High", "Travel", 6);
        Impression imp7 = new Impression(formatDateTime, "1", "Male", "<25", "High", "Travel", 7);
        Impression imp8 = new Impression(formatDateTime, "1", "Male", "<25", "High", "Travel", 8);
        Impression imp9 = new Impression(formatDateTime, "1", "Male", "<25", "High", "Travel", 9);
        Impression imp10 = new Impression(formatDateTime, "1", "Male", "<25", "High", "Travel", 10);
        add(imp1);
        add(imp2);
        add(imp3);
        add(imp4);
        add(imp5);
        add(imp6);
        add(imp7);
        add(imp8);
        add(imp9);
        add(imp10);
    }};


    @Test
    public void loadData(){
        //TESTING csv file loading, will create my own csv file (small) and test whether or not the load function
        // returns the right amount of data
        // this will check whether or not the data loaded has the same number of impressions

        //create arrays which will contain the information of the csv files i create
        //create the csv files for ServerEntry, Clicks and Impressions


        Campaign myCampaign = new Campaign();

        String serverString = "/Users/danielraad/Desktop/UniCourses/SEG/Code/SEng-Group-Project/myServer.csv";
        String clickString = "/Users/danielraad/Desktop/UniCourses/SEG/Code/SEng-Group-Project/myClickLog.csv";
        String impressionString = "/Users/danielraad/Desktop/UniCourses/SEG/Code/SEng-Group-Project/myImpression.csv";

        myCampaign.loadClickLog(clickString);
        myCampaign.loadSeverlog(serverString);
        myCampaign.loadImpressionLog(impressionString);

        assertNotNull(myCampaign.getClicks());
        assertEquals(10, myCampaign.getClicks().size());
        assertNotNull(myCampaign.getImpressions());
        assertEquals(10, myCampaign.getImpressions().size());
        assertNotNull(myCampaign.getServerEntries());
        assertEquals(10, myCampaign.getServerEntries().size());


    }

    @Test
    public void notLoadData(){
        Campaign myCampaign = new Campaign();

        String serverString = "/Users/danielraad/Desktop/UniCourses/SEG/Code/SEng-Group-Project/myServer.csv";
        String clickString = "/Users/danielraad/Desktop/UniCourses/SEG/Code/SEng-Group-Project/myClickLog.csv";
        String impressionString = "/Users/danielraad/Desktop/UniCourses/SEG/Code/SEng-Group-Project/myImpression.csv";


        myCampaign.loadClickLog(serverString);
//        myCampaign.loadSeverlog(serverString);
//        myCampaign.loadImpressionLog(impressionString);
//        myCampaign.loadImpressionLog(clickString);

        //should catch the error and not crash the program
        assertNull(myCampaign.getClicks());
//        assertNull(myCampaign.getImpressions());
//        assertNull(myCampaign.getServerEntries());
    }


    @Test
    public void totalCost(){
        Campaign myCampaign = new Campaign();
        assertEquals(110, myCampaign.calcTotalCost(impressions, clicks) );

    }

    @Test
    public void numImpressions(){
        Campaign myCampaign = new Campaign();
        assertEquals(10, myCampaign.calcImpressions(impressions));

        String impressionString = "/Users/danielraad/Desktop/UniCourses/SEG/Code/myImpressionCop.csv";
        myCampaign.loadImpressionLog(impressionString);
        assertEquals(8, myCampaign.calcImpressions(myCampaign.getImpressions()));
    }

    @Test
    public void impCost(){
        Campaign myCampaign = new Campaign();
        assertEquals(55, myCampaign.calcTotalImpCost(impressions));

        /** checking whether or not the data is cleaned for incorrect data */
        String impressionString = "/Users/danielraad/Desktop/UniCourses/SEG/Code/myImpressionCop.csv";
        myCampaign.loadImpressionLog(impressionString);
        assertEquals(50, myCampaign.calcTotalImpCost(myCampaign.getImpressions()));
    }



    @Test
    public void numClicks(){
        Campaign myCampaign = new Campaign();
        assertEquals(10, myCampaign.calcClicks(clicks));
    }

    @Test
    public void clickCost(){
        Campaign myCampaign = new Campaign();
        assertEquals(55, myCampaign.calcTotalClickCost(clicks));

        /** checking whether or not the data is cleaned for incorrect data */
        String clickString = "/Users/danielraad/Desktop/UniCourses/SEG/Code/myClickLogCop.csv";
        myCampaign.loadClickLog(clickString);
        assertEquals(50, myCampaign.calcTotalClickCost(myCampaign.getClicks()));

    }

    @Test
    public void calcConversions(){
        Campaign myCampaign = new Campaign();
        assertEquals(9, myCampaign.calcConversions(serverEntryArrayList9));
        assertEquals(2, myCampaign.calcConversions(serverEntryArrayList2));
    }


    @Test
    public void calcConvRate(){
        Campaign campaign = new Campaign();
        assertEquals(0.9, campaign.calcConvRate(serverEntryArrayList9, clicks));
        assertEquals(0.2, campaign.calcConvRate(serverEntryArrayList2, clicks));
    }


    @Test
    public void calcBounces(){
        Campaign campaign = new Campaign();
        assertEquals(2, campaign.calcBounces(serverEntryArrayList2));
        assertEquals(1, campaign.calcBounces(serverEntryArrayList9));
    }

    @Test
    public void calcBounceRate(){
        Campaign campaign = new Campaign();
        assertEquals(0.2, campaign.calcBounceRate(serverEntryArrayList2, clicks));
        assertEquals(0.1, campaign.calcBounceRate(serverEntryArrayList9, clicks));
    }


    @Test
    public void calcUniques(){
        Campaign campaign = new Campaign();
        assertEquals(9, campaign.calcUniques(clicks));
    }


    @Test
    public void calcCTR(){
        Campaign campaign = new Campaign();
        assertEquals(1, campaign.calcCTR(clicks, impressions));
    }


    @Test
    public void calcCPA(){
        Campaign campaign = new Campaign();
        assertEquals(55, campaign.calcCPA(impressions, clicks, serverEntryArrayList2));
        assertEquals(12.22222222, campaign.calcCPA(impressions, clicks, serverEntryArrayList9));
    }

    @Test
    public void calcCPC(){
        Campaign campaign = new Campaign();
        assertEquals(5.5, campaign.calcCPC(clicks));
    }

    @Test
    public void calcCPM(){
        Campaign campaign = new Campaign();
        assertEquals(550, campaign.calcCPM(impressions));
    }


}
