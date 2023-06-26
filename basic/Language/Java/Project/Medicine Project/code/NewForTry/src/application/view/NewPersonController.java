package application.view;

import application.Data;
import application.model.Person;
import application.model.Work;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class NewPersonController {
	@FXML
	private TextField name1;
	@FXML
	private TextField ID1;
	@FXML
	private TextField sex;
	@FXML
	private TextField medicalCode;
	@FXML
	private TextField cardID;
	@FXML
	private ChoiceBox<String> certificateType;
	@FXML
	private TextField certificateCode;
	@FXML
	private TextField workCode;
	@FXML
	private TextField date;

	ObservableList<String> certificateTypeObservableList = FXCollections.observableArrayList("澳门特区护照/身份证明","居民身份证","台湾居民来往大陆通行证","外国人护照","香港特区护照/身份证明","中国人民解放军军官证","中国人民武装警察警官证");
	private Person person=new Person();
	@FXML
    private void initialize() {
		certificateType.setItems(certificateTypeObservableList);
    }
	@FXML
	private void handleOK(){
		Data m=new Data();
		m.readPerson();
		try{
			if (med.getName()!=null){
				int i=0;
				for (Person mp:m.personData){
					if (med.getName().equals(mp.getName())){
						m.personData.remove(i);
						m.writePerson();
						break;
					}
					i++;
				}
			}
		}
		catch(Exception e){
			
		
		}
		person.setName(new SimpleStringProperty(name1.getText()));
		person.setID(new SimpleStringProperty(ID1.getText()));
		person.setSex(new SimpleStringProperty(sex.getText()));
		person.setMedicalCode(new SimpleStringProperty(medicalCode.getText()));
		person.setCardID(new SimpleStringProperty(cardID.getText()));
		person.setCertificateType(new SimpleStringProperty(certificateType.getValue().toString()));
		person.setCertificateCode(new SimpleStringProperty(certificateCode.getText()));
		person.setWorkCode(new SimpleStringProperty(workCode.getText()));
		person.setDate(new SimpleStringProperty(date.getText()));
		System.out.println(person.getName());
		m.getPersonData().add(person);
		m.writePerson();
		NewPerson.getStage().close();
		BasicInformation.showStage();
	}
	@FXML
	private void handleCancle(){
		NewPerson.getStage().close();
	}
	private Person med;
	public void setPerson(Person med) {
		this.med=med;
		try{
		if (med.getName()!=null){
		name1.setText(med.getName());
		ID1.setText(med.getID());
		certificateType.getValue();
		sex.setText(med.getSex());
		medicalCode.setText(med.getMedicalCode());
		cardID.setText(med.getCardID());
		certificateCode.setText(med.getertificateCode());
		workCode.setText(med.getWorkCode());
		date.setText(med.getDate());
		}
		}catch(Exception e){
			
		}
		
	}

}
