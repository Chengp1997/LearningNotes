package mainInter;

import java.io.IOException;

import application.Main;
import application.WarnDialogController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * create the view of Manager.fxml
 * @author ·¢Ò¯
 *
 */
public class WarnDialogStage {
	/**
	 * the stage of manager
	 */
	private static Stage stage= new Stage();
	/**
	 * show the stage
	 * @param message use to pass it into controller
	 * @return the stage
	 */
	public static Stage getStage(String message){
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("WarnDialog.fxml"));
		try {
			AnchorPane loginrView = (AnchorPane) loader.load();
			WarnDialogController con = loader.getController();			
			Scene scene = new Scene(loginrView);	
			con.setMessage(message);
			con.setStage(stage);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();		
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return stage;		
	}
}
