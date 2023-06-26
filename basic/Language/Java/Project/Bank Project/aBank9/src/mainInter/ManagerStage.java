package mainInter;

import java.io.IOException;


import application.Main;
import application.ManagerController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * create the view of Manager.fxml
 * @author ·¢Ò¯
 *
 */
public class ManagerStage {
	/**
	 * the stage of manager
	 */
	private static Stage stage= new Stage();
	/**
	 * show the stage
	 * @param main use to pass it into controller
	 * @return the stage
	 */
	public static Stage getStage(Main main){
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("Manager.fxml"));
	    try {
			AnchorPane loginrView = (AnchorPane) loader.load();
			ManagerController con = loader.getController();
			con.setMain(main);
			Scene scene = new Scene(loginrView);
			stage.setScene(scene);
			stage.setTitle("Manager");
			stage.show();           
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return stage;		
	}
}
