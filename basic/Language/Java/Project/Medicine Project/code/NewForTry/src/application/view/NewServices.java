package application.view;

import java.io.IOException;

import application.Main;
import application.model.Services;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NewServices {
	private static Stage primaryStage = new Stage();

	public static void showStage(Services services) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/NewServices.fxml"));
		try {
			AnchorPane NewServices = (AnchorPane) loader.load();
			Scene scene = new Scene(NewServices);
			primaryStage.setScene(scene);
			primaryStage.setTitle("NewServices!");
			NewServicesController jump = loader.getController();
			jump.setServices(services);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Stage getStage() {
		return primaryStage;
	}
}
