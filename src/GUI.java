import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GUI extends Application {
	private static Controller controller;
	private static Campaign campaign;
	private static File impressionFile;
	private static File clicksFile;
	private static File serverFile;
	private static Label serverLabel;
	private static Label clickLabel;
	private static Label impressionLabel;
	private ComboBox<String> fileOption;
	
	
	public GUI() {}
	
	public GUI(Controller con) {
		this.controller = con;
		this.campaign = controller.getCampaign();

		System.out.print(campaign.testIsConnected());
		GUI.launch(GUI.class);
	}


	public static void main(String[] args) {
		launch();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Group root = new Group();

		StackPane layering = new StackPane();
		Canvas canvas = new Canvas();

		
		BorderPane mainWindow = new BorderPane();
		HBox toolBar = new HBox();
		VBox chartOptions = new VBox(10);
		VBox loadedFiles = new VBox();
		
		String[] fileOptionText = {"Load...", "Save", "Save as..."};
		
		fileOption = new ComboBox<String>(FXCollections.observableArrayList(fileOptionText));
		fileOption.setValue("File");
		fileOption.setOnAction(e -> {
			if (fileOption.getValue().equals("Load...")) {
				fileChooserWindow();
				
			}
		});
		
		toolBar.getChildren().add(fileOption);
		Button lineGraphButton = new Button();
		Button histogramButton = new Button();
		Button pieChartButton = new Button();
		Button dataButton = new Button();
		
		dataButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				viewMetricsWindow();
				
			}
		});
		
		Image lineGraphimage = new Image(new FileInputStream("lineGraphIcon.png"), 100, 100, true, true);
		Image histogramimage = new Image(new FileInputStream("barChartIcon.png"), 100, 100, true, true);
		Image pieChartImage = new Image(new FileInputStream("pieChartIcon.png"), 100, 100, true, true);
		Image dataimage = new Image(new FileInputStream("dataIcon.png"), 100, 100, true, true);
		
		histogramButton.setGraphic(new ImageView(histogramimage));
		pieChartButton.setGraphic(new ImageView(pieChartImage));
		dataButton.setGraphic(new ImageView(dataimage));
		lineGraphButton.setGraphic(new ImageView(lineGraphimage));
		
		serverLabel = new Label();
		clickLabel = new Label();
		impressionLabel = new Label();
		Label chart1 = new Label("Create Line Graph");
		Label chart2 = new Label("Create Bar chart");
		Label chart3 = new Label("Create Histogram");
		Label chart4 = new Label("View Metrics");
		chartOptions.getChildren().addAll(lineGraphButton, chart1, histogramButton, chart2, pieChartButton, chart3, dataButton, chart4);
		loadedFiles.getChildren().addAll(clickLabel, impressionLabel, serverLabel);
		
		BorderPane.setMargin(loadedFiles, new Insets(10, 100, 10, 100));
		BorderPane.setMargin(chartOptions, new Insets(50, 25, 10, 50));
		
		mainWindow.setTop(toolBar);
		mainWindow.setCenter(chartOptions);
		mainWindow.setRight(loadedFiles);
		
		mainWindow.setStyle("-fx-background-color: azure;");
		layering.getChildren().addAll(canvas, mainWindow);
		canvas.widthProperty().bind(primaryStage.widthProperty());
		canvas.heightProperty().bind(primaryStage.heightProperty());
		
		root.getChildren().add(layering);
		Scene scene = new Scene(root, 500, 700);
		scene.getStylesheets().add("/GUI.css");
		primaryStage.setScene(scene);
		primaryStage.setTitle("Ad Auction Dashboard");
		primaryStage.show();
	}
	
	private void viewMetricsWindow() {
		
		Stage newWindow = new Stage();
		
		HBox metricLayout = new HBox(10);
		VBox metricLabels1 = new VBox(18);
		VBox metricLabels2 = new VBox(18);
		VBox metricBoxes1 = new VBox (10);
		VBox metricBoxes2 = new VBox (10);
		VBox windowLayout = new VBox (10);
		HBox granularityLayout = new HBox (10);
		
		Label bounceRateLabel = new Label("Bounce Rate");
		Label noImpressionsLabel = new Label("No. of Impressions");
		Label noClicksLabel = new Label("No. of Clicks");
		Label noUniquesLabel = new Label("No. of Uniques");
		Label noBouncesLabel = new Label("No. of Bounces");
		Label noConversionsLabel = new Label("No. of Conversions");
		Label ctrLabel = new Label("Click Through Rate");
		Label cpaLabel = new Label("CPA");
		Label cpcLabel = new Label("Cost per Conversion");
		Label cpmLabel = new Label("CPM");
		Label totalCostLabel = new Label("Total Cost");
		
		TextField bounceRateField = new TextField();
		TextField noImpressionsField = new TextField();
		TextField noClicksField = new TextField();
		TextField noUniquesField = new TextField();
		TextField noBouncesField = new TextField();
		TextField noConversionsField = new TextField();
		TextField ctrField = new TextField();
		TextField cpaField = new TextField();
		TextField cpcField = new TextField();
		TextField cpmField = new TextField();
		TextField totalCostField = new TextField();
		
		String[] granularityOptions = {"Day", "Week", "Month", "Year"};
		Label granularityLabel = new Label("Granularity");
		ComboBox<String> granularityField = new ComboBox<String>(FXCollections.observableArrayList(granularityOptions));
		granularityField.setValue("Week");
		
		
		granularityLayout.getChildren().addAll(granularityLabel, granularityField);
		metricLabels1.getChildren().addAll(bounceRateLabel, noImpressionsLabel, noClicksLabel, noUniquesLabel, noBouncesLabel, noConversionsLabel);
		metricBoxes1.getChildren().addAll(bounceRateField, noImpressionsField, noClicksField, noUniquesField, noBouncesField, noConversionsField);
		metricLabels2.getChildren().addAll(ctrLabel, cpaLabel, cpcLabel, cpmLabel, totalCostLabel);
		metricBoxes2.getChildren().addAll(ctrField, cpaField, cpcField, cpmField, totalCostField);
		
		metricLayout.getChildren().addAll(metricLabels1, metricBoxes1, metricLabels2, metricBoxes2);
		windowLayout.getChildren().addAll(granularityLayout, metricLayout);
		windowLayout.setMargin(granularityLayout, new Insets(10, 10, 5, 10));
		
		metricLayout.setMargin(metricLabels2, new Insets(5, 0, 0, 30));
		metricLayout.setMargin(metricLabels1, new Insets(5, 0, 0, 10));
		
		windowLayout.setStyle("-fx-background-color: azure;");
			
		Scene scene = new Scene(windowLayout, 600, 500);
		newWindow.setScene(scene);
		newWindow.setTitle("Metrics");
		newWindow.show();
	}
	
	
	private void fileChooserWindow() {
		
		
		VBox fileChooserButtons = new VBox(10);
		VBox loadedFileText = new VBox(10);
		HBox fileChooserLayout = new HBox(10);
		
		Stage newWindow = new Stage();
		newWindow.setTitle("Load files");
		
		Button loadImpressions = new Button("Load Impression file");
		Button loadClicks = new Button("Load Clicks file");
		Button loadServer = new Button("Load Server file");
		Button continueButton = new Button("Continue");
		
		newWindow.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				fileOption.setValue("File");
				
			}
		});
		
		Label serverFileLabel = new Label();
		Label clickFileLabel = new Label();
		Label impressionFileLabel = new Label();
		
		loadImpressions.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Choose Impressions File");
				impressionFile = fileChooser.showOpenDialog(newWindow);
				if (impressionFile != null)
					impressionFileLabel.setText(impressionFile.getName());
			}
		});
		
		loadClicks.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Choose Clicks File");
				clicksFile = fileChooser.showOpenDialog(newWindow);
				if (clicksFile != null)
					clickFileLabel.setText(clicksFile.getName());
				
			}
		});
		
		loadServer.setOnAction(new EventHandler<ActionEvent>() {
		
			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Choose Server File");
				serverFile = fileChooser.showOpenDialog(newWindow);
				if (serverFile != null)
					serverFileLabel.setText(serverFile.getName());
			}
		
		});
		
		continueButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (serverFile != null && clicksFile != null && impressionFile != null) {
					serverLabel.setText(serverFile.getName());
					clickLabel.setText(clicksFile.getName());
					impressionLabel.setText(impressionFile.getName());
				}
				fileOption.setValue("File");
				newWindow.close();
				
			}
		});
		
		
		loadedFileText.getChildren().addAll(clickFileLabel, impressionFileLabel, serverFileLabel);
		loadedFileText.setMargin(clickFileLabel, new Insets(25, 10, 10, 20));
		loadedFileText.setMargin(impressionFileLabel, new Insets(16, 10, 10, 20));
		loadedFileText.setMargin(serverFileLabel, new Insets(19, 10, 10, 20));
		
		fileChooserButtons.getChildren().addAll(loadClicks, loadImpressions, loadServer, continueButton);
		fileChooserButtons.setMargin(loadClicks, new Insets(20, 10, 10, 20));
		fileChooserButtons.setMargin(loadImpressions, new Insets(10, 10, 10, 20));
		fileChooserButtons.setMargin(loadServer, new Insets(10, 10, 10, 20));
		fileChooserButtons.setMargin(continueButton, new Insets(50, 50, 10, 50));
		
		fileChooserLayout.getChildren().addAll(fileChooserButtons, loadedFileText);
		
		Scene scene = new Scene(fileChooserLayout, 500, 300);
		newWindow.setScene(scene);
		newWindow.show();
	}
}
