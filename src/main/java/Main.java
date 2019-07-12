import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application{

	private AnchorPane rootLayout;


	public static void main (String[] args){
		launch(args);
	}


	@Override
	public void start(Stage primaryStage) throws Exception {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("fxml/Connection.fxml"));
		rootLayout = loader.load();

		ConnectionController controller = loader.getController();

		Scene scene = new Scene(rootLayout);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

}
