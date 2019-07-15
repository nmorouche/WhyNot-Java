import com.mongodb.DBObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.math.BigInteger;
import java.security.*;

import java.io.*;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class ConnectionController implements Initializable {

	@FXML
	private Button connectionBtn;
	@FXML
	private Button settingsBtn;
	@FXML
	private TextField emailEdt;
	@FXML
	private PasswordField passwordField;

	public String dataBaseName;
	public String dataBaseurl;

	@Override
	public void initialize(URL location, ResourceBundle resources) {



		settingsBtn.setOnAction(actionEvent -> {
			try {
				changeSceneToSettingsViewSceneOnClickBtn(actionEvent);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		connectionBtn.setOnAction(actionEvent -> {
			try {
				changeSceneToGraphViewSceneOnClickBtn(actionEvent);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		});
	}

	private void changeSceneToSettingsViewSceneOnClickBtn(ActionEvent actionEvent) throws IOException {

		Parent parentScene = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/Settings.fxml")));


		Scene graphViewScene = new Scene(parentScene);

		Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

		window.setScene(graphViewScene);
		window.show();

	}


	public void changeSceneToGraphViewSceneOnClickBtn(ActionEvent event) throws IOException, NoSuchAlgorithmException {

		File file = new File("settings.txt");


		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			dataBaseurl = bufferedReader.readLine().split("=")[1];
			dataBaseName = bufferedReader.readLine().split("=")[1];


		} catch (IOException e) {
			e.printStackTrace();
		}

		Database database = new Database(dataBaseurl, dataBaseName);

		if (database.testConnection()) {
			String passwordForAdmin;
			Optional<DBObject> resultPasswordFromQuery;
			resultPasswordFromQuery = database.queryFromAdminEmail("admin", emailEdt.getText());

			Optional<DBObject> resultUsernameFromQuery;
			resultUsernameFromQuery = database.queryFromAdminEmail("admin", emailEdt.getText());
			if(resultUsernameFromQuery.isPresent()){
				FileWriter fw = new FileWriter("username.txt");
				fw.write(resultUsernameFromQuery.get().get("lastname").toString()+ "\n");
				fw.close();
			}

			if (resultPasswordFromQuery.isPresent()) {
				passwordForAdmin = resultPasswordFromQuery.get().get("password").toString();

					String hashPassword = MD5(passwordField.getText());

				if (hashPassword.equals(passwordForAdmin)) {
					Parent parentScene = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/GraphView.fxml")));

					Scene graphScene = new Scene(parentScene);

					Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

					window.setScene(graphScene);
					window.show();
				}else{
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("Alert");
					alert.setHeaderText("Connection");
					alert.setContentText("Error, Email or password incorrect");
					alert.show();
				}
			}else{
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("Alert");
				alert.setHeaderText("Connection");
				alert.setContentText("Error, Email or password incorrect");
				alert.show();
			}



		} else {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Alert");
			alert.setHeaderText("Connection to database");
			alert.setContentText("Error, check your informations");
			alert.show();
		}

	}
	public String MD5(String md5) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
		}
		return null;
	}

}
