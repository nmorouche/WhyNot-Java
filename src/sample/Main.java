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

    Scene home, settings, statistics, connction;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        BorderPane homeBorderPane = new BorderPane();
        createBorder(primaryStage, homeBorderPane);
        home = new Scene(homeBorderPane, 1500, 900);
        createHomeScene(primaryStage, home);


        BorderPane settignsBorderPane = new BorderPane();
        createBorder(primaryStage, settignsBorderPane);
        settings = new Scene(settignsBorderPane, 1500, 900);


        BorderPane statisticsBorderPane = new BorderPane();
        createBorder(primaryStage, statisticsBorderPane);
        statistics = new Scene(statisticsBorderPane, 1500, 900);

        BorderPane connctionBorderPane = new BorderPane();
        createBorder(primaryStage, connctionBorderPane);
        connction = new Scene(connctionBorderPane, 1500, 900);
    }

    public void createBorder(Stage primaryStage, BorderPane borderPane){
        HBox infoBox = addHBox();
        VBox navBox = addVBox(primaryStage);
        VBox background = addBackground();
        borderPane.setTop(infoBox);
        borderPane.setLeft(navBox);
        borderPane.setCenter(background);
    }

    public void createHomeScene(Stage primaryStage, Scene scene){
        primaryStage.getIcons().add(new Image("file:icon.png"));
        primaryStage.setTitle("WhyNot - Interface d'administration");
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
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
        connectionBtn.setOnAction(e -> primaryStage.setScene(connction));
        Button statisticsBtn = new Button("Statistics");
        connectionBtn.setOnAction(e -> primaryStage.setScene(statistics));
        Button settingsBtn = new Button("Settings");
        connectionBtn.setOnAction(e -> primaryStage.setScene(settings));
        vbox.getChildren().addAll(connectionBtn, statisticsBtn, settingsBtn);

        return vbox;
    }
    public VBox addBackground(){
        VBox vbox = new VBox();
        vbox.setId("background");
        return vbox;
    }
}
