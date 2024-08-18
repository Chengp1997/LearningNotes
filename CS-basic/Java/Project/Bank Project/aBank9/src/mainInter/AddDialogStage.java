package mainInter;

import java.io.IOException;

import application.AddDialogStageController;
import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * create the view of AddDialog.fxml
 * @author ·¢Ò¯
 *
 */
public class AddDialogStage {
	/**
	 * the stage of addDialog
	 */
	private static Stage stage= new Stage();
	/**
	 * show the stage
	 * @param  i determine which customer to add
	 * @return the stage
	 */
	public static Stage getStage(int i){
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("AddDialog.fxml"));
	    try {
			AnchorPane loginrView = (AnchorPane) loader.load();
			Scene scene = new Scene(loginrView);
			stage.setScene(scene);
			stage.setTitle("Add");
			stage.show();           
			AddDialogStageController con = loader.getController();
			con.setStage(stage,i);
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return stage;		
	}
}
