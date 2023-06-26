package application.view;

import java.io.IOException;

import application.Main;
import application.model.Person;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NewPerson {
	private static Stage primaryStage = new Stage();
	public static void showStage(Person person) {
		FXMLLoader loader = new FXMLLoader();	
		loader.setLocation(Main.class.getResource("view/NewPerson.fxml"));
		try {
			AnchorPane NewPerson = (AnchorPane) loader.load();
			Scene scene = new Scene(NewPerson);	
			primaryStage.setScene(scene);
			primaryStage.setTitle("NewPerson!");
			primaryStage.setResizable(false);
            NewPersonController jump=loader.getController();
			jump.setPerson(person);
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
