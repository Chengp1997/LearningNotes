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
			"西药", "中成药", "中草药","床位费","化验费","诊查费","检查费","护理费","特检费","输氧费","治疗费","输血费",
			"特治费","医疗服务费","手术费","麻药费","产前检查费","材料费","新生儿费","内置材料","其他费","监护床位费","非处方药","处方药",
			"甲类","乙类","丙类","化验费","诊查费","检查费","护理费","特检费","输氧费","治疗费","输血费","特治费","医疗服务费","手术费","麻药费","产前检查费"
			,"材料费","新生儿费","内置材料","其他费","监护床位费");
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
