package application.view;

import application.Main;
import javafx.fxml.FXML;

public class MainMenuController {

	@SuppressWarnings("unused")
	private Main mainApp;
	
	@FXML
	private void medicalInformation(){
		MedicalInformation.showStage();
	}
	
	@FXML
	private void basicInformation(){
		BasicInformation.showStage();
	}
	 public void setMainApp(Main mainApp) {
	        this.mainApp = mainApp;

	    }
	 @FXML
	 private void Exit(){
		 MainMenu.getStage().close();
	 }
	 @FXML
	 private void ClaimCenter(){
		 ClaimCenter.showStage();
	 }
}