import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
	private final TimeInterval[] granularityOptions = {TimeInterval.HOUR, TimeInterval.DAY, TimeInterval.WEEK, TimeInterval.MONTH};
	private final String[] genders = {"Male", "Female"};
	private final String[] ageGroups = {"<25", "25-34", "35-44", "45-54", ">54"};
	private final String[] incomeGroups = {"Low", "Medium", "High"};
	private final String[] contextGroups = {"News", "Shopping", "Social Media", "Blog", "Hobbies", "Travel"};
	final String cssDefault = "-fx-border-color: black;\n"
			+ "-fx-border-insets: 50.0 10.0 0.0 10.0;\n"
			+ "-fx-border-width: 3;\n"
			+ "-fx-padding: 10;\n"
			+ "-fx-border-style: segments(10,15,15,15) solid;\n";

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
		HBox mainArea = new HBox(10);

		HBox metrics = getMetricsWindow();


		
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
		
		histogramButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				histogramWindow();
				
			}
		});
		
		lineGraphButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				lineWindow();
			}
		});


		Image lineGraphimage = new Image(new FileInputStream("lineGraphIcon.png"), 100, 100, true, true);
		Image histogramimage = new Image(new FileInputStream("barChartIcon.png"), 100, 100, true, true);
		Image pieChartImage = new Image(new FileInputStream("pieChartIcon.png"), 100, 100, true, true);
		
		histogramButton.setGraphic(new ImageView(histogramimage));
		pieChartButton.setGraphic(new ImageView(pieChartImage));
		lineGraphButton.setGraphic(new ImageView(lineGraphimage));
		

		Label chart1 = new Label("Create Line Graph");
		Label chart2 = new Label("Create Histogram");
		Label chart3 = new Label("Create Pie Chart");
		chartOptions.getChildren().addAll(lineGraphButton, chart1, histogramButton, chart2, pieChartButton, chart3);
		BorderPane.setMargin(metrics, new Insets(150, 100, 10, 50));
		BorderPane.setMargin(chartOptions, new Insets(50, 25, 10, 50));
		

		mainWindow.setTop(toolBar);
		mainWindow.setCenter(chartOptions);
		mainWindow.setRight(metrics);
		mainWindow.setStyle("-fx-background-color: #c8e3f0;");
		layering.getChildren().addAll(canvas, mainWindow);
		canvas.widthProperty().bind(primaryStage.widthProperty());
		canvas.heightProperty().bind(primaryStage.heightProperty());
		
		root.getChildren().add(layering);
		Scene scene = new Scene(root, 900, 550);
		scene.getStylesheets().add("/GUI.css");
		primaryStage.setScene(scene);
		primaryStage.setTitle("Ad Auction Dashboard");
		primaryStage.show();
	}
	
	private HBox getMetricsWindow() {
		
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
		ComboBox<TimeInterval> granularityField = new ComboBox<TimeInterval>(FXCollections.observableArrayList(granularityOptions));
		granularityField.setValue(TimeInterval.WEEK);
		
		
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
		

		return metricLayout;
			

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
		
		ComboBox<TimeInterval> granularity = new ComboBox<TimeInterval>(FXCollections.observableArrayList(granularityOptions));
		
		
		granularity.setPromptText("Choose Granularity");
		
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
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
				if(filters.get(0).equals("") == false) {
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
		windowLayout.setStyle("-fx-background-color: #c8e3f0;");
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
		ArrayList<HistogramBar> histogramBars = histogram.getHistogramBars();
		
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		final BarChart<String, Number> chart = new BarChart<String, Number>(xAxis, yAxis);
		
		xAxis.setLabel("Cost Range");
		yAxis.setLabel("Frequency");
		chart.setTitle("Total Cost Histogram");
		XYChart.Series series1 = new XYChart.Series();
		chart.setBarGap(0);
		chart.setCategoryGap(0);
		
		for (HistogramBar b : histogramBars) {
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
	private void lineWindow(){
		Stage window = new Stage();

		RadioButton serverR = new RadioButton("Server file");
		RadioButton clicksR = new RadioButton("Click file");
		RadioButton impressionR = new RadioButton("Impression file");

		final ToggleGroup radioGroup = new ToggleGroup();
		serverR.setToggleGroup(radioGroup);
		clicksR.setToggleGroup(radioGroup);
		impressionR.setToggleGroup(radioGroup);
		serverR.setSelected(true);

		TilePane serverFilterOptions = serverFilters();
		TilePane clickFilterOptions = clickFilters();
		TilePane impressionFilterOptions = impressionFilters();

		HBox serverMetricsOptions = addServerHbox();
		HBox clickMetricsOptions = addClickHbox();
		HBox impressionMetricsOptions = addImpHbox();

		VBox filterPane = new VBox();

		filterPane.getChildren().addAll(serverFilterOptions,serverMetricsOptions);

		ComboBox<TimeInterval> granularity = new ComboBox<TimeInterval>(FXCollections.observableArrayList(granularityOptions));
		granularity.setPromptText("Choose Granularity");

		radioGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {

				RadioButton button =(RadioButton)radioGroup.getSelectedToggle();

				if(button.getText().equals("Server file")){
					filterPane.getChildren().clear();
					filterPane.getChildren().addAll(serverFilterOptions,serverMetricsOptions);
				} else if(button.getText().equals("Click file")){
					filterPane.getChildren().clear();
					filterPane.getChildren().addAll(clickFilterOptions,clickMetricsOptions);
				} else if(button.getText().equals("Impression file")){
					filterPane.getChildren().clear();
					filterPane.getChildren().addAll(impressionFilterOptions,impressionMetricsOptions);
				}
			}
		});

		HBox fileChecks = new HBox(10);
		VBox windowLayout = new VBox(10);
		HBox metricsGranularity = new HBox(30);

		Button createLineGraph = new Button("Create");

		createLineGraph.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				ArrayList<String> filters = new ArrayList<String>();
				ArrayList<Metric> metrics = new ArrayList<Metric>();
				ObservableList<Node> filterNodes = null;
				ObservableList<Node> metricsNodes = null;

				if(impressionR.isSelected()){
					filterNodes = impressionFilterOptions.getChildren();
					metricsNodes = impressionMetricsOptions.getChildren();

				} else if(serverR.isSelected()){
					filterNodes = serverFilterOptions.getChildren();
					metricsNodes = serverMetricsOptions.getChildren();
				} else if(clicksR.isSelected()){
					filterNodes = clickFilterOptions.getChildren();
					metricsNodes = clickMetricsOptions.getChildren();
				}


				for (Node n : metricsNodes) {
					if (n instanceof ChoiceBox) {
						metrics.add((Metric) ((ChoiceBox)n).getValue());
					}
				}


				for (Node n : filterNodes) {
					if (n instanceof TextField) {
						filters.add(((TextField)n).getText());
					} else if (n instanceof ComboBox){
						filters.add((String)((ComboBox)n).getValue());
					}
				}

				LocalDateTime startDate = null;
				LocalDateTime endDate = null;
				ArrayList<String> context = new ArrayList<String>();
				ArrayList<String> ageGroups = new ArrayList<String>();
				ArrayList<String> incomes = new ArrayList<String>();
				//Same situation as above, this time looking for each filter
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
				if(filters.get(0).equals("") == false) {
					startDate = LocalDateTime.parse(filters.get(0),formatter);
				}
				if (filters.get(1).equals("") == false){
					endDate = LocalDateTime.parse(filters.get(1), formatter);
				}

				if(impressionR.isSelected()){
					if (filters.get(2) != null)
						context.add(filters.get(2));
					if(filters.get(4) != null)
						ageGroups.add(filters.get(4));
					if (filters.get(5) != null)
						incomes.add(filters.get(5));

					Filter filter = new Filter(startDate,endDate,context,filters.get(3),ageGroups,incomes);
					createLineChart(metrics.get(0),filter);
				} else {
					Filter filter = new Filter(startDate,endDate,context,null,ageGroups,incomes);
					createLineChart(metrics.get(0),filter);
				}
			}
		});

		fileChecks.getChildren().addAll(clicksR,impressionR,serverR);
		metricsGranularity.getChildren().addAll(granularity);
		windowLayout.getChildren().addAll(fileChecks,filterPane,metricsGranularity,createLineGraph);
		windowLayout.setStyle("-fx-background-color: #c8e3f0;");

		Scene scene = new Scene(windowLayout,400,400);
		window.setScene(scene);
		window.setTitle("Create LineGraph");
		window.show();


	}


	//Metric
	public HBox addImpHbox(){
		ChoiceBox<Metric> impressionMetricChoices = new ChoiceBox<Metric>();
		impressionMetricChoices.getItems().add(Metric.TOTAL_IMPRESSIONS);
		impressionMetricChoices.getItems().add(Metric.TOTAL_IMPRESSION_COST);
		impressionMetricChoices.getItems().add(Metric.CPA);
		impressionMetricChoices.getItems().add(Metric.CPM);
		impressionMetricChoices.getItems().add(Metric.TOTAL_UNIQUES);
		impressionMetricChoices.setValue(Metric.TOTAL_IMPRESSIONS);
		Label impressionMetricLabel = new Label("Metric: ");
		HBox impMetricBox = new HBox(impressionMetricLabel,impressionMetricChoices);

		final String cssDefault = "-fx-border-insets: 10.0 10.0 0.0 10.0;\n"
				+ "-fx-border-width: 3;\n"
				+ "-fx-padding: 10;\n";



		return impMetricBox;
	}




	public HBox addClickHbox(){
		ChoiceBox<Metric> clickMetricChoices = new ChoiceBox<Metric>();
		clickMetricChoices.getItems().add(Metric.TOTAL_CLICKS);
		clickMetricChoices.getItems().add(Metric.TOTAL_CLICK_COST);
		clickMetricChoices.getItems().add(Metric.CTR);
		clickMetricChoices.getItems().add(Metric.CPC);
		clickMetricChoices.setValue(Metric.TOTAL_CLICKS);
		Label label = new Label("Metric: ");
		HBox hBox = new HBox(label,clickMetricChoices);
	return hBox;
	}


	public HBox addServerHbox(){
		ChoiceBox<Metric> serverChoices = new ChoiceBox<Metric>();
		serverChoices.getItems().add(Metric.BOUNCES);
		serverChoices.getItems().add(Metric.BOUNCE_RATE);
		serverChoices.getItems().add(Metric.TOTAL_CONVERSIONS);
		serverChoices.getItems().add(Metric.CONVERSION_RATE);
		serverChoices.setValue(Metric.BOUNCES);
		Label label = new Label("Metric: ");
		HBox labelBox = new HBox(label,serverChoices);

	return labelBox;
	}

	private void createLineChart(Metric metric, Filter filters){
		Stage stage = new Stage();
		stage.setTitle("Line Chart");
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Date");


		final LineChart<String,Number> lineChart =
				new LineChart<String,Number>(xAxis,yAxis);

		lineChart.setTitle("impression line chart");

		XYChart.Series series = new XYChart.Series();
		series.setName("");

		LineGraph lineGraph = new LineGraph(metric, TimeInterval.DAY, controller,filters); //this is where the filters, metric and granularity will be passed
		ArrayList<DataPoint> dataPoints = lineGraph.getDataPoints();//you can then just grab the data from it and use it in the graph
		System.out.println("This is data points" + dataPoints);

		for (DataPoint dp : dataPoints) {
			series.getData().add(new XYChart.Data(dp.getStartTime().toString(), dp.getMetric()));
		}

		Scene scene  = new Scene(lineChart,800,600);
		lineChart.getData().add(series);

		stage.setScene(scene);
		stage.show();

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
				try {
					mainWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
				newWindow.close();
				
			}
		});
		
		
		loadServer.setMinWidth(125);
		loadClicks.setMinWidth(125);
		continueButton.setMinWidth(125);
		
		loadedFileText.getChildren().addAll(clickFileLabel, impressionFileLabel, serverFileLabel);
		loadedFileText.setMargin(clickFileLabel, new Insets(25, 10, 10, 20));
		loadedFileText.setMargin(impressionFileLabel, new Insets(16, 10, 10, 20));
		loadedFileText.setMargin(serverFileLabel, new Insets(19, 10, 10, 20));
		
		fileChooserButtons.getChildren().addAll(loadClicks, loadImpressions, loadServer, continueButton);
		fileChooserButtons.setMargin(loadClicks, new Insets(50, 10, 10, 20));
		fileChooserButtons.setMargin(loadImpressions, new Insets(10, 10, 10, 20));
		fileChooserButtons.setMargin(loadServer, new Insets(10, 10, 10, 20));
		fileChooserButtons.setMargin(continueButton, new Insets(10, 30, 10, 200));
		
		fileChooserLayout.getChildren().addAll(fileChooserButtons, loadedFileText);
		fileChooserLayout.setStyle("-fx-background-color: #c8e3f0;");
		
		
		Scene scene = new Scene(fileChooserLayout, 350, 300);
		fileChooserLayout.getStylesheets().add("/GUI.css");
		newWindow.setScene(scene);
		newWindow.show();
	}
}