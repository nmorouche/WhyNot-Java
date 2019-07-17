import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ResourceBundle;

public class GraphViewController implements Initializable {

	@FXML
	public VBox graphVbox;
	@FXML
	public Label connectedLabel;

	@FXML
	public Button parityChart;
	@FXML
	public Button reportChart;
	@FXML
	public Button averageAgeChart;
	@FXML
	public Button eventChart;

	private String dataBaseName;
	private String dataBaseurl;

	private Database database;




	@Override
	public void initialize(URL location, ResourceBundle resources) {

		File file = new File("settings.txt");
		File fileUsername = new File("username.txt");


		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			dataBaseurl = bufferedReader.readLine().split("=")[1];
			dataBaseName = bufferedReader.readLine().split("=")[1];

			FileReader fileLastnameReader = new FileReader(fileUsername);
			BufferedReader bufferedReaderLastName = new BufferedReader(fileLastnameReader);

			connectedLabel.setText("Connected as : "+ bufferedReaderLastName.readLine());

		} catch (IOException e) {
			e.printStackTrace();
		}

		database = new Database(dataBaseurl, dataBaseName);

		parityChart.setOnAction(this::changeVboxToParityChartOnClickBtn);
		reportChart.setOnAction(this::changeVboxToReportChartOnClickBtn);
		averageAgeChart.setOnAction(this::changeVboxToAverageAgeChartOnClickBtn);
		eventChart.setOnAction(this::changeVboxToEventChartOnClickBtn);
	}

	private void changeVboxToEventChartOnClickBtn(ActionEvent actionEvent) {
		try {

			String[][] numberOfParticipantsPerEvent = new String[2][];
			numberOfParticipantsPerEvent[0] = new String[50];
			numberOfParticipantsPerEvent[1] = new String[50];

			numberOfParticipantsPerEvent = database.queryEvent("events");

			EventBarChart(numberOfParticipantsPerEvent);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}


	private void changeVboxToReportChartOnClickBtn(ActionEvent actionEvent) {

		try {
			int valueOfManReported = database.reportQuery("users", "gender","Homme");
			int valueOfWomanReported = database.reportQuery("users","gender","Femme");
			parityReportedPieCharts(valueOfManReported, valueOfWomanReported);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

	}

	private void changeVboxToParityChartOnClickBtn(ActionEvent actionEvent){
		try {
			int valueOfMan = database.genericQuery("users", "gender","Homme");
			int valueOfWoman = database.genericQuery("users", "gender","Femme");
			parityPieCharts(valueOfMan, valueOfWoman);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

	}

	private void changeVboxToAverageAgeChartOnClickBtn(ActionEvent actionEvent) {
		try {
			double[] averageAge = database.queryAge("users", "birthdate");

			AverageAgeBarCharts(averageAge);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	private void EventBarChart(String[][] numberOfParticipantsPerEvent) {

		 int i = 0;

		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("Event");

		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Number of participant");

		XYChart.Series set1 = new XYChart.Series<>();

		while (numberOfParticipantsPerEvent[0][i] != null)	{
			try {
				set1.getData().add(new XYChart.Data(numberOfParticipantsPerEvent[0][i], NumberFormat.getInstance().parse(numberOfParticipantsPerEvent[1][i])));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			i++;
		}

		BarChart<?, ?> participantPerEventBarChart = new BarChart<String, Number>(xAxis, yAxis);
		participantPerEventBarChart.getData().addAll(set1);
		graphVbox.getChildren().clear();
		graphVbox.getChildren().add(participantPerEventBarChart);

	}

	private void parityPieCharts(int valueOfManReported, int ValueOfWomanReported){
		ObservableList<PieChart.Data> pieChartReportedData = FXCollections.observableArrayList(
				new PieChart.Data("Man", valueOfManReported),
				new PieChart.Data("Woman", ValueOfWomanReported));
		final PieChart chartReported = new PieChart(pieChartReportedData);
		chartReported.setTitle("Parity Man/Woman");

		graphVbox.getChildren().clear();
		graphVbox.getChildren().add(chartReported);

	}
	private void parityReportedPieCharts(int valueOfManReported, int ValueOfWomanReported){
		ObservableList<PieChart.Data> pieChartReportedData = FXCollections.observableArrayList(
				new PieChart.Data("Man Reported", valueOfManReported),
				new PieChart.Data("Woman Reported", ValueOfWomanReported));
		final PieChart chartReported = new PieChart(pieChartReportedData);
		chartReported.setTitle("Parity Man/Woman reported");

		graphVbox.getChildren().clear();
		graphVbox.getChildren().add(chartReported);

	}

	private void AverageAgeBarCharts(double[] averageAge) {

		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("gender");

		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Average Age");

		XYChart.Series set1 = new XYChart.Series<>();

		set1.getData().add(new XYChart.Data("Man", averageAge[1]));
		set1.getData().add(new XYChart.Data("Woman", averageAge[2]));

		set1.getData().add(new XYChart.Data("All", averageAge[0]));

		BarChart<?, ?> AverageAgeBarChart = new BarChart<String, Number>(xAxis, yAxis);
		AverageAgeBarChart.getData().addAll(set1);


		graphVbox.getChildren().clear();
		graphVbox.getChildren().add(AverageAgeBarChart);
	}

}
