import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

	@FXML
	private Button exitToTitleScreen;
	@FXML
	private Button testSettingsBtn;

	@FXML
	private TextField url;
	@FXML
	private TextField databaseName;




	@Override
	public void initialize(URL location, ResourceBundle resources) {

		File file = new File("settings.txt");
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			url.setText(bufferedReader.readLine().split("=")[1]);
			databaseName.setText(bufferedReader.readLine().split("=")[1]);

		} catch (IOException e) {
			e.printStackTrace();
		}


		exitToTitleScreen.setOnAction(actionEvent -> {
			try {
				changeSceneToConnectionSceneOnClickBtn(actionEvent);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		testSettingsBtn.setOnAction(actionEvent -> testSettings());


	}

	private void testSettings(){
		String urlInSettings = url.getText();
		String databaseNameInSettings = databaseName.getText();

		Database database = new Database(urlInSettings, databaseNameInSettings);


		if (database.testConnection()){
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Confirmation");
			alert.setHeaderText("Connection to database");
			alert.setContentText("Success");
			alert.show();
		}else{
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Alert");
			alert.setHeaderText("Connection to database");
			alert.setContentText("Error, check your informations");
			alert.show();
		}

	}

	private void changeSceneToConnectionSceneOnClickBtn(ActionEvent actionEvent) throws IOException {


		FileWriter fw = new FileWriter("settings.txt");
		fw.write("url=" + url.getText() + "\n");
		fw.write("databaseName=" + databaseName.getText() + "\n");
		fw.close();


		Parent parentScene = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/Connection.fxml")));
		Scene connectionScene = new Scene(parentScene);

		Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
		window.setScene(connectionScene);
		window.show();


	}
}
