package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {

    Scene home, settings, statistics, connection;
    Stage window;

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

        window.getIcons().add(new Image("file:icon.png"));
        window.setTitle("WhyNot - Interface d'administration");

        window.setScene(home);
        window.show();

    }

    public void createHomeScene(){
        BorderPane homeBorderPane = new BorderPane();
        createHomeBorder(window, homeBorderPane);
        home = new Scene(homeBorderPane, 1500, 900);
        home.getStylesheets().add("style.css");
    }

    private void createSettingsScene() {
        BorderPane settingsBorderPane = new BorderPane();
        createSettingsBorder(window, settingsBorderPane);
        settings = new Scene(settingsBorderPane, 1500, 900);
        settings.getStylesheets().add("style.css");
    }

    private void createStatisticsScene() {
        BorderPane statisticsBorderPane = new BorderPane();
        createStatisticsBorder(window, statisticsBorderPane);
        statistics = new Scene(statisticsBorderPane, 1500, 900);
        statistics.getStylesheets().add("style.css");

    }

    private void createConnectionScene() {
        BorderPane connectionBorderPane = new BorderPane();
        createConnectionBorder(window, connectionBorderPane);
        connection = new Scene(connectionBorderPane, 1500, 900);
        connection.getStylesheets().add("style.css");
    }

    public void createSettingsBorder(Stage stage, BorderPane borderPane){
        HBox infoBox = addInfoHBox();
        VBox navBox = addNavVBox(stage);
        VBox settingsVbox = addSettingsVbox();
        borderPane.setTop(infoBox);
        borderPane.setLeft(navBox);
        borderPane.setCenter(settingsVbox);
    }

    public VBox addSettingsVbox(){
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

        vbox.getChildren().addAll(databaseUsernameLabel, databaseUsernameTextField, databasePasswordLabel, databasePasswordTextField, databaseNameLabel, databaseNameTextField, databaseUrlLabel, databaseUrlTextField, databasePortLabel, databasePortTextField);


        return vbox;
    }

    public void createStatisticsBorder(Stage primaryStage, BorderPane borderPane){
        HBox infoBox = addInfoHBox();
        VBox navBox = addNavVBox(primaryStage);
        borderPane.setTop(infoBox);
        borderPane.setLeft(navBox);
    }

    public void createConnectionBorder(Stage primaryStage, BorderPane borderPane){
        HBox infoBox = addInfoHBox();
        VBox navBox = addNavVBox(primaryStage);
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

    public void createHomeBorder(Stage primaryStage, BorderPane borderPane){
        HBox infoBox = addInfoHBox();
        VBox navBox = addNavVBox(primaryStage);
        VBox background = addBackground();
        borderPane.setTop(infoBox);
        borderPane.setLeft(navBox);
        borderPane.setCenter(background);
    }


    public HBox addInfoHBox() {
        HBox hbox = new HBox();
        hbox.setId("infoBar");
        return hbox;
    }

    public VBox addNavVBox(Stage primaryStage) {
        VBox vbox = new VBox();
        vbox.setId("navBar");

        Button connectionBtn = new Button("Connection");
        connectionBtn.setOnAction(e -> primaryStage.setScene(connection));

        Button statisticsBtn = new Button("Statistics");
        statisticsBtn.setOnAction(e -> primaryStage.setScene(statistics));

        Button settingsBtn = new Button("Settings");
        settingsBtn.setOnAction(e -> primaryStage.setScene(settings));

        vbox.getChildren().addAll(connectionBtn, statisticsBtn, settingsBtn);

        return vbox;
    }
    public VBox addBackground(){
        VBox vbox = new VBox();
        vbox.setId("background");
        return vbox;
    }
}
