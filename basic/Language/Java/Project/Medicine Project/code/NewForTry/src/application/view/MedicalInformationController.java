package application.view;

import application.Data;
import application.model.Disease;
import application.model.Medicine;
import application.model.MedicineProject;
import application.model.Services;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MedicalInformationController {

	// for medicine table
	@FXML
	private TableView<Medicine> medicineTable;
	@FXML
	private TableColumn<Medicine, String> IDColumn;
	@FXML
	private TableColumn<Medicine, String> NameColumn;
	@FXML
	private TextField search1;
	// for medicineProject table
	@FXML
	private TableView<MedicineProject> medicineProjectTable;
	@FXML
	private TableColumn<MedicineProject, String> IDColumn1;
	@FXML
	private TableColumn<MedicineProject, String> NameColumn1;
	// for services
	@FXML
	private TableView<Services> servicesTable;
	@FXML
	private TableColumn<Services, String> IDColumn2;
	@FXML
	private TableColumn<Services, String> NameColumn2;
	//for disease
	@FXML
	private TableView<Disease> diseaseTable;
	@FXML
	private TableColumn<Disease, String> IDColumn3;
	@FXML
	private TableColumn<Disease, String> NameColumn3;
	// for medicine
	@FXML
	private Label name;
	@FXML
	private Label ID;
	@FXML
	private Label EnglishName;
	@FXML
	private Label chargeType;
	@FXML
	private Label sign;
	@FXML
	private Label chargeLevel;
	@FXML
	private Label limitPrice;
	@FXML
	private Label medicineUnit;
	@FXML
	private Label hospitalSign;
	@FXML
	private Label approval;
	@FXML
	private Label hospitalLevel;
	@FXML
	private Label formOfDrug;
	@FXML
	private Label scopeOfUse;
	@FXML
	private Label rate;
	@FXML
	private Label usage;
	@FXML
	private Label unit;
	@FXML
	private Label size;
	@FXML
	private Label limitDays;
	@FXML
	private Label tradingName;
	@FXML
	private Label factory;
	@FXML
	private Label OTC;
	@FXML
	private Label notes;
	@FXML
	private Label countryListOfCode;
	@FXML
	private Label origin;
	// for medicineProject
	@FXML
	private TextField search2;
	@FXML
	private Label name1;
	@FXML
	private Label ID1;
	@FXML
	private Label chargeType1;
	@FXML
	private Label hospitalLevel1;
	@FXML
	private Label chargeLevel1;
	@FXML
	private Label approval1;
	@FXML
	private Label unit1;
	@FXML
	private Label factory1;
	@FXML
	private Label notes1;
	@FXML
	private Label scopeOfUse1;

	// for Services
	@FXML
	private TextField search3;
	@FXML
	private Label name2;
	@FXML
	private Label ID2;
	@FXML
	private Label notes2;
	@FXML
	private Label scopeOfUse2;
	@FXML
	private Label chargeType2;

	// for disease
	@FXML
	private TextField search4;
	@FXML
	private Label name3;
	@FXML
	private Label ID3;
	@FXML
	private Label notes3;
	@FXML
	private Label sign3;
	@FXML
	private Label Type3;
	@FXML
	private TextField search5;
	Data p;
	Data mp;
	Data s;
	Data d;

	@FXML
	private void initialize() {
		showMedicineDetails(null);
		showMedicineProjectDetails(null);
		showServicesDetails(null);
		showDiseaseDetails(null);
		// Initialize the person table with the two columns.
	    p = new Data();
		p.readMedicine();
		mp = new Data();
		mp.readMedicineProject();
		s = new Data();
		s.readServices();
		d = new Data();
		d.readDisease();
		IDColumn.setCellValueFactory(cellData -> cellData.getValue().IDProperty());
		NameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		medicineTable.setItems(p.getMedicineData());
		IDColumn1.setCellValueFactory(cellData -> cellData.getValue().IDProperty());
		NameColumn1.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		medicineProjectTable.setItems(mp.getMedicineProjectData());
		IDColumn2.setCellValueFactory(cellData -> cellData.getValue().IDProperty());
		NameColumn2.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		servicesTable.setItems(s.getServicesData());
		IDColumn3.setCellValueFactory(cellData -> cellData.getValue().IDProperty());
		NameColumn3.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		diseaseTable.setItems(d.getDiseaseData());

		// Listen for selection changes and show the person details when
		// changed.
		medicineTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showMedicineDetails(newValue));
		medicineProjectTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showMedicineProjectDetails(newValue));
		servicesTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showServicesDetails(newValue));
		diseaseTable.getSelectionModel().selectedItemProperty()
		.addListener((observable, oldValue, newValue) -> showDiseaseDetails(newValue));
	}

	// 显示药品信息
	private void showMedicineDetails(Medicine medicine) {
		if (medicine != null) {

			name.setText(medicine.getName());
			ID.setText(medicine.getID());
			EnglishName.setText(medicine.getEnglishName());
			chargeType.setText(medicine.getChargeType());
			sign.setText(medicine.getSign());
			chargeLevel.setText(medicine.getChargeLevel());
			medicineUnit.setText(medicine.getMedicineUnit());
			limitPrice.setText(medicine.getLimitPrice());
			hospitalSign.setText(medicine.getHospitalSign());
			approval.setText(medicine.getApproval());
			hospitalLevel.setText(medicine.getHospitalLevel());
			formOfDrug.setText(medicine.getFormOfDrug());
			scopeOfUse.setText(medicine.getScopeOfUse());
			rate.setText(medicine.getRate());
			usage.setText(medicine.getUsage());
			unit.setText(medicine.getUnit());
			size.setText(medicine.getSize());
			limitDays.setText(medicine.getLimitDays());
			factory.setText(medicine.getFactory());
			OTC.setText(medicine.getOTC());
			notes.setText(medicine.getNotes());
			countryListOfCode.setText(medicine.getCountryListOfCode());
			origin.setText(medicine.getOrigin());
			tradingName.setText(medicine.getTradingName());

		} else {
			name.setText("");
			ID.setText("");
			EnglishName.setText("");
			chargeType.setText("");
			sign.setText("");
			chargeLevel.setText("");
			medicineUnit.setText("");
			limitPrice.setText("");
			hospitalSign.setText("");
			approval.setText("");
			hospitalLevel.setText("");
			formOfDrug.setText("");
			scopeOfUse.setText("");
			rate.setText("");
			usage.setText("");
			unit.setText("");
			size.setText("");
			limitDays.setText("");
			tradingName.setText("");
			factory.setText("");
			OTC.setText("");
			notes.setText("");
			countryListOfCode.setText("");
			origin.setText("");
		}

	}

	// 新建药品
	@FXML
	private void newMedicine() {
		Medicine a = new Medicine();
		NewMedicine.showStage(a);
		MedicalInformation.getStage().close();
	}

	// 删除药品

	@FXML
	private void handleDeleteMedicine() {
		int selectedIndex = medicineTable.getSelectionModel().getSelectedIndex();
		medicineTable.getItems().remove(selectedIndex);
		p.writeMedicine();

	}

	// 编辑药品
	@FXML
	private void handleEditMedicine() {
		Medicine selectedMedicine = medicineTable.getSelectionModel().getSelectedItem();
		int selectedIndex = medicineTable.getSelectionModel().getSelectedIndex();

		NewMedicine.showStage(selectedMedicine);

	}

	// 查找药品
	@FXML
	private void searchMedicine() {
		Data p = new Data();
		p.readMedicine();
		for (Medicine med : p.getMedicineData()) {
			if (med.getName().equals(search1.getText()) || med.getID().equals(search1.getText())) {
				medicineTable.getSelectionModel().selectedItemProperty()
						.addListener((observable, oldValue, newValue) -> showMedicineDetails(newValue));
				showMedicineDetails(med);
			}
		}
	}

	// 新建医疗项目
	@FXML
	private void newMedicalProject() {
		MedicineProject a = new MedicineProject();
		NewMedicineProject.showStage(a);
	}

	private void showMedicineProjectDetails(MedicineProject medicineProject) {
		if (medicineProject != null) {
			name1.setText(medicineProject.getName());
			ID1.setText(medicineProject.getID());
			chargeType1.setText(medicineProject.getChargeType());
			chargeLevel1.setText(medicineProject.getChargeLevel());
			approval1.setText(medicineProject.getApproval());
			hospitalLevel1.setText(medicineProject.getHospitalLevel());
			scopeOfUse1.setText(medicineProject.getScopeOfUse());
			unit1.setText(medicineProject.getUnit());
			factory1.setText(medicineProject.getFactory());
			notes1.setText(medicineProject.getNotes());

		} else {
			name1.setText("");
			ID1.setText("");
			chargeType1.setText("");
			chargeLevel1.setText("");
			approval1.setText("");
			hospitalLevel1.setText("");
			scopeOfUse1.setText("");
			unit1.setText("");
			factory1.setText("");
			notes1.setText("");
		}

	}

	// 查找项目
	@FXML
	private void searchMedicineProject() {
		Data p = new Data();
		p.readMedicineProject();
		for (MedicineProject med : p.getMedicineProjectData()) {
			if (med.getName().equals(search2.getText()) || med.getID().equals(search2.getText())) {
				medicineProjectTable.getSelectionModel().selectedItemProperty()
						.addListener((observable, oldValue, newValue) -> showMedicineProjectDetails(newValue));
				showMedicineProjectDetails(med);
			}
		}
	}
	// 编辑医疗项目

	@FXML
	private void handleEditMedicineProject() {

		MedicineProject selectedmedicineProject = medicineProjectTable.getSelectionModel().getSelectedItem();
		int t = medicineProjectTable.getSelectionModel().getSelectedIndex();
		NewMedicineProject.showStage(selectedmedicineProject);
	}

	// 删除医疗项目
	@FXML
	private void handleDeleteMedicineProject() {
		int selectedIndex = medicineProjectTable.getSelectionModel().getSelectedIndex();
		medicineProjectTable.getItems().remove(selectedIndex);
		mp.writeMedcineProject();

	}

	// 新建服务设施
	@FXML
	private void newServices() {
		Services ser = new Services();
		NewServices.showStage(ser);
	}

	private void showServicesDetails(Services services) {
		if (services != null) {
			name2.setText(services.getName());
			ID2.setText(services.getID());
			scopeOfUse2.setText(services.getScopeOfUse());
			chargeType2.setText(services.getChargeType());
			notes2.setText(services.getNotes());

		} else {
			name2.setText("");
			ID2.setText("");
			chargeType2.setText("");
			scopeOfUse2.setText("");
			notes2.setText("");
		}

	}

	// 搜索服务设施
	@FXML
	private void searchServices() {
		Data p = new Data();
		p.readServices();
		for (Services med : p.getServicesData()) {
			if (med.getName().equals(search3.getText()) || med.getID().equals(search3.getText())) {
				servicesTable.getSelectionModel().selectedItemProperty()
						.addListener((observable, oldValue, newValue) -> showServicesDetails(newValue));
				showServicesDetails(med);
			}
		}
	}

	// 删除服务设施
	@FXML
	private void handleDeleteServices() {
		int selectedIndex = servicesTable.getSelectionModel().getSelectedIndex();
		servicesTable.getItems().remove(selectedIndex);
		s.writeServices();

	}

	// 编辑服务
	@FXML
	private void handleEditServices() {

		Services selectedServices = servicesTable.getSelectionModel().getSelectedItem();
		int t = servicesTable.getSelectionModel().getSelectedIndex();
		NewServices.showStage(selectedServices);
	}
	
	//病种
	private void showDiseaseDetails(Disease disease) {
		if (disease != null) {
			name3.setText(disease.getName());
			ID3.setText(disease.getID());
			sign3.setText(disease.getSign());
			Type3.setText(disease.getType());
			notes3.setText(disease.getNotes());

		} else {
			name3.setText("");
			ID3.setText("");
			Type3.setText("");
			sign3.setText("");
			notes3.setText("");
		}

	}
	//新增病种
       @FXML
       private void newDisease(){
    	   Disease disease=new Disease();
    	   NewDisease.showStage(disease);
       }
	// 搜索病种
		@FXML
		private void searchDisease() {
			Data p = new Data();
			p.readDisease();
			for (Disease med : p.getDiseaseData()) {
				if (med.getName().equals(search4.getText()) || med.getID().equals(search4.getText())) {
					diseaseTable.getSelectionModel().selectedItemProperty()
							.addListener((observable, oldValue, newValue) -> showDiseaseDetails(newValue));
					showDiseaseDetails(med);
				}
			}
		}

		// 删除病种
		@FXML
		private void handleDeleteDisease() {
			int selectedIndex = diseaseTable.getSelectionModel().getSelectedIndex();
			diseaseTable.getItems().remove(selectedIndex);
			d.writeDisease();

		}

		// 编辑病种
		@FXML
		private void handleEditDisease() {

			Disease selectedDisease = diseaseTable.getSelectionModel().getSelectedItem();
			int t = diseaseTable.getSelectionModel().getSelectedIndex();
			NewDisease.showStage(selectedDisease);
		}


	// 返回按钮
	@FXML
	private void back() {
		MedicalInformation.getStage().close();
	}

}
