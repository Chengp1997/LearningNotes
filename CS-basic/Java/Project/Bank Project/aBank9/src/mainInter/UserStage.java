package mainInter;

import java.io.IOException;

import application.Main;
import application.UserController;
import data.Customer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * create the view of Manager.fxml
 * @author ·¢Ò¯
 *
 */
public class UserStage {
	/**
	 * the stage of manager
	 */
	private static Stage stage= new Stage();
	/**
	 * show the stage
	 * @param cus use to pass it into controller
	 * @return the stage
	 */
	public static Stage getStage(Customer cus){
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("User.fxml"));
		try {
			AnchorPane loginrView = (AnchorPane) loader.load();			
			UserController con = loader.getController();
			con.setCustomer(cus);			
			Scene scene = new Scene(loginrView);				
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();		
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return stage;		
	}
}
