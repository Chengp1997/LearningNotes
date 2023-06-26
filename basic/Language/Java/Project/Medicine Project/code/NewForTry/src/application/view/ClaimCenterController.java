package application.view;

import application.Data;
import application.model.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ClaimCenterController {

	@FXML private Label name;
	@FXML private Label sex;
	@FXML private Label Type;
	@FXML private Label unitName;
	@FXML private Label date;
	@FXML private Label times;
	@FXML private Label medicalTimes;
	@FXML private Label cardID;
	@FXML private Label birday;
	@FXML private Label unitID;
	@FXML private Label hospitalTimes;
	@FXML private Label judge;
	@FXML private Label fee;
	@FXML private Label total;
	@FXML private TextField search;
	
	Data p;
	@FXML
	private void initialize(){
		showDetails(null);
		p=new Data();
		p.readPerson();
	}
	@FXML
	private void search(){
		for (Person med : p.getPersonData()) {
			if (med.getName().equals(search.getText()) || med.getID().equals(search.getText())) {
				showDetails(med);
			}else{
					NotFound.showStage();
				
			}
		}
	}
	
	private void showDetails(Person person){
		if(person!=null){
			name.setText(name.getText());
			sex.setText(sex.getText());
			Type.setText(Type.getText());
			unitName.setText(unitName.getText());
			date.setText(date.getText());
			times.setText(times.getText());
			medicalTimes.setText(medicalTimes.getText());
			cardID.setText(cardID.getText());
			birday.setText(birday.getText());
			unitID.setText(unitID.getText());
			hospitalTimes.setText(hospitalTimes.getText());
			judge.setText(judge.getText());
			fee.setText(fee.getText());
			total.setText(total.getText());
			
		}else{
			name.setText("");
			sex.setText("");
			Type.setText("");
			unitName.setText("");
			date.setText("");
			times.setText("");
			medicalTimes.setText("");
			cardID.setText("");
			birday.setText("");
			unitID.setText("");
			hospitalTimes.setText("");
			judge.setText("");
			fee.setText("");
			total.setText("");
		}
	}
	@FXML
	private void back(){
		ClaimCenter.getStage().close();
	}
}
