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
			"西药", "中成药", "中草药","床位费","化验费","诊查费","检查费","护理费","特检费","输氧费","治疗费","输血费",
			"特治费","医疗服务费","手术费","麻药费","产前检查费","材料费","新生儿费","内置材料","其他费","监护床位费","非处方药","处方药",
			"甲类","乙类","丙类","化验费","诊查费","检查费","护理费","特检费","输氧费","治疗费","输血费","特治费","医疗服务费","手术费","麻药费","产前检查费"
			,"材料费","新生儿费","内置材料","其他费","监护床位费");
	ObservableList<String> chargeLevelObservableList=FXCollections.observableArrayList("甲类","乙类","丙类");
	ObservableList<String> approvalObservableList=FXCollections.observableArrayList("不需要审批","需要审批");
	ObservableList<String> hospitalLevelObservableList=FXCollections.observableArrayList("一级医院","二级医院","三级医院","社区医院","所有医院");
	
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
