package application.view;

import java.io.IOException;

import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Welcome {
	private static Stage primaryStage = new Stage();
	public static void showStage() {
		FXMLLoader loader = new FXMLLoader();	
		loader.setLocation(Main.class.getResource("view/Welcome.fxml"));
		try {
			AnchorPane Welcome = (AnchorPane) loader.load();
			Scene scene = new Scene(Welcome);	
			primaryStage.setScene(scene);
			primaryStage.setTitle("Welcome!");
			primaryStage.setResizable(false);
			primaryStage.show();
			//WelcomeController jump = loader.getController();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Stage getStage(){
		return primaryStage;
	}
}
