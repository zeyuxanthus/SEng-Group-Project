import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GUI extends Application {
    private static Controller controller;

    private static File impressionFile;
    private static File clicksFile;
    private static File serverFile;
    private final String[] metrics = {"Number of Impressions", "Number of Clicks", "Number of Uniques", "Number of " +
			"Bounces", "Number of Conversions", "Total Cost", "Click Through Rate", "Cost per Acquisition", "Cost per " +
			"Click", "Cost per thousand Impressions", "Bounce Rate"};
    private ComboBox<String> fileOption;
    private final TimeInterval[] granularityOptions = {TimeInterval.HOUR, TimeInterval.DAY, TimeInterval.WEEK,
			TimeInterval.MONTH};
    private final String[] genders = {"Male", "Female"};
    private final String[] ageGroups = {"<25", "25-34", "35-44", "45-54", ">54"};
    private final String[] incomeGroups = {"Low", "Medium", "High"};
    private final String[] contextGroups = {"News", "Shopping", "Social Media", "Blog", "Hobbies", "Travel"};
    final String cssDefault = "-fx-border-color: black;\n"
            + "-fx-border-insets: 50.0 10.0 0.0 10.0;\n"
            + "-fx-border-width: 3;\n"
            + "-fx-padding: 10;\n"
            + "-fx-border-style: segments(10,15,15,15) solid;\n";

    public GUI() {
    }

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


    private void mainWindow() throws Exception {
        Group root = new Group();
        Stage primaryStage = new Stage();

        StackPane layering = new StackPane();
        Canvas canvas = new Canvas();


        BorderPane mainWindow = new BorderPane();
        HBox toolBar = new HBox();
        VBox chartOptions = new VBox(10);
        HBox mainArea = new HBox(10);

        VBox metrics = getMetricsWindow();


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

        //controller.createBarChar(Metric.TOTAL_IMPRESSIONS, BarChartType.DAY_OF_WEEK,
        //              new Filter(null, null, new ArrayList<>(), null, new ArrayList<String>(), new ArrayList<String>()));
    }

    private VBox getMetricsWindow() {

    	
    	
        HBox metricLayout = new HBox(10);
        VBox metricLabels1 = new VBox(18);
        VBox metricLabels2 = new VBox(18);
        VBox metricBoxes1 = new VBox(10);
        VBox metricBoxes2 = new VBox(10);
        VBox windowLayout = new VBox(10);
        HBox granularityLayout = new HBox(10);
        HBox files = new HBox(10);
        
        Label serverLabel = new Label(serverFile.getName());
        Label impressionLabel = new Label(impressionFile.getName());
        Label clickLabel = new Label(clicksFile.getName());

        
        
        
        Label bounceRateLabel = new Label("Bounce Rate");
        Label noImpressionsLabel = new Label("No. of Impressions");
        Label noClicksLabel = new Label("No. of Clicks");
        Label noUniquesLabel = new Label("No. of Uniques");
        Label noBouncesLabel = new Label("No. of Bounces");
        Label noConversionsLabel = new Label("No. of Conversions");
        Label ctrLabel = new Label("Click Through Rate");
        Label cpaLabel = new Label("Cost Per Aquisition");
        Label cpcLabel = new Label("Cost per Conversion");
        Label cpmLabel = new Label("Cost per 1000 impressions");
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


        Label granularityLabel = new Label("Time Interval");
        ComboBox<TimeInterval> granularityField =
				new ComboBox<TimeInterval>(FXCollections.observableArrayList(granularityOptions));
        granularityField.setValue(TimeInterval.WEEK);


        granularityLayout.getChildren().addAll(granularityLabel, granularityField);
        metricLabels1.getChildren().addAll(bounceRateLabel, noImpressionsLabel, noClicksLabel, noUniquesLabel,
										   noBouncesLabel, noConversionsLabel);
        metricBoxes1.getChildren().addAll(bounceRateField, noImpressionsField, noClicksField, noUniquesField,
										  noBouncesField, noConversionsField);
        metricLabels2.getChildren().addAll(ctrLabel, cpaLabel, cpcLabel, cpmLabel, totalCostLabel, conversionRateLabel);
        metricBoxes2.getChildren().addAll(ctrField, cpaField, cpcField, cpmField, totalCostField, conversionRateField);

        files.getChildren().addAll(clickLabel, impressionLabel, serverLabel);

        metricLayout.getChildren().addAll(metricLabels1, metricBoxes1, metricLabels2, metricBoxes2);
        windowLayout.getChildren().addAll(granularityLayout, metricLayout);
        granularityLayout.setMargin(granularityLabel, new Insets(3, 0, 0, 0));
        //windowLayout.setMargin(granularityLayout, new Insets(10, 10, 5, 10));
        
        granularityLayout.setMargin(granularityLabel, new Insets(0,0,0,10));
        metricLayout.setMargin(metricLabels2, new Insets(5, 0, 0, 30));
        metricLayout.setMargin(metricLabels1, new Insets(5, 0, 0, 10));


        return windowLayout;


    }

    private void histogramWindow() {
        Stage newWindow = new Stage();
        VBox filterPane = new VBox(10);

        TilePane impressionFilterOptions = impressionFilters();

        filterPane.getChildren().add(impressionFilterOptions);

        ComboBox<TimeInterval> granularity =
				new ComboBox<TimeInterval>(FXCollections.observableArrayList(granularityOptions));

        granularity.setValue(TimeInterval.DAY);
        Label granLabel = new Label("Granularity: ");
        
        VBox windowLayout = new VBox(10);
        HBox metricsGranularity = new HBox(10);

        Button createChart = new Button("Create");

        createChart.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                ArrayList<String> filters = new ArrayList<String>();
                ObservableList<Node> filterNodes = null;
               filterNodes = impressionFilterOptions.getChildren();


               for (Node n : filterNodes) {
               	if (n instanceof VBox) {
               		for (Node m : ((VBox) n).getChildren()) {
               			 if (m instanceof TextField) {
                                filters.add(((TextField) m).getText());
                            } else if (m instanceof ComboBox) {
                                filters.add((String) ((ComboBox) m).getValue());
                            }
               		}
               	}
               }

                LocalDateTime startDate = null;
                LocalDateTime endDate = null;
                ArrayList<String> contexts = new ArrayList<String>();
                ArrayList<String> ageGroups = new ArrayList<String>();
                ArrayList<String> incomes = new ArrayList<String>();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                if (filters.get(0).equals("") == false) {
                    startDate = LocalDateTime.parse(filters.get(0), formatter);
                }
                if (filters.get(1).equals("") == false) {
                    endDate = LocalDateTime.parse(filters.get(1), formatter);
                }


                if (filters.get(2) != null)
                    contexts.add(filters.get(2));
                if (filters.get(4) != null)
                    ageGroups.add(filters.get(4));
                if (filters.get(5) != null)
                    incomes.add(filters.get(5));
                Filter filter = new Filter(startDate, endDate, contexts, filters.get(3), ageGroups, incomes);
                createHistogram(filter);



            }

        });


        metricsGranularity.getChildren().addAll(granLabel, granularity);


        windowLayout.getChildren().addAll(filterPane, metricsGranularity, createChart);
        windowLayout.setStyle("-fx-background-color: #c8e3f0;");
        Scene scene = new Scene(windowLayout, 400, 400);
        newWindow.setScene(scene);
        newWindow.setTitle("Create Histogram");
        newWindow.show();


        newWindow.setTitle("Create Histogram");
        newWindow.show();
    }



    private TilePane impressionFilters() {
        TilePane filters = new TilePane(10, 5);

        filters.setPrefColumns(3);
        filters.setPrefRows(2);
        
        VBox ageBox = new VBox(5);
        VBox entryBox = new VBox(5);
        VBox exitBox = new VBox(5);
        VBox contextBox = new VBox(5);
        VBox genderBox = new VBox(5);
        VBox incomeBox = new VBox(5);
        
        Label ageLabel = new Label("Age Group");
        Label entryLabel = new Label("Date From");
        Label exitLabel = new Label("Date Until");
        Label contextLabel = new Label("Context");
        Label genderLabel = new Label("Gender");
        Label incomeLabel = new Label("Income");
        
        TextField entryDate = new TextField();
        TextField exitDate = new TextField();
//        entryDate.setPromptText("Date From");
//        exitDate.setPromptText("Date Until");
        ComboBox<String> age = new ComboBox<String>(FXCollections.observableArrayList(ageGroups));
        ComboBox<String> context = new ComboBox<String>(FXCollections.observableArrayList(contextGroups));
        ComboBox<String> gender = new ComboBox<String>(FXCollections.observableArrayList(genders));
        ComboBox<String> income = new ComboBox<String>(FXCollections.observableArrayList(incomeGroups));

        age.setPrefWidth(100);
        context.setPrefWidth(100);
        gender.setPrefWidth(100);
        income.setPrefWidth(100);
        
//        age.setPromptText("Age Group");
//        context.setPromptText("Context");
//        gender.setPromptText("Gender");
//        income.setPromptText("Income");

        entryBox.getChildren().addAll(entryLabel, entryDate);
        exitBox.getChildren().addAll(exitLabel, exitDate);
        contextBox.getChildren().addAll(contextLabel, context);
        genderBox.getChildren().addAll(genderLabel, gender);
        ageBox.getChildren().addAll(ageLabel, age);
        incomeBox.getChildren().addAll(incomeLabel, income);
        
        filters.getChildren().addAll(entryBox, exitBox, contextBox, genderBox, ageBox, incomeBox);

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

    private void lineWindow() {
        Stage window = new Stage();

        TilePane impressionFilterOptions = impressionFilters();

        HBox impressionMetricsOptions = addImpHbox();
        VBox filterPane = new VBox(10);
        Label granLabel = new Label("Granularity: ");
        filterPane.getChildren().addAll(impressionFilterOptions, impressionMetricsOptions);

        ComboBox<TimeInterval> granularity =
				new ComboBox<TimeInterval>(FXCollections.observableArrayList(granularityOptions));
        granularity.setValue(TimeInterval.DAY);

        VBox windowLayout = new VBox(10);
        HBox metricsGranularity = new HBox(10);

        Button createLineGraph = new Button("Create");

        createLineGraph.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ArrayList<String> filters = new ArrayList<String>();
                ArrayList<Metric> metrics = new ArrayList<Metric>();
                ObservableList<Node> filterNodes = null;
                ObservableList<Node> metricsNodes = null;
                metricsNodes = impressionMetricsOptions.getChildren();
                filterNodes = impressionFilterOptions.getChildren();

                for (Node n : metricsNodes) {
                    if (n instanceof ChoiceBox) {
                        metrics.add((Metric) ((ChoiceBox) n).getValue());
                    }
                }


                for (Node n : filterNodes) {
                	if (n instanceof VBox) {
                		for (Node m : ((VBox) n).getChildren()) {
                			 if (m instanceof TextField) {
                                 filters.add(((TextField) m).getText());
                             } else if (m instanceof ComboBox) {
                                 filters.add((String) ((ComboBox) m).getValue());
                             }
                		}
                	}
                }

                LocalDateTime startDate = null;
                LocalDateTime endDate = null;
                ArrayList<String> context = new ArrayList<String>();
                ArrayList<String> ageGroups = new ArrayList<String>();
                ArrayList<String> incomes = new ArrayList<String>();
                //Same situation as above, this time looking for each filter
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                if (filters.get(0).equals("") == false) {
                    startDate = LocalDateTime.parse(filters.get(0), formatter);
                }
                if (filters.get(1).equals("") == false) {
                    endDate = LocalDateTime.parse(filters.get(1), formatter);
                }
                if (filters.get(2) != null)
                    context.add(filters.get(2));
                if (filters.get(4) != null)
                    ageGroups.add(filters.get(4));
                if (filters.get(5) != null)
                    incomes.add(filters.get(5));

                Filter filter = new Filter(startDate, endDate, context, filters.get(3), ageGroups, incomes);
                createLineChart(metrics.get(0), filter, granularity.getValue());

            }
        });

        metricsGranularity.getChildren().addAll(granLabel, granularity);
        windowLayout.getChildren().addAll(filterPane, metricsGranularity, createLineGraph);
        windowLayout.setStyle("-fx-background-color: #c8e3f0;");

        Scene scene = new Scene(windowLayout, 400, 400);
        window.setScene(scene);
        window.setTitle("Create LineGraph");
        window.show();


    }


    //Metric
    public HBox addImpHbox() {
        ChoiceBox<Metric> impressionMetricChoices = new ChoiceBox<Metric>();
        impressionMetricChoices.getItems().add(Metric.TOTAL_IMPRESSIONS);
        impressionMetricChoices.getItems().add(Metric.TOTAL_IMPRESSION_COST);
        impressionMetricChoices.getItems().add(Metric.COST_PER_AQUISITION);
        impressionMetricChoices.getItems().add(Metric.COST_PER_CLICK);
        impressionMetricChoices.getItems().add(Metric.TOTAL_UNIQUES);
        impressionMetricChoices.setValue(Metric.TOTAL_IMPRESSIONS);
        impressionMetricChoices.getItems().add(Metric.TOTAL_CLICKS);
        impressionMetricChoices.getItems().add(Metric.TOTAL_CLICK_COST);
        impressionMetricChoices.getItems().add(Metric.CLICK_THROUGH_RATE);
        impressionMetricChoices.getItems().add(Metric.COST_PER_1000_IMPRESSIONS);
        impressionMetricChoices.setValue(Metric.TOTAL_CLICKS);
        impressionMetricChoices.getItems().add(Metric.BOUNCES);
        impressionMetricChoices.getItems().add(Metric.BOUNCE_RATE);
        impressionMetricChoices.getItems().add(Metric.TOTAL_CONVERSIONS);
        impressionMetricChoices.getItems().add(Metric.CONVERSION_RATE);
        impressionMetricChoices.setValue(Metric.BOUNCES);
        Label impressionMetricLabel = new Label("Metric: ");
        HBox impMetricBox = new HBox(impressionMetricLabel, impressionMetricChoices);

        final String cssDefault = "-fx-border-insets: 10.0 10.0 0.0 10.0;\n"
                + "-fx-border-width: 3;\n"
                + "-fx-padding: 10;\n";


        return impMetricBox;
    }

    private void createLineChart(Metric metric, Filter filters, TimeInterval interval) {
        Stage stage = new Stage();
        stage.setTitle("Line Chart");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Date");


        final LineChart<String, Number> lineChart =
                new LineChart<String, Number>(xAxis, yAxis);

        lineChart.setTitle(metric + " line chart");

        XYChart.Series series = new XYChart.Series();
        series.setName(metric + " per " + interval);
        

        LineGraph lineGraph = new LineGraph(metric, interval, controller, filters); //this is where the
		// filters, metric and granularity will be passed
        ArrayList<DataPoint> dataPoints = lineGraph.getDataPoints();//you can then just grab the data from it and use
		// it in the graph

        for (DataPoint dp : dataPoints) {
            series.getData().add(new XYChart.Data(dp.getStartTime().toString(), dp.getMetric()));
        }

        TilePane impressionFilterOptions = impressionFilters();

        HBox impressionMetricsOptions = addImpHbox();
        VBox filterPane = new VBox(10);
        Label granLabel = new Label("Granularity: ");
        filterPane.getChildren().addAll(impressionFilterOptions, impressionMetricsOptions);


        ComboBox<TimeInterval> granularity =
				new ComboBox<TimeInterval>(FXCollections.observableArrayList(granularityOptions));
        granularity.setValue(TimeInterval.DAY);

        VBox windowLayout = new VBox(10);
        HBox metricsGranularity = new HBox(10);

        Button createLineGraph = new Button("Create");

        createLineGraph.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ArrayList<String> filters = new ArrayList<String>();
                ArrayList<Metric> metrics = new ArrayList<Metric>();
                ObservableList<Node> filterNodes = null;
                ObservableList<Node> metricsNodes = null;
                metricsNodes = impressionMetricsOptions.getChildren();
                filterNodes = impressionFilterOptions.getChildren();

                for (Node n : metricsNodes) {
                    if (n instanceof ChoiceBox) {
                        metrics.add((Metric) ((ChoiceBox) n).getValue());
                    }
                }


                for (Node n : filterNodes) {
                	if (n instanceof VBox) {
                		for (Node m : ((VBox) n).getChildren()) {
                			 if (m instanceof TextField) {
                                 filters.add(((TextField) m).getText());
                             } else if (m instanceof ComboBox) {
                                 filters.add((String) ((ComboBox) m).getValue());
                             }
                		}
                	}
                }

                LocalDateTime startDate = null;
                LocalDateTime endDate = null;
                ArrayList<String> context = new ArrayList<String>();
                ArrayList<String> ageGroups = new ArrayList<String>();
                ArrayList<String> incomes = new ArrayList<String>();
                //Same situation as above, this time looking for each filter
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                if (filters.get(0).equals("") == false) {
                    startDate = LocalDateTime.parse(filters.get(0), formatter);
                }
                if (filters.get(1).equals("") == false) {
                    endDate = LocalDateTime.parse(filters.get(1), formatter);
                }
                if (filters.get(2) != null)
                    context.add(filters.get(2));
                if (filters.get(4) != null)
                    ageGroups.add(filters.get(4));
                if (filters.get(5) != null)
                    incomes.add(filters.get(5));

                Filter filter = new Filter(startDate, endDate, context, filters.get(3), ageGroups, incomes);
                LineGraph lineGraph = new LineGraph(metrics.get(0), granularity.getValue(), controller, filter); //this is where the
        		// filters, metric and granularity will be passed
                ArrayList<DataPoint> dataPoints = lineGraph.getDataPoints();//you can then just grab the data from it and use
        		// it in the graph
                XYChart.Series series2 = new XYChart.Series();
                series2.setName(metrics.get(0) + " per " + granularity.getValue());
                for (DataPoint dp : dataPoints) {
                    series2.getData().add(new XYChart.Data(dp.getStartTime().toString(), dp.getMetric()));
                }
                lineChart.getData().clear();
                lineChart.getData().add(series2);
                lineChart.setTitle(metrics.get(0) + " line chart");
            }
        });

        lineChart.setMinHeight(600);
        
        metricsGranularity.getChildren().addAll(granLabel, granularity);
        HBox metricsAndCreate = new HBox(25);
        metricsAndCreate.getChildren().addAll(metricsGranularity, createLineGraph);
        
        VBox mainWindow = new VBox(20);
        HBox filterOptions = new HBox(10);
        filterOptions.getChildren().addAll(filterPane, metricsAndCreate);
        mainWindow.getChildren().addAll(lineChart, filterOptions);
        mainWindow.setStyle("-fx-background-color: #c8e3f0;");
        Scene scene = new Scene(mainWindow, 800, 800);
        //lineChart.getData().add(series);
        new ZoomManager<>(mainWindow, lineChart, series);
        stage.setScene(scene);
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
                File tempFile = fileChooser.showOpenDialog(newWindow);
                if (tempFile != null) {
	                try {
						Scanner input = new Scanner(tempFile);
						if(input.nextLine().split(",").length == 7) {
							impressionFile = tempFile;
							impressionFileLabel.setText(impressionFile.getName());
						}
						else {
							displayError("File contains the wrong number of columns, check it's the right one?");
						}
						input.close();
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            }
        });

        loadClicks.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
            	FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Choose Clicks File");
                File tempFile = fileChooser.showOpenDialog(newWindow);
                if (tempFile != null) {
	                try {
						Scanner input = new Scanner(tempFile);
						if(input.nextLine().split(",").length == 3) {
							clicksFile = tempFile;
							clickFileLabel.setText(clicksFile.getName());
						}
						else {
							displayError("File contains the wrong number of columns, check it's the right one?");
						}
						input.close();
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            }
        });

        loadServer.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
            	FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Choose Clicks File");
                File tempFile = fileChooser.showOpenDialog(newWindow);
                if (tempFile != null) {
	                try {
						Scanner input = new Scanner(tempFile);
						if(input.nextLine().split(",").length == 5) {
							serverFile = tempFile;
							serverFileLabel.setText(serverFile.getName());
						}
						else {
							displayError("File contains the wrong number of columns");
						}
						input.close();
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
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
                    try {
                        mainWindow();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    newWindow.close();
                }
                else {
                	displayError("Not enough files selected");
                }
            }
        });


        loadServer.setMinWidth(125);
        loadClicks.setMinWidth(125);
        continueButton.setMinWidth(125);

        loadedFileText.getChildren().addAll(clickFileLabel, impressionFileLabel, serverFileLabel);
        loadedFileText.setMargin(clickFileLabel, new Insets(55, 10, 10, 20));
        loadedFileText.setMargin(impressionFileLabel, new Insets(16, 10, 10, 20));
        loadedFileText.setMargin(serverFileLabel, new Insets(19, 10, 10, 20));

        fileChooserButtons.getChildren().addAll(loadClicks, loadImpressions, loadServer, continueButton);
        fileChooserButtons.setMargin(loadClicks, new Insets(50, 10, 10, 20));
        fileChooserButtons.setMargin(loadImpressions, new Insets(10, 10, 10, 20));
        fileChooserButtons.setMargin(loadServer, new Insets(10, 10, 10, 20));
        fileChooserButtons.setMargin(continueButton, new Insets(10, 30, 10, 50));

        fileChooserLayout.getChildren().addAll(fileChooserButtons, loadedFileText);
        fileChooserLayout.setStyle("-fx-background-color: #c8e3f0;");


        Scene scene = new Scene(fileChooserLayout, 350, 300);
        fileChooserLayout.getStylesheets().add("/GUI.css");
        newWindow.setScene(scene);
        newWindow.show();
    }
    
    public void displayError(String error) {
    	Stage window = new Stage();
    	window.setTitle("Error");
    	Label errorLabel = new Label(error);
    	Scene scene = new Scene(errorLabel, 400, 100);
    	window.setScene(scene);
    	window.show();
    	
    }
}