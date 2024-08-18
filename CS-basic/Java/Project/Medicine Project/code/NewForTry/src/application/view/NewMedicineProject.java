package application.view;

import java.io.IOException;

import application.Main;
import application.model.MedicineProject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NewMedicineProject{
	private static Stage primaryStage = new Stage();
	public static void showStage(MedicineProject medp) {
		FXMLLoader loader = new FXMLLoader();	
		loader.setLocation(Main.class.getResource("view/NewMedicalProject.fxml"));
		try {
			AnchorPane NewMedicalProject = (AnchorPane) loader.load();
			Scene scene = new Scene(NewMedicalProject);	
			primaryStage.setScene(scene);
			primaryStage.setTitle("NewMedicalProject!");
			primaryStage.setResizable(false);
			NewMedicineProjectController jump=loader.getController();
			
			jump.setMedicineProject(medp);
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