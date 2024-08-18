package application.view;

import javafx.fxml.FXML;

public class WarningController {

	
	@FXML
	private void OK(){
		Warning.getStage().close();
	}
}
