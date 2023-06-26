package application.view;

import application.Data;
import application.Main;
import application.model.Disease;
import application.model.Services;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class NewDiseaseController {
	private Main mainApp;
	@FXML
	private TextField name;
	@FXML
	private TextField ID;
	@FXML
	private TextField sign;
	@FXML
	private TextField notes;
	@FXML
	private ChoiceBox<String> Type;
	
	ObservableList<String> typeObservableList = FXCollections.observableArrayList("��Ѫ��ϵͳ����","����ϵͳ����","��л�ڷ��ڼ���","��Ѫϵͳ����","���༲��","��ϵͳ","����ϵͳ����","��Ⱦ��","����ϵͳ����","��������","����","�м�","����ϵͳ","����");

	private Disease disease=new Disease();
	@FXML
    private void initialize() {
        Type.setItems(typeObservableList);
    }
	
	@FXML
	private void handleOK(){
		Data m=new Data();
		m.readDisease();
		try{
			if (med.getName()!=null){
				int i=0;
				for (Disease mp:m.diseaseData){
					if (med.getName().equals(mp.getName())){
						m.diseaseData.remove(i);
						m.writeDisease();
						break;
					}
					i++;
				}
			}
		}
		catch(Exception e){
			
		
		}
		disease.setName(new SimpleStringProperty(name.getText()));
		disease.setID(new SimpleStringProperty(ID.getText()));
		disease.setType(new SimpleStringProperty(Type.getValue().toString()));
		disease.setNotes(new SimpleStringProperty(notes.getText()));
		disease.setSign(new SimpleStringProperty(sign.getText()));
		m.getDiseaseData().add(disease);
		m.writeDisease();
		NewDisease.getStage().close();
		MedicalInformation.showStage();
	}
	@FXML
	private void handleCancle(){
		NewDisease.getStage().close();
	}
	private Disease med;
		public void setDisease(Disease med){
			this.med=med;
			try{
			if (med.getName()!=null){
			name.setText(med.getName());
			ID.setText(med.getID());
			Type.getValue();
			notes.setText(med.getNotes());
			sign.setText(med.getSign());
			}
			}catch(Exception e){
				
			}
		}
}
