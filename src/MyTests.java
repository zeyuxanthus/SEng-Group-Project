import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.Line;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MyTests {



//
//    @Test
//    public void checkConnection(){
//
//        try {
//            assertEquals(false, Campaign.connect().isClosed());
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//    }


    @Test
    public void mergeArrays(){
        Campaign campaign = new Campaign();
        campaign.loadLogs("/Users/danielraad/IdeaProjects/TestCode/server_log.csv", "/Users/danielraad/IdeaProjects/TestCode/click_log.csv", "/Users/danielraad/IdeaProjects/TestCode/impression_log.csv");
    }


    @Test
    public void getters(){
        Campaign campaign = new Campaign();
        ArrayList list1 = campaign.getClicks();
        ArrayList list2 = campaign.getClicks();
        ArrayList list3 = campaign.getClicks();
        ArrayList list4 = campaign.getImpressions();
        ArrayList list5 = campaign.getServerEntries();

        assertEquals(list1, list2);
    }

    @Test
    public void fileDeletion(){
        File tmpDir =new File("/Users/danielraad/IdeaProjects/TestCode/test.db");
        if(tmpDir.exists()) {
            FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
            assertEquals(false, tmpDir.exists());
        }
    }
//
//    @Test
//    public void loadMainDatabase(){
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//
//        String serverString = "/Users/danielraad/Desktop/UniCourses/SEG/Code/SEng-Group-Project/server_log.csv";
//        String clickString = "/Users/danielraad/Desktop/UniCourses/SEG/Code/SEng-Group-Project/click_log.csv";
//        String impressionString = "/Users/danielraad/Desktop/UniCourses/SEG/Code/SEng-Group-Project/impression_log.csv";
//
//        Campaign campaign = new Campaign();
//        campaign.initialise(clickString,impressionString, serverString);
//
//        try{
//
//            String sql = "SELECT count() FROM sqlite_master WHERE type='table';";
//
//            Connection conn = Campaign.connect();
//            Statement stmt = conn.createStatement();
//
//            ResultSet rs = stmt.executeQuery(sql);
//            rs.next();
//
//            assertEquals(3, rs.getInt(1));
//
//        }catch(SQLException e){
//            e.printStackTrace();
//        }
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//    }

//
//    /**
//     * Test for the loading of a saved campaign
//     */
//    @Test
//    public void loadSavedDatabase(){
//
//
//        String campaignName = "test2";
//
//        Controller controller = new Controller();
//        controller.loadCampaign(campaignName);
//
//        try{
//
//            String sql = "SELECT count() FROM sqlite_master WHERE type='table';";
//
//            Connection conn = Campaign.connect(campaignName);
//            Statement stmt = conn.createStatement();
//
//            ResultSet rs = stmt.executeQuery(sql);
//            rs.next();
//
//            assertEquals(3, rs.getInt(1));
//
//        }catch(SQLException e){
//            e.printStackTrace();
//        }
//
//    }


    /**
     * Testing that the file exists without the campaign
     */
    @Test
    public void persistence(){
        File nikeFile = new File("Nike.db");
        assertEquals(true, nikeFile.exists());
    }


    /**
     * Will test whether the saveGraph method has correctly saved the file
     * potentially change the route of the file so that Controller saves the file
     */

    @Test
    public void saveChart(){

        Campaign campaign = new Campaign();
        LineGraph line = new LineGraph(campaign);
        String chart = "graph1";
        File chartImage = new File(chart);
        line.saveGraph(chart);
        assertEquals(true, chartImage.exists());

    }

    /**
     * checking whether or not the two arraylists produce the same results
     */
    @Test
    public void datapoint(){

    }


    @Test
    public void granularityTesting(){

    }


    /**
     *  big data base testing the ability to load a million impressions
     *
     */
    @Test
    public void millionImpressions(){

    }


    /**
     * filter integration tests
     */
//
//    @Test
//    public void filterImpressionTest(){
//
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//
//        Campaign campaign = new Campaign();
//        String serverString = "myServer.csv";
//        String clickString = "myClickLog.csv";
//        String impressionString = "myImpression.csv";
//        campaign.initialise(clickString, impressionString, serverString);
//
//        LineGraph line = new LineGraph(campaign);
//
//        ArrayList list = line.filterImpressionLog("Male,Low");
//        assertEquals(1, list.size());
//
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//
//
//    }
//
//    @Test
//    public void filterClickTest(){
//
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//
//
//        Campaign campaign = new Campaign();
//        String serverString = "myServer.csv";
//        String clickString = "myClickLog.csv";
//        String impressionString = "myImpression.csv";
//        campaign.initialise(clickString, impressionString, serverString);
//
//        LineGraph line = new LineGraph(campaign);
//
//        ArrayList<Click> list = line.filterClickLog("Male,Low");
//
//        assertEquals(1, list.size());
//
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//
//
//    }

//    @Test
//    public void filterServerTest(){
//
//
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//
//        Campaign campaign = new Campaign();
//        String serverString = "myServer.csv";
//        String clickString = "myClickLog.csv";
//        String impressionString = "myImpression.csv";
//        campaign.initialise(clickString, impressionString, serverString);
//
//        LineGraph line = new LineGraph(campaign);
//
//        ArrayList list = line.filterServerLog("Female,Medium");
//        assertEquals(6, list.size());
//
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//
//
//    }
//
//    @Test
//    public void filterByDate(){
//
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//
//        Campaign campaign = new Campaign();
//
//
//        LineGraph line = new LineGraph(campaign);
//
//        ArrayList list = line.filterImpressionLog("2015-01-01 12:00:04 + 2015-01-01 12:00:13");
//        assertEquals(5, list.size());
//
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//
//    }
//
//
//
//
//    @Test
//    public void filterByContext(){
//
//
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//
//        Campaign campaign = new Campaign();
//        String serverString = "myServer.csv";
//        String clickString = "myClickLog.csv";
//        String impressionString = "myImpression.csv";
//        campaign.initialise(clickString, impressionString, serverString);
//
//
//        LineGraph line = new LineGraph(campaign);
//
//        ArrayList list = line.filterImpressionLog("Social Media");
//        assertEquals(3, list.size());
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//
//    }
//
//    @Test
//    public void filterByGender(){
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//
//        Campaign campaign = new Campaign();
//        String serverString = "myServer.csv";
//        String clickString = "myClickLog.csv";
//        String impressionString = "myImpression.csv";
//        campaign.initialise(clickString, impressionString, serverString);
//
//
//        LineGraph line = new LineGraph(campaign);
//
//        ArrayList list = line.filterImpressionLog("Female");
//        assertEquals(8, list.size());
//
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//
//    }
//
//    @Test
//    public void filterByAge(){
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//
//        Campaign campaign = new Campaign();
//        String serverString = "myServer.csv";
//        String clickString = "myClickLog.csv";
//        String impressionString = "myImpression.csv";
//        campaign.initialise(clickString, impressionString, serverString);
//
//
//        LineGraph line = new LineGraph(campaign);
//
//        ArrayList list = line.filterImpressionLog(">54");
//        assertEquals(3, list.size());
//
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//    }
//
//    @Test
//    public void filterByIncome(){
//
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//
//        Campaign campaign = new Campaign();
//        String serverString = "myServer.csv";
//        String clickString = "myClickLog.csv";
//        String impressionString = "myImpression.csv";
//        campaign.initialise(clickString, impressionString, serverString);
//
//
//        LineGraph line = new LineGraph(campaign);
//
//        ArrayList list = line.filterImpressionLog("Medium");
//        assertEquals(6, list.size());
//
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//    }
//
//
//
//
//
//    /**
//     * Integration tests for the metric tests, whether they are producing the correct original data results
//     * this is the sql database which is being queried
//     */
//    @Test
//    public void metricTest(){
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//
//        Campaign campaign = new Campaign();
//
//        String serverString = "myServer.csv";
//        String clickString = "myClickLog.csv";
//        String impressionString = "myImpression.csv";
//        campaign.initialise(clickString, impressionString, serverString);
//        campaign.calculateMetrics();
//
//        assertEquals(118097.92122826772, campaign.getTotalCost());
//        assertEquals(486104, campaign.getTotalImpressions());
//        assertEquals(23923, campaign.getTotalClicks());
//        assertEquals(487.0554979820736, campaign.getTotalImpressionCost());
//        assertEquals(117610.0, campaign.getTotalClickCost());
//        assertEquals(23806, campaign.getTotalUnique());
//        assertEquals(0, campaign.getTotalBounces());
//        assertEquals(2026, campaign.getTotalConversions());
//
//        assertEquals(58.2911753347817, campaign.getCPA());
//        assertEquals(4.916189441123605, campaign.getCPC());
//        assertEquals(1.0019573959113144, campaign.getCPM());
//        assertEquals(0.049213748498263744, campaign.getCTR());
//        assertEquals(0.0846883752037788, campaign.getConversionRate());
//        assertEquals(0, campaign.getBounceRate());
//
//
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//
//
//
//    }
//
//    @Test
//    public void sqlTotalCost(){
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//        Campaign campaign = new Campaign();
//
//        String serverString = "myServer.csv";
//        String clickString = "myClickLog.csv";
//        String impressionString = "myImpression.csv";
//        campaign.initialise(clickString, impressionString, serverString);
//        campaign.calcTotalCost();
//
//        assertEquals(0.008638 + 157.888888 , campaign.getTotalCost());
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//    }
//
//    @Test
//    public void sqlTotalImpressions() {
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//        Campaign campaign = new Campaign();
//
//        String serverString = "myServer.csv";
//        String clickString = "myClickLog.csv";
//        String impressionString = "myImpression.csv";
//        campaign.initialise(clickString, impressionString, serverString);
//        campaign.calcImpressions();
//
//        assertEquals(10, campaign.getTotalImpressions());
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//    }
//
//    @Test
//    public void sqlTotalClicks(){
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//        Campaign campaign = new Campaign();
//
//        String serverString = "myServer.csv";
//        String clickString = "myClickLog.csv";
//        String impressionString = "myImpression.csv";
//        campaign.initialise(clickString, impressionString, serverString);
//        campaign.calcClicks();
//
//        assertEquals(10, campaign.getTotalClicks());
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//    }
//
//    @Test
//    public void sqlTotalImpressionCost(){
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//        Campaign campaign = new Campaign();
//
//        String serverString = "myServer.csv";
//        String clickString = "myClickLog.csv";
//        String impressionString = "myImpression.csv";
//        campaign.initialise(clickString, impressionString, serverString);
//        campaign.calcTotalImpCost();
//
//        assertEquals(0.008638, campaign.getTotalImpressionCost());
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//    }
//
//    @Test
//    public void sqlTotalClickCost(){
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//        Campaign campaign = new Campaign();
//
//        String serverString = "myServer.csv";
//        String clickString = "myClickLog.csv";
//        String impressionString = "myImpression.csv";
//        campaign.initialise(clickString, impressionString, serverString);
//        campaign.calcTotalClickCost();
//
//        assertEquals(157.888888, campaign.getTotalClickCost());
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//    }
//
//    @Test
//    public void sqlTotalUnique(){
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//        Campaign campaign = new Campaign();
//
//        String serverString = "myServer.csv";
//        String clickString = "myClickLog.csv";
//        String impressionString = "myImpression.csv";
//        campaign.initialise(clickString, impressionString, serverString);
//        campaign.calcUniques();
//
//        assertEquals(10, campaign.getTotalUnique());
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//    }
//
//    @Test
//    public void sqlTotalBounce(){
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//        Campaign campaign = new Campaign();
//
//        String serverString = "myServer.csv";
//        String clickString = "myClickLog.csv";
//        String impressionString = "myImpression.csv";
//        campaign.initialise(clickString, impressionString, serverString);
//        campaign.calcBounces();
//
//        assertEquals(2, campaign.getTotalBounces());
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//    }
//
//    @Test
//    public void sqlTotalConversions(){
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//        Campaign campaign = new Campaign();
//
//        String serverString = "myServer.csv";
//        String clickString = "myClickLog.csv";
//        String impressionString = "myImpression.csv";
//        campaign.initialise(clickString, impressionString, serverString);
//        campaign.calcConversions();
//
//        assertEquals(2, campaign.getTotalConversions());
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//    }
//
//    @Test
//    public void sqlCPA(){
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//        Campaign campaign = new Campaign();
//
//        String serverString = "myServer.csv";
//        String clickString = "myClickLog.csv";
//        String impressionString = "myImpression.csv";
//        campaign.initialise(clickString, impressionString, serverString);
//        campaign.calcCPA();
//
//        assertEquals(78.948763, campaign.getCPA());
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//    }
//
//    @Test
//    public void sqlCPM(){
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//        Campaign campaign = new Campaign();
//
//        String serverString = "myServer.csv";
//        String clickString = "myClickLog.csv";
//        String impressionString = "myImpression.csv";
//        campaign.initialise(clickString, impressionString, serverString);
//        campaign.calcCPM();
//
//        assertEquals(0.8638, campaign.getCPM());
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//    }
//
//    @Test
//    public void sqlCPC(){
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//        Campaign campaign = new Campaign();
//        String serverString = "myServer.csv";
//        String clickString = "myClickLog.csv";
//        String impressionString = "myImpression.csv";
//        campaign.initialise(clickString, impressionString, serverString);
//        campaign.calcCPC();
//
//        assertEquals(15.7888888, campaign.getCPC());
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//
//    }
//
//    @Test
//    public void sqlCTR(){
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//        Campaign campaign = new Campaign();
//
//        String serverString = "myServer.csv";
//        String clickString = "myClickLog.csv";
//        String impressionString = "myImpression.csv";
//        campaign.initialise(clickString, impressionString, serverString);
//        campaign.calcCTR();
//
//        assertEquals(1, campaign.getCTR());
//        FileDelete.fileDelete("/Users/danielraad/IdeaProjects/TestCode/test.db");
//    }

    /**
     * Tests for the calculations when an array is being passed through
     */

    @Test
    public void totalCost(){
        Campaign myCampaign = new Campaign();
        assertEquals(110, myCampaign.calcTotalCost(impressions, clicks) );

    }

    @Test
    public void numImpressions(){
        Campaign myCampaign = new Campaign();
        assertEquals(10, myCampaign.calcImpressions(impressions));

    }

    @Test
    public void impCost(){
        Campaign myCampaign = new Campaign();
        assertEquals(55, myCampaign.calcTotalImpCost(impressions));

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
        assertEquals(12.222222222222221, campaign.calcCPA(impressions, clicks, serverEntryArrayList9));
    }

    @Test
    public void calcCPC(){
        Campaign campaign = new Campaign();
        assertEquals(5.5, campaign.calcCPC(clicks));
    }

    @Test
    public void calcCPM(){
        Campaign campaign = new Campaign();
        assertEquals(5500, campaign.calcCPM(impressions));
    }


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
        Impression imp1 = new Impression(formatDateTime, "1", "Male", "<25", "High", "Travel", (float) 1);
        Impression imp2 = new Impression(formatDateTime, "2", "Male", "<25", "High", "Travel", (float)2);
        Impression imp3 = new Impression(formatDateTime, "3", "Male", "<25", "High", "Travel", (float)3);
        Impression imp4 = new Impression(formatDateTime, "4", "Male", "<25", "High", "Travel", (float)4);
        Impression imp5 = new Impression(formatDateTime, "5", "Male", "<25", "High", "Travel", (float)5);
        Impression imp6 = new Impression(formatDateTime, "6", "Male", "<25", "High", "Travel", (float)6);
        Impression imp7 = new Impression(formatDateTime, "7", "Male", "<25", "High", "Travel", (float)7);
        Impression imp8 = new Impression(formatDateTime, "8", "Male", "<25", "High", "Travel", (float)8);
        Impression imp9 = new Impression(formatDateTime, "9", "Male", "<25", "High", "Travel", (float)9);
        Impression imp10 = new Impression(formatDateTime, "1", "Male", "<25", "High", "Travel", (float)10);
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
        Impression imp1 = new Impression(formatDateTime, "1", "Male", "<25", "High", "Travel", (float)1);
        Impression imp2 = new Impression(formatDateTime, "1", "Male", "<25", "High", "Travel", (float)-2);
        Impression imp3 = new Impression(formatDateTime, "1", "Male", "<25", "High", "Travel", (float)-3);
        Impression imp4 = new Impression(formatDateTime, "1", "Male", "<25", "High", "Travel", (float)4);
        Impression imp5 = new Impression(formatDateTime, "1", "Male", "<25", "High", "Travel", (float)5);
        Impression imp6 = new Impression(formatDateTime, "1", "Male", "<25", "High", "Travel", (float)6);
        Impression imp7 = new Impression(formatDateTime, "1", "Male", "<25", "High", "Travel", (float)7);
        Impression imp8 = new Impression(formatDateTime, "1", "Male", "<25", "High", "Travel", (float)8);
        Impression imp9 = new Impression(formatDateTime, "1", "Male", "<25", "High", "Travel", (float)9);
        Impression imp10 = new Impression(formatDateTime, "1", "Male", "<25", "High", "Travel", (float)10);
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



}
