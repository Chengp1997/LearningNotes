package application.view;

import java.io.IOException;

import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class BasicInformation {
	private static Stage primaryStage = new Stage();
	public static void showStage() {
		FXMLLoader loader = new FXMLLoader();	
		loader.setLocation(Main.class.getResource("view/BasicInformation.fxml"));
		try {
			AnchorPane MainMenu = (AnchorPane) loader.load();
			Scene scene = new Scene(MainMenu);	
			primaryStage.setScene(scene);
			primaryStage.setTitle("BasicInformation!");
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
