import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

	private Scene home, settings, statistics, connection;
	private Stage window;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage){
		window = primaryStage;

		//Création Scene settings
		createSettingsScene();

		//Création Scene statistics
		createStatisticsScene();

		//Création Scene connection
		createConnectionScene();

		//Création Scene home
		createHomeScene();

		window.getIcons().add(new Image("/images/icon.png"));
		window.setTitle("WhyNot - Interface d'administration");

		window.setScene(home);
		window.show();

	}

	private void createHomeScene(){
		BorderPane homeBorderPane = new BorderPane();
		createHomeBorder(homeBorderPane);
		home = new Scene(homeBorderPane, 1500, 900);
		home.getStylesheets().add("/css/style.css");
	}

	private void createHomeBorder(BorderPane borderPane){
		HBox infoBox = addInfoHBox();
		VBox navBox = addNavVBox();
		VBox background = addBackground();
		borderPane.setTop(infoBox);
		borderPane.setLeft(navBox);
		borderPane.setCenter(background);
	}

	private void createSettingsScene() {
		BorderPane settingsBorderPane = new BorderPane();
		createSettingsBorder(settingsBorderPane);
		settings = new Scene(settingsBorderPane, 1500, 900);
		settings.getStylesheets().add("/css/style.css");
	}

	private void createSettingsBorder(BorderPane borderPane){
		HBox chartsBox = addInfoHBox();
		VBox navBox = addNavVBox();
		VBox settingsVbox = addSettingsVbox();
		borderPane.setTop(chartsBox);
		borderPane.setLeft(navBox);
		borderPane.setCenter(settingsVbox);
	}

	private VBox addSettingsVbox(){
		VBox vbox = new VBox();

		Label databaseUsernameLabel = new Label("DataBase username :");
		TextField databaseUsernameTextField = new TextField();

		Label databasePasswordLabel = new Label("Database username password :");
		TextField databasePasswordTextField = new TextField();

		Label databaseNameLabel = new Label("DataBase Name :");
		TextField databaseNameTextField = new TextField();

		Label databaseUrlLabel = new Label("URL :");
		TextField databaseUrlTextField = new TextField();

		Label databasePortLabel = new Label("Port :");
		TextField  databasePortTextField = new TextField();

		Button validateSettingsButton = new Button("Test Connection");

		vbox.getChildren().addAll(databaseUsernameLabel, databaseUsernameTextField, databasePasswordLabel, databasePasswordTextField, databaseNameLabel, databaseNameTextField, databaseUrlLabel, databaseUrlTextField, databasePortLabel, databasePortTextField, validateSettingsButton);
		validateSettingsButton.setOnAction(e -> TestConnection(databaseUsernameTextField.getText(), databasePasswordTextField.getText(),databaseNameTextField.getText(), databaseUrlTextField.getText(), databasePortTextField.getText()));

		return vbox;
	}

	private void TestConnection(String databaseUsernameTextField, String databasePasswordTextField,String databaseNameTextField,String databaseUrlTextField, String databasePortTextField){
		Database database = new Database(databaseUsernameTextField, databasePasswordTextField, databaseNameTextField, databaseUrlTextField, databasePortTextField);
		if(database.dbConnection(database) == null){
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Alerte");
			alert.setHeaderText("Connexion a la base de donnée");
			alert.setContentText("Erreur, verifié vos infos");
			alert.show();
		}else{
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Confirmation");
			alert.setHeaderText("Connexion a la base de donnée");
			alert.setContentText("Réussis");
			alert.show();
		}

	}

	private void createStatisticsScene() {
		BorderPane statisticsBorderPane = new BorderPane();
		createStatisticsBorder(statisticsBorderPane);
		statistics = new Scene(statisticsBorderPane, 1500, 900);
		statistics.getStylesheets().add("/css/style.css");

	}

	private void createStatisticsBorder(BorderPane borderPane){
		VBox graphVBox = new VBox();
		HBox graphHBox = addGraphHBox(graphVBox);
		VBox navBox = addNavVBox();
		borderPane.setTop(graphHBox);
		borderPane.setLeft(navBox);
		borderPane.setCenter(graphVBox);
	}

	private HBox addGraphHBox(VBox graphVBox){
		HBox hbox = new HBox();
		hbox.setId("infoBar");
		//Button parity = new Button("Parity");
		//Button parity = new Button("Parity");
		Button connectionTime = new Button("Connection Time");
		connectionTime.setOnAction(e -> ConnectionTimeCharts(graphVBox));
		Button parity = new Button("Parity");
		parity.setOnAction(e -> ParityPieCharts(graphVBox));
		hbox.getChildren().addAll(parity, connectionTime);
		return hbox;
	}

	private void createConnectionScene() {
		BorderPane connectionBorderPane = new BorderPane();
		createConnectionBorder(connectionBorderPane);
		connection = new Scene(connectionBorderPane, 1500, 900);
		connection.getStylesheets().add("/css/style.css");
	}

	private void createConnectionBorder(BorderPane borderPane){
		HBox infoBox = addInfoHBox();
		VBox navBox = addNavVBox();
		VBox connectionVBox = addConnectionVBox();
		borderPane.setTop(infoBox);
		borderPane.setLeft(navBox);
		borderPane.setCenter(connectionVBox);
	}

	private VBox addConnectionVBox() {
		VBox vbox = new VBox();
		vbox.setId("connectionBox");

		Label usernameLabel = new Label("Username :");
		TextField usernameTextField = new TextField();

		Label passwordLabel = new Label("Password :");
		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("Your password");

		Button connectionBtn = new Button("Connection");
		//connectionBtn.setOnAction(e -> connectToDatabase());
		vbox.getChildren().addAll(usernameLabel, usernameTextField, passwordLabel, passwordField, connectionBtn);
		return vbox;
	}

	private HBox addInfoHBox() {
		HBox hbox = new HBox();
		hbox.setId("infoBar");
		return hbox;
	}

	private VBox addNavVBox() {
		VBox vbox = new VBox();
		vbox.setId("navBar");

		Button connectionBtn = new Button("Connection");
		connectionBtn.setOnAction(e -> window.setScene(connection));

		Button statisticsBtn = new Button("Statistics");
		statisticsBtn.setOnAction(e -> window.setScene(statistics));

		Button settingsBtn = new Button("Settings");
		settingsBtn.setOnAction(e -> window.setScene(settings));

		vbox.getChildren().addAll(connectionBtn, statisticsBtn, settingsBtn);

		return vbox;
	}
	private VBox addBackground(){
		VBox vbox = new VBox();
		vbox.setId("background");
		return vbox;
	}

	private void ParityPieCharts(VBox graphVBox){

		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				new PieChart.Data("Homme", 54),
				new PieChart.Data("Femme", 46));
		final PieChart chart = new PieChart(pieChartData);
		chart.setTitle("Répartition du nombres d'homme et de femme sur WhyNot");

		graphVBox.getChildren().clear();
		graphVBox.getChildren().add(chart);

	}

	private void ConnectionTimeCharts(VBox graphVBox){
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				new PieChart.Data("TEST", 150),
				new PieChart.Data("Femme", 50));
		final PieChart chart = new PieChart(pieChartData);
		chart.setTitle("Connection Time");

		graphVBox.getChildren().clear();
		graphVBox.getChildren().add(chart);

	}
}
