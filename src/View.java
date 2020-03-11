
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class View extends Application implements Observer {

	private static Controller controller;
	private static Campaign campaign;
	private static File impressionFile;
	private static File clicksFile;
	private static File serverFile;
	
	
	public View() {
		
	}
	
	public View(Controller con) {
		this.controller = con;
		this.campaign = controller.getCampaign();

		System.out.print(campaign.testIsConnected());
		View.launch(View.class);
	}
	
	public static void main(String[] args) {
		launch();
	}
	
	public void start(Stage primaryStage) throws Exception {
		
		VBox buttons = new VBox(10);
		Button loadImpressions = new Button("Load Impression file");
		Button loadClicks = new Button("Load Clicks file");
		Button loadServer = new Button("Load Server file");
		
		
		loadImpressions.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Choose Impressions File");
				impressionFile = fileChooser.showOpenDialog(primaryStage);
			}
		});
		
		loadClicks.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Choose Clicks File");
				clicksFile = fileChooser.showOpenDialog(primaryStage);
				
			}
		});
		
		loadServer.setOnAction(new EventHandler<ActionEvent>() {
		
			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Choose Server File");
				serverFile = fileChooser.showOpenDialog(primaryStage);
				
			}
		
		});
		
		
		
		Button startButton = new Button("Continue");
		startButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (impressionFile != null && clicksFile != null && serverFile != null) {
					campaign.initialise(clicksFile.getAbsolutePath(),impressionFile.getAbsolutePath(),
							    serverFile.getAbsolutePath());
					campaign.loadImpressionLog(impressionFile.getAbsolutePath());
					campaign.loadClickLog(clicksFile.getAbsolutePath());
					campaign.loadSeverlog(serverFile.getAbsolutePath());
					campaign.calculateMetrics();
					primaryStage.getScene().setRoot(createTableWindow());
					primaryStage.setWidth(600);
					primaryStage.setHeight(600);
				}
				//TODO remove (this is just for testing)
				controller.createHistogram(null, campaign, 7);
			}

		});
		
		buttons.getChildren().addAll(loadImpressions, loadClicks, loadServer, startButton);
		Scene scene = new Scene(buttons);
		primaryStage.setScene(scene);
		
		primaryStage.setTitle("Ad Auction");
		primaryStage.sizeToScene();
		primaryStage.show();


	}

	
	public Group createTableWindow() {
		Group root = new Group();
		System.out.print(campaign.testIsConnected());
		
		TableView impTable = generateImpressionTable();
		TableView clickTable = generateClickTable();
		TableView serverTable = generateServerTable();
		
		//All the text boxes that contain the different metrics
		Label totImprLabl = new Label("Total Impressions: ");
		TextField totImprField = new TextField();
		totImprField.setEditable(false);
		HBox totImprBox = new HBox(totImprLabl, totImprField);
		totImprField.setText("" + campaign.getTotalImpressions());
		
		Label totImprCostLabl = new Label("Total Impression costs: ");
		TextField totImprCostField = new TextField();
		totImprCostField.setEditable(false);
		HBox totImprCostBox = new HBox(totImprCostLabl, totImprCostField);
		totImprCostField.setText("" + campaign.getTotalImpressionCost());
		
		Label totClickslabl = new Label("Total Clicks: ");
		TextField totClicksField = new TextField();
		totClicksField.setEditable(false);
		HBox totClicksBox = new HBox(totClickslabl, totClicksField);
		totClicksField.setText("" + campaign.getTotalClicks());
		
		Label totClicksCostlabl = new Label("Total Impression costs: ");
		TextField totClicksCostField = new TextField();
		totClicksCostField.setEditable(false);
		HBox totClickCostsBox = new HBox(totClicksCostlabl, totClicksCostField);
		totClicksCostField.setText("" + campaign.getTotalClickCost());
		
		Label totConversionslabl = new Label("Total Conversions: ");
		TextField totConversionsField = new TextField();
		totConversionsField.setEditable(false);
		HBox totConversionsBox = new HBox(totConversionslabl, totConversionsField);
		totConversionsField.setText("" + campaign.getTotalConversions());
		
		Label conversionRatelabl = new Label("Conversion Rate: ");
		TextField conversionRateField = new TextField();
		conversionRateField.setEditable(false);
		HBox conversionRateBox = new HBox(conversionRatelabl, conversionRateField);
		conversionRateField.setText("" + campaign.getConversionRate());
		
		Label totBounceslabl = new Label("Total Bounces: ");
		TextField totBouncesField = new TextField();
		totBouncesField.setEditable(false);
		HBox totBouncesBox = new HBox(totBounceslabl, totBouncesField);
		totBouncesField.setText("" + campaign.getTotalBounces());
		
		Label bounceRatelabl = new Label("Bounce Rate: ");
		TextField bounceRateField = new TextField();
		bounceRateField.setEditable(false);
		HBox bounceRateBox = new HBox(bounceRatelabl, bounceRateField);
		bounceRateField.setText("" + campaign.getBounceRate());
		
		Label totUniqueslabl = new Label("Total Unique Users: ");
		TextField totUniquesField = new TextField();
		totUniquesField.setEditable(false);
		HBox totUniquesBox = new HBox(totUniqueslabl, totUniquesField);
		totUniquesField.setText("" + campaign.getTotalUnique());
		
		Label CTRlabl = new Label("CTR: ");
		TextField CTRField = new TextField();
		CTRField.setEditable(false);
		HBox CTRBox = new HBox(CTRlabl, CTRField);
		CTRField.setText("" + campaign.getCTR());
		
		Label CPAlabl = new Label("CPA: ");
		TextField CPAField = new TextField();
		CPAField.setEditable(false);
		HBox CPABox = new HBox(CPAlabl, CPAField);
		CPAField.setText("" + campaign.getCPA());
		
		Label CPClabl = new Label("CPC: ");
		TextField CPCField = new TextField();
		CPCField.setEditable(false);
		HBox CPCBox = new HBox(CPClabl, CPCField);
		CPCField.setText("" + campaign.getCPC());
		
		Label CPMlabl = new Label("CPM: ");
		TextField CPMField = new TextField();
		CPMField.setEditable(false);
		HBox CPMBox = new HBox(CPMlabl, CPMField);
		CPMField.setText("" + campaign.getCPM());
		
		
		//Layout of all the tables and boxes and puts them in tabs
		VBox impressionMetrics = new VBox(10);
		impressionMetrics.getChildren().addAll(impTable, totImprBox, totImprCostBox, CPABox, CPMBox, totUniquesBox);
		
		VBox clickMetrics = new VBox(10);
		clickMetrics.getChildren().addAll( clickTable, totClicksBox, totClickCostsBox, CTRBox, CPCBox);
		
		VBox serverMetrics = new VBox(10);
		serverMetrics.getChildren().addAll(serverTable, totBouncesBox, bounceRateBox, totConversionsBox, conversionRateBox);
		
		TabPane tabPane = new TabPane();
		Tab tab1 = new Tab("Impression log", impressionMetrics);
		Tab tab2 = new Tab("Click log", clickMetrics);
		Tab tab3 = new Tab("Server log", serverMetrics);
		
		
		tabPane.getTabs().addAll(tab1, tab2, tab3);
		
		VBox vbox = new VBox(tabPane);
		root.getChildren().add(vbox);
		
		return root;
	}
	
	//functions that generate the tables for all three logs and fills them
	private TableView generateImpressionTable() {
		TableView tableView = new TableView();
		
		TableColumn<LocalDateTime, Impression> col1 = new TableColumn<>("Date");
		col1.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
		
		TableColumn<String, Impression> col2 = new TableColumn<>("ID");
		col2.setCellValueFactory(new PropertyValueFactory<>("ID"));

		TableColumn<String, Impression> col3 = new TableColumn<>("Gender");
		col3.setCellValueFactory(new PropertyValueFactory<>("gender"));
		
		TableColumn<String, Impression> col4 = new TableColumn<>("Age");
		col4.setCellValueFactory(new PropertyValueFactory<>("ageGroup"));
		
		TableColumn<String, Impression> col5 = new TableColumn<>("Income");
		col5.setCellValueFactory(new PropertyValueFactory<>("income"));
		
		TableColumn<String, Impression> col6 = new TableColumn<>("Context");
		col6.setCellValueFactory(new PropertyValueFactory<>("context"));
		
		TableColumn<Float, Impression> col7 = new TableColumn<>("Impression Cost");
		col7.setCellValueFactory(new PropertyValueFactory<>("ImpressionCost"));
		
		tableView.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7);
		tableView.getItems().addAll(campaign.getImpressions());
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		tableView.getSelectionModel().setCellSelectionEnabled(true);
		return tableView;
	}
	
	private TableView generateClickTable() {
		TableView tableView = new TableView();
		
		TableColumn<LocalDateTime, Click> col1 = new TableColumn<>("Date");
		col1.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
		
		TableColumn<String, Click> col2 = new TableColumn<>("ID");
		col2.setCellValueFactory(new PropertyValueFactory<>("iD"));
		
		TableColumn<Float, Click> col3 = new TableColumn<>("Impression Cost");
		col3.setCellValueFactory(new PropertyValueFactory<>("clickCost"));
		
		tableView.getColumns().addAll(col1, col2, col3);
		tableView.getItems().addAll(campaign.getClicks());
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		tableView.getSelectionModel().setCellSelectionEnabled(true);
		return tableView;
	}
	
	private TableView generateServerTable() {
		TableView tableView = new TableView();
		
		TableColumn<LocalDateTime, ServerEntry> col1 = new TableColumn<>("Entry Date");
		col1.setCellValueFactory(new PropertyValueFactory<>("entryDate"));
		
		TableColumn<String, ServerEntry> col2 = new TableColumn<>("ID");
		col2.setCellValueFactory(new PropertyValueFactory<>("iD"));
		
		TableColumn<LocalDateTime, ServerEntry> col3 = new TableColumn<>("Exit Date");
		col3.setCellValueFactory(new PropertyValueFactory<>("exitDate"));
		
		TableColumn<Integer, ServerEntry> col4 = new TableColumn<>("Pages Viewed");
		col4.setCellValueFactory(new PropertyValueFactory<>("pagesViewed"));
		
		TableColumn<String, ServerEntry> col5 = new TableColumn<>("Conversion");
		col5.setCellValueFactory(new PropertyValueFactory<>("conversion"));
		
		tableView.getColumns().addAll(col1, col2, col3, col4, col5);
		tableView.getItems().addAll(campaign.getServerEntries());
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		tableView.getSelectionModel().setCellSelectionEnabled(true);
		return tableView;
	}

	@Override
	public void observableChanged(Observable observable) {

	}
}
