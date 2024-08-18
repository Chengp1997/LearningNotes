package application.view;

import java.io.IOException;

import application.Main;
import application.model.Work;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NewWork {
	private static Stage primaryStage = new Stage();
	public static void showStage(Work med) {
		FXMLLoader loader = new FXMLLoader();	
		loader.setLocation(Main.class.getResource("view/NewWork.fxml"));
		try {
			AnchorPane NewWork = (AnchorPane) loader.load();
			Scene scene = new Scene(NewWork);	
			primaryStage.setScene(scene);
			primaryStage.setTitle("NewWork!");
            NewWorkController jump=loader.getController();
			jump.setWork(med);
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
