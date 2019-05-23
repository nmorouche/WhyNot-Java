package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        home.getStylesheets().add("style.css");
        window.setScene(home);
        window.show();

    }

    public void createHomeScene(){
        BorderPane homeBorderPane = new BorderPane();
        createHomeBorder(window, homeBorderPane);
        home = new Scene(homeBorderPane, 1500, 900);
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
        HBox infoBox = addHBox();
        VBox navBox = addVBox(stage);
        borderPane.setTop(infoBox);
        borderPane.setLeft(navBox);
    }

    public void createStatisticsBorder(Stage primaryStage, BorderPane borderPane){
        HBox infoBox = addHBox();
        VBox navBox = addVBox(primaryStage);
        borderPane.setTop(infoBox);
        borderPane.setLeft(navBox);
    }

    public void createConnectionBorder(Stage primaryStage, BorderPane borderPane){
        HBox infoBox = addHBox();
        VBox navBox = addVBox(primaryStage);
        borderPane.setTop(infoBox);
        borderPane.setLeft(navBox);
    }

    public void createHomeBorder(Stage primaryStage, BorderPane borderPane){
        HBox infoBox = addHBox();
        VBox navBox = addVBox(primaryStage);
        VBox background = addBackground();
        borderPane.setTop(infoBox);
        borderPane.setLeft(navBox);
        borderPane.setCenter(background);
    }


    public HBox addHBox() {
        HBox hbox = new HBox();
        hbox.setId("infoBar");
        return hbox;
    }

    public VBox addVBox(Stage primaryStage) {
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
