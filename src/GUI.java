import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.plaf.basic.BasicScrollPaneUI.ViewportChangeHandler;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GUI extends Application {
	private static Controller controller;

	private static File impressionFile;
	private static File clicksFile;
	private static File serverFile;
	private final String[] metrics = {"Number of Impressions", "Number of Clicks", "Number of Uniques", "Number of Bounces", "Number of Conversions", "Total Cost", "Click Through Rate", "Cost per Acquisition", "Cost per Click", "Cost per thousand Impressions", "Bounce Rate"};
	private ComboBox<String> fileOption;
	private final String[] granularityOptions = {"Day", "Week", "Month", "Year"};
	private final String[] genders = {"Male", "Female"};
	private final String[] ageGroups = {"<25", "25-34", "35-44", "45-54", ">54"};
	private final String[] incomeGroups = {"Low", "Medium", "High"};
	private final String[] contextGroups = {"News", "Shopping", "Social Media", "Blog", "Hobbies", "Travel"};
	
	public GUI() {}
	
	public GUI(Controller con) {
		this.controller = con;
		launch();
	}


	public static void main(String[] args) {
		launch();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		mainWindow();
		fileChooserWindow();
		
	}
	
	
	private void mainWindow() throws Exception{
		Group root = new Group();
		Stage primaryStage = new Stage();
		
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
		
		histogramButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				histogramWindow();
				
			}
		});
		
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
		

		Label chart1 = new Label("Create Line Graph");
		Label chart2 = new Label("Create Histogram");
		Label chart3 = new Label("Create Pie Chart");
		Label chart4 = new Label("View Metrics");
		chartOptions.getChildren().addAll(lineGraphButton, chart1, histogramButton, chart2, pieChartButton, chart3, dataButton, chart4);
		
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
		Label conversionRateLabel = new Label("Conversion Rate");
		
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
		TextField conversionRateField = new TextField();
		
		bounceRateField.setText("" + controller.getBounceRate());
		noImpressionsField.setText("" + controller.getTotalImpressions());
		noClicksField.setText("" + controller.getTotalClicks());
		noUniquesField.setText("" + controller.getTotalUnique());
		noBouncesField.setText("" + controller.getTotalBounces());
		noConversionsField.setText("" + controller.getTotalConversions());
		ctrField.setText("" + controller.getCTR());
		cpaField.setText("" + controller.getCPA());
		cpcField.setText("" + controller.getCPC());
		cpmField.setText("" + controller.getCPM());
		totalCostField.setText("" + controller.getTotalCost());
		conversionRateField.setText("" + controller.getConversionRate());
		
		
		Label granularityLabel = new Label("Granularity");
		ComboBox<String> granularityField = new ComboBox<String>(FXCollections.observableArrayList(granularityOptions));
		granularityField.setValue("Week");
		
		
		granularityLayout.getChildren().addAll(granularityLabel, granularityField);
		metricLabels1.getChildren().addAll(bounceRateLabel, noImpressionsLabel, noClicksLabel, noUniquesLabel, noBouncesLabel, noConversionsLabel);
		metricBoxes1.getChildren().addAll(bounceRateField, noImpressionsField, noClicksField, noUniquesField, noBouncesField, noConversionsField);
		metricLabels2.getChildren().addAll(ctrLabel, cpaLabel, cpcLabel, cpmLabel, totalCostLabel, conversionRateLabel);
		metricBoxes2.getChildren().addAll(ctrField, cpaField, cpcField, cpmField, totalCostField, conversionRateField);
		
		metricLayout.getChildren().addAll(metricLabels1, metricBoxes1, metricLabels2, metricBoxes2);
		windowLayout.getChildren().addAll(granularityLayout, metricLayout);
		granularityLayout.setMargin(granularityLabel, new Insets(3,0,0,0));
		windowLayout.setMargin(granularityLayout, new Insets(10, 10, 5, 10));
		
		metricLayout.setMargin(metricLabels2, new Insets(5, 0, 0, 30));
		metricLayout.setMargin(metricLabels1, new Insets(5, 0, 0, 10));
		
		windowLayout.setStyle("-fx-background-color: azure;");
			
		Scene scene = new Scene(windowLayout, 600, 500);
		newWindow.setScene(scene);
		newWindow.setTitle("Metrics");
		newWindow.show();
	}
	
	private void histogramWindow() {
		Stage newWindow = new Stage();
		final ToggleGroup group = new ToggleGroup();
		
		RadioButton impressionsCheck = new RadioButton("Impression file");
		RadioButton clicksCheck = new RadioButton("Click file");
		RadioButton serverCheck = new RadioButton("Server file");
		
		VBox filterPane = new VBox();
		
		impressionsCheck.setToggleGroup(group);
		clicksCheck.setToggleGroup(group);
		serverCheck.setToggleGroup(group);
		serverCheck.setSelected(true);
		
		TilePane serverFilterOptions = serverFilters();
		TilePane clickFilterOptions = clickFilters();
		TilePane impressionFilterOptions = impressionFilters();
		
		filterPane.getChildren().add(serverFilterOptions);
		
		ComboBox<String> granularity = new ComboBox<String>(FXCollections.observableArrayList(granularityOptions));
		
		
		granularity.setValue("Choose Granularity");
		
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				
				RadioButton rb = (RadioButton) group.getSelectedToggle();
				
				if (rb.getText().equals("Impression file")) {
					filterPane.getChildren().clear();
					filterPane.getChildren().add(impressionFilterOptions);
				}
				else if (rb.getText().equals("Click file")) {
					filterPane.getChildren().clear();
					filterPane.getChildren().add(clickFilterOptions);
				}
				else if (rb.getText().equals("Server file")) {
					filterPane.getChildren().clear();
					filterPane.getChildren().add(serverFilterOptions);
				}
				
			}
			
		});
		
		HBox fileChecks = new HBox(10);
		VBox windowLayout = new VBox(10);
		HBox metricsGranularity = new HBox(30);
		
		Button createChart = new Button("Create");
		
		createChart.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				ArrayList<String> filters = new ArrayList<String>();
				ObservableList<Node> filterNodes = null;
				
				if (impressionsCheck.isSelected()) {
					filterNodes = impressionFilterOptions.getChildren();
				}
				else if (serverCheck.isSelected()) {
					filterNodes = serverFilterOptions.getChildren();
				}
				else if(clicksCheck.isSelected()) {
					filterNodes = clickFilterOptions.getChildren();
				}
				
				
				for (Node n : filterNodes) {
					if (n instanceof TextField) {
							filters.add(((TextField) n).getText());
					}
					else if (n instanceof ComboBox) {
							filters.add((String) ((ComboBox) n).getValue());
					}
				}
				
				LocalDateTime startDate = null;
				LocalDateTime endDate = null;
				ArrayList<String> contexts = new ArrayList<String>();
				ArrayList<String> ageGroups = new ArrayList<String>();
				ArrayList<String> incomes = new ArrayList<String>();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				if(filters.get(1).equals("") == false) {
					startDate = LocalDateTime.parse(filters.get(0), formatter);
				}
				if(filters.get(1).equals("") == false) {
					endDate = LocalDateTime.parse(filters.get(1), formatter);
				}	
				
				
				if (impressionsCheck.isSelected()) {
					if (filters.get(2) != null)
						contexts.add(filters.get(2));
					if (filters.get(4) != null)
						ageGroups.add(filters.get(4));
					if (filters.get(5) != null)
						incomes.add(filters.get(5));
					Filter filter = new Filter(startDate, endDate, contexts, filters.get(3), ageGroups, incomes);
					createHistogram(filter);
				}
				else {
					Filter filter = new Filter(startDate, endDate,  contexts, null, ageGroups, incomes);
					createHistogram(filter);
				}
				
				
			}
			
		});
		
		fileChecks.getChildren().addAll(clicksCheck, impressionsCheck, serverCheck);
		
		metricsGranularity.getChildren().addAll(granularity);
		
		
		
		windowLayout.getChildren().addAll(fileChecks, filterPane, metricsGranularity, createChart);
		windowLayout.setStyle("-fx-background-color: azure;");
		
		Scene scene = new Scene(windowLayout, 400, 400);
		newWindow.setScene(scene);
		newWindow.setTitle("Create Histogram");
		newWindow.show();
		
		
		
		
		newWindow.setTitle("Create Histogram");
		newWindow.show();
	}
	
	private TilePane serverFilters() {
		TilePane filters = new TilePane();
		
		filters.setPrefColumns(3);
		filters.setPrefRows(2);
		
		TextField entryDate = new TextField();
		TextField exitDate = new TextField();
		
		entryDate.setPromptText("Date From");
		exitDate.setPromptText("Date Until");
		
		filters.getChildren().addAll(entryDate, exitDate);
		
		return filters;
	}
	
	private TilePane clickFilters() {
		TilePane filters = new TilePane();
		
		filters.setPrefColumns(3);
		filters.setPrefRows(2);
		
		TextField entryDate = new TextField();
		TextField exitDate = new TextField();
		entryDate.setPromptText("Date From");
		exitDate.setPromptText("Date Until");
		filters.getChildren().addAll(entryDate, exitDate);
		
		
		
		return filters;
	}
	
	private TilePane impressionFilters() {
		TilePane filters = new TilePane();
		
		filters.setPrefColumns(3);
		filters.setPrefRows(2);
		
		TextField entryDate = new TextField();
		TextField exitDate = new TextField();
		entryDate.setPromptText("Date From");
		exitDate.setPromptText("Date Until");
		ComboBox<String> age = new ComboBox<String>(FXCollections.observableArrayList(ageGroups));
		ComboBox<String> context = new ComboBox<String>(FXCollections.observableArrayList(contextGroups));
		ComboBox<String> gender = new ComboBox<String>(FXCollections.observableArrayList(genders));
		ComboBox<String> income = new ComboBox<String>(FXCollections.observableArrayList(incomeGroups));
		
		age.setPromptText("Age Group");
		context.setPromptText("Context");
		gender.setPromptText("Gender");
		income.setPromptText("Income");
		
		filters.getChildren().addAll(entryDate, exitDate, context, gender, age, income);
		
		return filters;
	}
	
	private void createHistogram(Filter filters) {
		Histogram histogram = controller.createHistogram(5, 2, filters);
		ArrayList<Bar> bars = histogram.getBars();
		
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		final BarChart<String, Number> chart = new BarChart<String, Number>(xAxis, yAxis);
		
		xAxis.setLabel("Cost Range");
		yAxis.setLabel("Frequency");
		chart.setTitle("Total Cost Histogram");
		XYChart.Series series1 = new XYChart.Series();
		chart.setBarGap(0);
		chart.setCategoryGap(0);
		
		for (Bar b : bars) {
			series1.getData().add(new XYChart.Data(b.getLowerBound() + " - " + b.getUpperBound(), b.getFrequency()));
		}
		
		chart.getData().addAll(series1);
		
		Stage window = new Stage();
		Label chartLabel = new Label("Histogram for cost variation");
		VBox vbox = new VBox(15);
		vbox.getChildren().addAll(chartLabel, chart);
		
		Scene scene = new Scene(vbox, 700, 700);
		window.setScene(scene);
		window.show();
		
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
		
		Label bounceLabel = new Label("How many pages define a bounce:");
		TextField bounceDefiner = new TextField("1");
		
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
					System.out.println(serverFile.getAbsolutePath());
					System.out.println(clicksFile.getAbsolutePath());
					System.out.println(impressionFile.getAbsolutePath());
					//campaign.setBounceDefinition(Integer.parseInt(bounceDefiner.getText()));
					controller.loadNewCampaign(serverFile.getAbsolutePath(), clicksFile.getAbsolutePath(), impressionFile.getAbsolutePath(), 1);
					// TODO change 1 to use a bounceDefinition specified by the user
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
		
		Scene scene = new Scene(fileChooserLayout, 350, 300);
		newWindow.setScene(scene);
		newWindow.show();
	}
}