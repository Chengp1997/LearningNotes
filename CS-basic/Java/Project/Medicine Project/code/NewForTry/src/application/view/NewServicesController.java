package application.view;

import application.Data;
import application.model.Medicine;
import application.model.Services;
import application.model.Work;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class NewServicesController {
	@FXML
	private TextField name;
	@FXML
	private TextField ID;
	@FXML
	private TextField scopeOfUse;
	@FXML
	private TextField notes;
	@FXML
	private ChoiceBox<String> chargeType;
	
	ObservableList<String> chargeTypeObservableList = FXCollections.observableArrayList(
			"��ҩ", "�г�ҩ", "�в�ҩ","��λ��","�����","����","����","�����","�ؼ��","������","���Ʒ�","��Ѫ��",
			"���η�","ҽ�Ʒ����","������","��ҩ��","��ǰ����","���Ϸ�","��������","���ò���","������","�໤��λ��","�Ǵ���ҩ","����ҩ",
			"����","����","����","�����","����","����","�����","�ؼ��","������","���Ʒ�","��Ѫ��","���η�","ҽ�Ʒ����","������","��ҩ��","��ǰ����"
			,"���Ϸ�","��������","���ò���","������","�໤��λ��");
	private Services services=new Services();
	@FXML
    private void initialize() {
        chargeType.setItems(chargeTypeObservableList);
    }
	@FXML
	private void handleOK(){
		Data m=new Data();
		m.readServices();
		try{
			if (med.getName()!=null){
				int i=0;
				for (Services mp:m.servicesData){
					if (med.getName().equals(mp.getName())){
						m.servicesData.remove(i);
						m.writeServices();
						break;
					}
					i++;
				}
			}
		}
		catch(Exception e){
			
		
		}
		services.setName(new SimpleStringProperty(name.getText()));
		services.setID(new SimpleStringProperty(ID.getText()));
		services.setChargeType(new SimpleStringProperty(chargeType.getValue().toString()));
		services.setNotes(new SimpleStringProperty(notes.getText()));
		services.setScopeOfUse(new SimpleStringProperty(scopeOfUse.getText()));
		System.out.println(services.getName());
		m.getServicesData().add(services);
		m.writeServices();
		NewServices.getStage().close();
		MedicalInformation.showStage();
	}
	@FXML
	private void handleCancle(){
		NewServices.getStage().close();
	}
	private Services med;
		public void setServices(Services med){
			this.med=med;
			try{
			if (med.getName()!=null){
			name.setText(med.getName());
			ID.setText(med.getID());
			chargeType.getValue();
			
			notes.setText(med.getNotes());
			scopeOfUse.setText(med.getScopeOfUse());
			
			
			}
			}catch(Exception e){
				
			}
		}
}
