package application.view;

import application.Data;
import application.model.MedicineProject;
import application.model.Work;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class NewWorkController {
	@FXML
	private TextField name;
	@FXML
	private TextField ID;
	@FXML
	private TextField address;
	@FXML
	private TextField postCode;
	@FXML
	private TextField phone;
	@FXML
	private ChoiceBox<String> workType;
	
	ObservableList<String> workTypeObservableList = FXCollections.observableArrayList("机关","灵活就业管理单位","企业","事业");
	private Work work=new Work();
	
	@FXML
	private void initialize() {
        workType.setItems(workTypeObservableList);
    }
	@FXML
	private void handleOK(){
		Data m=new Data();
		m.readWork();
		try{
			if (med.getName()!=null){
				int i=0;
				for (Work mp:m.workData){
					if (med.getName().equals(mp.getName())){
						m.workData.remove(i);
						m.writeWork();
						break;
					}
					i++;
				}
			}
		}
		catch(Exception e){
			
		
		}
		
		work.setName(new SimpleStringProperty(name.getText()));
		work.setID(new SimpleStringProperty(ID.getText()));
		work.setWorkType(new SimpleStringProperty(workType.getValue().toString()));
		work.setAddress(new SimpleStringProperty(address.getText()));
		work.setPostCode(new SimpleStringProperty(postCode.getText()));
		work.setPhone(new SimpleStringProperty(phone.getText()));
		System.out.println(work.getName());
		m.getWorkData().add(work);
		m.writeWork();
		NewWork.getStage().close();
		BasicInformation.showStage();
	}
	@FXML
	private void handleCancle(){
		NewWork.getStage().close();
	}
	private Work med;
	public void setWork(Work med) {
		this.med=med;
		try{
		if (med.getName()!=null){
		name.setText(med.getName());
		ID.setText(med.getID());
		workType.getValue();
		address.setText(med.getAddress());
		postCode.setText(med.getPostCode());
		phone.setText(med.getPhone());
		}
		}catch(Exception e){
			
		}
		
	}
}
