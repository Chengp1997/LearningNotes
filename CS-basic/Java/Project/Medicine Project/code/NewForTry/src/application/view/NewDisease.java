package application.view;

import java.io.IOException;

import application.Main;
import application.model.Disease;
import application.model.Medicine;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NewDisease {
	private static Stage primaryStage = new Stage();
	public static void showStage(Disease med) {
		FXMLLoader loader = new FXMLLoader();	
		loader.setLocation(Main.class.getResource("view/NewDisease.fxml"));
		try {
			AnchorPane NewDisease = (AnchorPane) loader.load();
			Scene scene = new Scene(NewDisease);	
			primaryStage.setScene(scene);
			primaryStage.setTitle("Disease!");
			NewDiseaseController jump=loader.getController();
			jump.setDisease(med);
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
