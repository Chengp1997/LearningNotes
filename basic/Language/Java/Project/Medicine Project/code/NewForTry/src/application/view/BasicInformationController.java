package application.view;

import application.Data;
import application.model.Person;
import application.model.Work;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class BasicInformationController {
	@FXML
	private TableView<Work> workTable;
	@FXML
	private TableColumn<Work, String> IDColumn;
	@FXML
	private TableColumn<Work, String> NameColumn;
	@FXML
	private TableView<Person> personTable;
	@FXML
	private TableColumn<Person, String> IDColumn1;
	@FXML
	private TableColumn<Person, String> NameColumn1;
	@FXML
	private TextField search1;
	@FXML
	private Label name;
	@FXML
	private Label ID;
	@FXML
	private Label workType;
	@FXML
	private Label address;
	@FXML
	private Label postCode;
	@FXML
	private Label 	phone;
	@FXML
	private TextField search2;
	@FXML
	private Label name1;
	@FXML
	private Label ID1;
	@FXML
	private Label sex;
	@FXML
	private Label medicalCode;
	@FXML
	private Label cardID;
	@FXML
	private Label certificateType;
	@FXML
	private Label certificateCode;
	@FXML
	private Label workCode;
	@FXML
	private Label date;
	Data p;
	Data m;
	@FXML
	private void initialize() {
		showWorkDetails(null);
		showPersonDetails(null);
		// Initialize the person table with the two columns.
		p = new Data();
		p.readWork();
		m=new Data();
		m.readPerson();
		IDColumn.setCellValueFactory(cellData -> cellData.getValue().IDProperty());
		NameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		workTable.setItems(p.getWorkData());
		IDColumn1.setCellValueFactory(cellData -> cellData.getValue().IDProperty());
		NameColumn1.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		personTable.setItems(m.getPersonData());

		// Listen for selection changes and show the person details when
		// changed.
		workTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showWorkDetails(newValue));
		personTable.getSelectionModel().selectedItemProperty()
		.addListener((observable, oldValue, newValue) -> showPersonDetails(newValue));
	}

	private void showPersonDetails(Person person) {
		if (person != null) {
			name.setText(person.getName());
			ID.setText(person.getID());
			sex.setText(person.getSex());
			medicalCode.setText(person.getMedicalCode());
			cardID.setText(person.getCardID());
			certificateType.setText(person.getCertificateType());
			certificateCode.setText(person.getMedicalCode());
			workCode.setText(person.getWorkCode());
			date.setText(person.getDate());

		} else {
			name.setText("");
			ID.setText("");
			sex.setText("");
			medicalCode.setText("");
			cardID.setText("");
			certificateType.setText("");
			certificateCode.setText("");
			workCode.setText("");
			date.setText("");

		}
	}

	private void showWorkDetails(Work work) {
		if (work != null) {
			name.setText(work.getName());
			ID.setText(work.getID());
			workType.setText(work.getWorkType());
			address.setText(work.getAddress());
			postCode.setText(work.getPostCode());
			phone.setText(work.getPhone());

		} else {
			name.setText("");
			ID.setText("");
			workType.setText("");
			address.setText("");
			postCode.setText("");
			phone.setText("");

		}
		
	}
	
	// 新建单位
		@FXML
		private void newWork() {
			Work work=new Work();
			NewWork.showStage(work);
			BasicInformation.getStage().close();
		}

		// 删除单位
		@FXML
		private void handleDeleteWork() {
			int selectedIndex = workTable.getSelectionModel().getSelectedIndex();
				workTable.getItems().remove(selectedIndex);
				p.writeWork();
		}
		
		// 查找单位
		@FXML
		private void searchWork() {
			Data p = new Data();
			p.readWork();
			for (Work med : p.getWorkData()) {
				if (med.getName().equals(search1.getText()) || med.getID().equals(search1.getText())) {
					workTable.getSelectionModel().selectedItemProperty()
							.addListener((observable, oldValue, newValue) -> showWorkDetails(newValue));
					showWorkDetails(med);
				}
			}
		}
		
		// 编辑单位
		 @FXML
		 private void handleEditWork(){
			 Work selectedWork=workTable.getSelectionModel().getSelectedItem();
		 int selectedIndex = workTable.getSelectionModel().getSelectedIndex();

		 NewWork.showStage(selectedWork);
		 }
		
		
		// 新建人
				@FXML
				private void newPerson() {
					Person person=new Person();
					NewPerson.showStage(person);
					BasicInformation.getStage().close();
				}

				// 删除人
				@FXML
				private void handleDeletePerson() {
					int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
						personTable.getItems().remove(selectedIndex);
						m.writePerson();
				}
				
				// 查找单位
				@FXML
				private void searchPerson() {
					Data p = new Data();
					p.readPerson();
					for (Person med : p.getPersonData()) {
						if (med.getName().equals(search1.getText()) || med.getID().equals(search1.getText())) {
							personTable.getSelectionModel().selectedItemProperty()
									.addListener((observable, oldValue, newValue) -> showPersonDetails(newValue));
							showPersonDetails(med);
						}
					}
				}
				 @FXML
				 private void handleEditPerson(){
					 Person selectedPerson=personTable.getSelectionModel().getSelectedItem();
				 int selectedIndex = personTable.getSelectionModel().getSelectedIndex();

				 NewPerson.showStage(selectedPerson);
				 }
				
				//返回按钮
				@FXML
				private void back(){
					BasicInformation.getStage().close();
				}
}
