package application.view;

import com.sun.javafx.scene.control.behavior.PasswordFieldBehavior;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class WelcomeController {
	@FXML TextField name;
	@FXML PasswordField passWord;

	@SuppressWarnings("unused")
	private Main mainApp;
	@FXML
	private void welcome(){
		if(passWord.getText().equals("12345")&&name.getText().equals("12345")){
		Welcome.getStage().close();
		MainMenu.showStage();
		}else{
			Warning.showStage();
		}
	}

	 public void setMainApp(Main mainApp) {
	        this.mainApp = mainApp;

	    }
}
