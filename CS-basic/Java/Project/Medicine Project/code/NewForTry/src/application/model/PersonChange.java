package application.model;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PersonChange implements Serializable {
	protected String name;
	protected String ID;
	protected String sex;
	protected String medicalCode;
	protected String cardID;
	protected String certificateType;
	protected String certificateCode;
	protected String workCode;
	protected String date;
	
	 public void initialize(Person per){
			this.name=per.getName();
			this.ID=per.getID();
			this.sex=per.getSex();
			this.medicalCode=per.getMedicalCode();
			this.cardID=per.getCardID();
			this.certificateType=per.getCertificateType();
			this.certificateCode=per.getertificateCode();
			this.workCode=per.getWorkCode();
			this.date=per.getDate();
		}
	 
	 public Person getPerson(){
		 Person per=new Person();
			per.setName(new SimpleStringProperty(name));
			per.setID(new SimpleStringProperty(ID));
			per.setSex(new SimpleStringProperty(sex));
			per.setMedicalCode(new SimpleStringProperty(medicalCode));
			per.setCardID(new SimpleStringProperty(cardID));
			per.setCertificateType(new SimpleStringProperty(certificateType));
			per.setCertificateCode(new SimpleStringProperty(certificateCode));
			per.setWorkCode(new SimpleStringProperty(workCode));
			per.setDate(new SimpleStringProperty(date));
			return per;
		}
}
