package application.view;

import application.Data;
import application.Main;
import application.model.Medicine;
import application.model.MedicineProject;
import application.model.Work;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class NewMedicineController {

	@SuppressWarnings("unused")
	private Main mainApp;
	@FXML
	private TextField name;
	@FXML
	private TextField ID;
	@FXML
	private TextField EnglishName;
	@FXML
	private TextField medicineUnit;
	@FXML
	private TextField limitPrice;
	@FXML
	private TextField formOfDrug;
	@FXML
	private TextField scopeOfUse;
	@FXML
	private TextField rate;
	@FXML
	private TextField usage;
	@FXML
	private TextField unit;
	@FXML
	private TextField size;
	@FXML
	private TextField limitsOfDay;
	@FXML
	private TextField tradingName;
	@FXML
	private TextField factory;
	@FXML
	private TextField OTC;
	@FXML
	private TextField notes;
	@FXML
	private TextField countryListOfCode;
	@FXML
	private TextField origin;
	@FXML
	private ChoiceBox<String> chargeType;
	@FXML
	private ChoiceBox<String> sign;
	@FXML
	private ChoiceBox<String> chargeLevel;
	@FXML
	private ChoiceBox<String> hospitalSign;
	@FXML
	private ChoiceBox<String> approval;
	@FXML
	private ChoiceBox<String> hospitalLevel;
	
	ObservableList<String> chargeTypeObservableList = FXCollections.observableArrayList(
			"��ҩ", "�г�ҩ", "�в�ҩ","��λ��","�����","����","����","�����","�ؼ��","������","���Ʒ�","��Ѫ��",
			"���η�","ҽ�Ʒ����","������","��ҩ��","��ǰ����","���Ϸ�","��������","���ò���","������","�໤��λ��","�Ǵ���ҩ","����ҩ",
			"����","����","����","�����","����","����","�����","�ؼ��","������","���Ʒ�","��Ѫ��","���η�","ҽ�Ʒ����","������","��ҩ��","��ǰ����"
			,"���Ϸ�","��������","���ò���","������","�໤��λ��");

	ObservableList<String> signObservableList=FXCollections.observableArrayList("�Ǵ���ҩ","����ҩ");
	ObservableList<String> chargeLevelObservableList=FXCollections.observableArrayList("����","����","����");
	ObservableList<String> hospitalSignObservableList=FXCollections.observableArrayList("��Ժ���Ƽ�","Ժ���Ƽ�");
	ObservableList<String> approvalObservableList=FXCollections.observableArrayList("����Ҫ����","��Ҫ����");
	ObservableList<String> hospitalLevelObservableList=FXCollections.observableArrayList("һ��ҽԺ","����ҽԺ","����ҽԺ","����ҽԺ","����ҽԺ");
	
	private Medicine medicine=new Medicine();
	@FXML
    private void initialize() {
        chargeType.setItems(chargeTypeObservableList);
        sign.setItems(signObservableList);
        chargeLevel.setItems(chargeLevelObservableList);
        hospitalSign.setItems(hospitalSignObservableList);
        approval.setItems(approvalObservableList);
        hospitalLevel.setItems(hospitalLevelObservableList);
    }


	@FXML
	private void handleOK(){
		Data m=new Data();
		m.readMedicine();
		try{
			if (med.getName()!=null){
				int i=0;
				for (Medicine mp:m.medicineData){
					if (med.getName().equals(mp.getName())){
						m.medicineData.remove(i);
						m.writeMedicine();
						break;
					}
					i++;
				}
			}
		}
		catch(Exception e){
			
		
		}
		medicine.setName(new SimpleStringProperty(name.getText()));
		medicine.setID(new SimpleStringProperty(ID.getText()));
		medicine.setEnglishName(new SimpleStringProperty(EnglishName.getText()));
		medicine.setRate(new SimpleStringProperty(rate.getText()));
		medicine.setChargeType(new SimpleStringProperty(chargeType.getValue().toString()));
		medicine.setUsage(new SimpleStringProperty(usage.getText()));
		medicine.setSign(new SimpleStringProperty(sign.getValue().toString()));
		medicine.setUnit(new SimpleStringProperty(unit.getText()));
		medicine.setChargeLevel(new SimpleStringProperty(chargeLevel.getValue().toString()));
		medicine.setSize(new SimpleStringProperty(size.getText()));
		medicine.setMedicineUnit(new SimpleStringProperty(medicineUnit.getText()));
		medicine.setLimitDays(new SimpleStringProperty(limitsOfDay.getText()));
		medicine.setLimitPrice(new SimpleStringProperty(limitPrice.getText()));
		medicine.setTradingName(new SimpleStringProperty(tradingName.getText()));
		medicine.sethospitalSign(new SimpleStringProperty(hospitalSign.getValue().toString()));
		medicine.setFactory(new SimpleStringProperty(factory.getText()));
		medicine.setApproval(new SimpleStringProperty(approval.getValue().toString()));
		medicine.setOTC(new SimpleStringProperty(OTC.getText()));
		medicine.setHospitalLevel(new SimpleStringProperty(hospitalLevel.getValue().toString()));
		medicine.setNotes(new SimpleStringProperty(notes.getText()));
		medicine.setFormOfDrug(new SimpleStringProperty(formOfDrug.getText()));
		medicine.setCountryListOfCode(new SimpleStringProperty(countryListOfCode.getText()));
		medicine.setScopeOfUse(new SimpleStringProperty(scopeOfUse.getText()));
		medicine.setOrigin(new SimpleStringProperty(origin.getText()));
		System.out.println(medicine.getName());
		m.getMedicineData().add(medicine);
		m.writeMedicine();
		NewMedicine.getStage().close();
		MedicalInformation.showStage();
	}
	@FXML
	private void handleCancle(){
		NewMedicine.getStage().close();
	}

	

	 public void setMainApp(Main mainApp) {
	        this.mainApp = mainApp;

	    }


	 private Medicine med;
		public void setMedicine(Medicine med){
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
			scopeOfUse.setText(med.getScopeOfUse());
			EnglishName.setText(med.getEnglishName());
			sign.getValue();
			medicineUnit.setText(med.getName());
			limitPrice.setText(med.getName());
			hospitalSign.getValue();
			formOfDrug.setText(med.getName());
			rate.setText(med.getName());
			usage.setText(med.getName());
			origin.setText(med.getOrigin());
			size.setText(med.getName());
			limitsOfDay.setText(med.getLimitDays());
			tradingName.setText(med.getName());
			OTC.setText(med.getName());
			countryListOfCode.setText(med.getName());
			
			}
			}catch(Exception e){
				
			}
		}
}