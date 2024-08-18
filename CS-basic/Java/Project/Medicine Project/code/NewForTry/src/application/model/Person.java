package application.model;

import java.io.Serializable;

import javafx.beans.property.StringProperty;

public class Person implements Serializable{

	protected StringProperty name;
	protected StringProperty ID;
	protected StringProperty sex;
	protected StringProperty medicalCode;
	protected StringProperty cardID;
	protected StringProperty certificateType;
	protected StringProperty certificateCode;
	protected StringProperty workCode;
	protected StringProperty date;
	
	 public String getName(){
			return name.get();
		}
		public void setName(StringProperty name){
			this.name=name;
		}
		public StringProperty nameProperty(){
			return name;
		}
		public String getID(){
			return ID.get();
		}
		public void setID(StringProperty ID){
			this.ID=ID;
		}
		public StringProperty IDProperty(){
			return ID;
		}
		public String getSex(){
			return sex.get();
		}
		public void setSex(StringProperty sex){
			this.sex=sex;
		}
		public StringProperty sexProperty(){
			return sex;
		}
		public String getMedicalCode(){
			return medicalCode.get();
		}
		public void setMedicalCode(StringProperty medicalCode){
			this.medicalCode=medicalCode;
		}
		public StringProperty medicalCodeProperty(){
			return medicalCode;
		}
		public String getCardID(){
			return cardID.get();
		}
		public void setCardID(StringProperty cardID){
			this.cardID=cardID;
		}
		public StringProperty cardIDProperty(){
			return cardID;
		}
		public String getCertificateType(){
			return certificateType.get();
		}
		public void setCertificateType(StringProperty certificateType){
			this.certificateType=certificateType;
		}
		public StringProperty certificateTypeProperty(){
			return certificateType;
		}
		public String getertificateCode(){
			return certificateCode.get();
		}
		public void setCertificateCode(StringProperty certificateCode){
			this.certificateCode=certificateCode;
		}
		public StringProperty certificateCodeProperty(){
			return certificateCode;
		}
		public String getWorkCode(){
			return workCode.get();
		}
		public void setWorkCode(StringProperty workCode){
			this.workCode=workCode;
		}
		public StringProperty workCodeProperty(){
			return workCode;
		}
		public String getDate(){
			return date.get();
		}
		public void setDate(StringProperty date){
			this.date=date;
		}
		public StringProperty dateProperty(){
			return date;
		}
}
