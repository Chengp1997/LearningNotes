package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;

import application.model.Medicine;
import application.view.Welcome;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class Main extends Application {

	 private static ObjectOutputStream output;
     private static ObjectInputStream input;
	 public  ObservableList<Medicine> medicineData= FXCollections.observableArrayList();
		
    @Override
	public void start(Stage primaryStage) {
		Welcome.showStage();
		
		
	}
	public static void main(String[] args) {
		launch(args);
		
	}
	

}