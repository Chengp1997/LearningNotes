package application.view;

import java.io.IOException;

import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ClaimCenter {
	private static Stage primaryStage = new Stage();
	public static void showStage() {
		FXMLLoader loader = new FXMLLoader();	
		loader.setLocation(Main.class.getResource("view/ClaimCenter.fxml"));
		try {
			AnchorPane ClaimCenter = (AnchorPane) loader.load();
			Scene scene = new Scene(ClaimCenter);	
			primaryStage.setScene(scene);
			primaryStage.setTitle("ClaimCenter!");
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Stage getStage(){
		return primaryStage;
	}
}
