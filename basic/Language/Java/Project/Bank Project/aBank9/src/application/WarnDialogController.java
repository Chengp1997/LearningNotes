package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class WarnDialogController {
	/**
	 * show the error in dialog
	 */
	@FXML
	private Label message;
	/**
	 * use to close the stage
	 */
	private Stage stage;
	/**
	 * use to close the stage
	 */
	@FXML
	private void close(){
		stage.close();
	}
	/**
	 * initialize the stage
	 * @param stage passed by WarnDialogStage
	 */
	public void setStage(Stage stage){
		this.stage = stage;
	}
	/**
	 * display the error message
	 * @param message passed by WarnDialogStage
	 */
	public void setMessage(String message){
		this.message.setText(message);
	}
}
