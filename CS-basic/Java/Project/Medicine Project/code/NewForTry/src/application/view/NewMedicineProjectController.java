package application.view;

import application.Data;
import application.model.MedicineProject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class NewMedicineProjectController {
	@FXML
	private TextField name;
	@FXML
	private TextField ID;
	@FXML
	private TextField scopeOfUse;
	@FXML
	private TextField unit;
	@FXML
	private TextField factory;
	@FXML
	private TextField notes;
	@FXML
	private ChoiceBox<String> chargeType;
	@FXML
	private ChoiceBox<String> chargeLevel;
	@FXML
	private ChoiceBox<String> approval;
	@FXML
	private ChoiceBox<String> hospitalLevel;
	
	ObservableList<String> chargeTypeObservableList = FXCollections.observableArrayList(
			"��ҩ", "�г�ҩ", "�в�ҩ","��λ��","�����","����","����","�����","�ؼ��","������","���Ʒ�","��Ѫ��",
			"���η�","ҽ�Ʒ����","������","��ҩ��","��ǰ����","���Ϸ�","��������","���ò���","������","�໤��λ��","�Ǵ���ҩ","����ҩ",
			"����","����","����","�����","����","����","�����","�ؼ��","������","���Ʒ�","��Ѫ��","���η�","ҽ�Ʒ����","������","��ҩ��","��ǰ����"
			,"���Ϸ�","��������","���ò���","������","�໤��λ��");
	ObservableList<String> chargeLevelObservableList=FXCollections.observableArrayList("����","����","����");
	ObservableList<String> approvalObservableList=FXCollections.observableArrayList("����Ҫ����","��Ҫ����");
	ObservableList<String> hospitalLevelObservableList=FXCollections.observableArrayList("һ��ҽԺ","����ҽԺ","����ҽԺ","����ҽԺ","����ҽԺ");
	
	private MedicineProject medicineproject=new MedicineProject();
	@FXML
    private void initialize() {
        chargeType.setItems(chargeTypeObservableList);
        chargeLevel.setItems(chargeLevelObservableList);
        approval.setItems(approvalObservableList);
        hospitalLevel.setItems(hospitalLevelObservableList);
    }


	@FXML
	private void handleOK(){
		Data m=new Data();
		m.readMedicineProject();
		try{
			if (med.getName()!=null){
				int i=0;
				for (MedicineProject mp:m.medicineProjectData){
					if (med.getName().equals(mp.getName())){
						System.out.println(mp.getName()+" "+med.getName());
						m.medicineProjectData.remove(i);
						m.writeMedcineProject();
						break;
					}
					i++;
				}
			}
		}
		catch(Exception e){
			
		
		}
		medicineproject.setName(new SimpleStringProperty(name.getText()));
		medicineproject.setID(new SimpleStringProperty(ID.getText()));
		medicineproject.setChargeType(new SimpleStringProperty(chargeType.getValue().toString()));
		medicineproject.setUnit(new SimpleStringProperty(unit.getText()));
		medicineproject.setChargeLevel(new SimpleStringProperty(chargeLevel.getValue().toString()));
		medicineproject.setFactory(new SimpleStringProperty(factory.getText()));
		medicineproject.setApproval(new SimpleStringProperty(approval.getValue().toString()));
		medicineproject.setHospitalLevel(new SimpleStringProperty(hospitalLevel.getValue().toString()));
		medicineproject.setNotes(new SimpleStringProperty(notes.getText()));
		medicineproject.setScopeOfUse(new SimpleStringProperty(scopeOfUse.getText()));
		System.out.println(medicineproject.getName());
		m.getMedicineProjectData().add(medicineproject);
		m.writeMedcineProject();
		NewMedicineProject.getStage().close();
		MedicalInformation.showStage();
	}
	
	private MedicineProject med;
	public void setMedicineProject(MedicineProject med){
		this.med=med;
		try{
		if (med.getName()!=null){
		name.setText(med.getName());
		ID.setText(med.getID());
		chargeType.getValue();
		unit.setText(med.getUnit());
		chargeLevel.getValue();
		factory.setText(med.getFactory());
		approval.getValue();
		hospitalLevel.getValue();
		notes.setText(med.getNotes());
		scopeOfUse.setText(med.getScopeOfUse());}
		}catch(Exception e){
			
		}
	}
	@FXML
	private void handleCancle(){
		NewMedicineProject.getStage().close();
	}
	
}
