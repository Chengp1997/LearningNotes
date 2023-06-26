package login;

import java.io.IOException;

import application.LoginController;
import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * create the view of Login.fxml
 * @author ·¢Ò¯
 *
 */
public class LoginStage {
	/**
	 * the stage of Login 
	 */
	private static Stage stage= new Stage();
	/**
	 * show the stage
	 * @param main use to pass it into controller
	 * @return the stage
	 */
	public static Stage getStage(Main main){
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("Login.fxml"));
	    try {
			AnchorPane loginrView = (AnchorPane) loader.load();
			LoginController con = loader.getController();
			con.setMain(main);
			Scene scene = new Scene(loginrView);
			stage.setScene(scene);
			stage.setTitle("login");
			stage.show();           
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return stage;		
	}
}
