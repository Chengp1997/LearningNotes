package application.view;

import java.io.IOException;

import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Warning {
	private static Stage primaryStage = new Stage();
	public static void showStage() {
		FXMLLoader loader = new FXMLLoader();	
		loader.setLocation(Main.class.getResource("view/Warning.fxml"));
		try {
			AnchorPane Warning = (AnchorPane) loader.load();
			Scene scene = new Scene(Warning);	
			primaryStage.setScene(scene);
			primaryStage.setTitle("Warning!");
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
