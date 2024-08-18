package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import login.LoginStage;



public class Main extends Application {
	/**
	 * stage
	 */
	private Stage stage;
	/**
	 * The entrance of the program
	 */
	@Override
	public void start(Stage primaryStage) {
		stage = LoginStage.getStage(this);		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	public Stage getStage(){
		return stage;
	}
	public void setStage(Stage stage){
		this.stage = stage;
	}
}
