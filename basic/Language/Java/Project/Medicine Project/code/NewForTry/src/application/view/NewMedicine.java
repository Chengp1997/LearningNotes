package application.view;

import java.io.IOException;

import application.Main;
import application.model.Medicine;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NewMedicine{
	private static Stage primaryStage = new Stage();
	public static void showStage(Medicine med) {
		FXMLLoader loader = new FXMLLoader();	
		loader.setLocation(Main.class.getResource("view/NewMedicine.fxml"));
		try {
			AnchorPane NewMedicine = (AnchorPane) loader.load();
			Scene scene = new Scene(NewMedicine);	
			primaryStage.setScene(scene);
			primaryStage.setTitle("NewMedicine!");

			NewMedicineController jump=loader.getController();
			
			jump.setMedicine(med);
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