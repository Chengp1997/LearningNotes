package application.model;

import java.io.Serializable;

import javafx.beans.property.StringProperty;

public class MedicineProject implements Serializable {

	protected StringProperty name;
	protected StringProperty ID;
	protected StringProperty chargeType;
	protected StringProperty chargeLevel;
	protected StringProperty hospitalLevel;
	protected StringProperty approval;
	protected StringProperty unit;
	protected StringProperty factory;
	protected StringProperty notes;
	protected StringProperty scopeOfUse;
	
	public MedicineProject(){
		
	}
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
	public StringProperty  IDProperty(){
		return ID;
	}
	public String getChargeType(){
		return chargeType.get();
	}
	public void setChargeType(StringProperty chargeType){
		this.chargeType=chargeType;
	}
	public StringProperty chargeTypeProperty(){
		return chargeType;
	}
	public String getChargeLevel(){
		return chargeLevel.get();
	}
	public void setChargeLevel(StringProperty chargeLevel){
		this.chargeLevel=chargeLevel;
	}
	public StringProperty chargeLevelProperty(){
		return chargeLevel;
	}
	public String getApproval(){
		return approval.get();
	}
	public void setApproval(StringProperty approval){
		this.approval=approval;
	}
	public StringProperty approvalProperty(){
		return approval;
	}
	
	public String getHospitalLevel(){
		return hospitalLevel.get();
	}
	public void setHospitalLevel(StringProperty hospitalLevel){
		this.hospitalLevel=hospitalLevel;
	}
	public StringProperty hospitalLevelProperty(){
		return hospitalLevel;
	}
	public String getScopeOfUse(){
		return scopeOfUse.get();
	}
	public void setScopeOfUse(StringProperty scopeOfUse){
		this.scopeOfUse=scopeOfUse;
	}
	public StringProperty scopeOfUseProperty(){
		return scopeOfUse;
	}
	public String getUnit(){
		return unit.get();
	}
	public void setUnit(StringProperty unit){
		this.unit=unit;
	}
	public StringProperty unitProperty(){
		return unit;
	}
	public String getFactory(){
		return factory.get();
	}
	public void setFactory(StringProperty factory){
		this.factory=factory;
	}
	public StringProperty factoryProperty(){
		return factory;
	}
	public String getNotes(){
		return notes.get();
	}
	public void setNotes(StringProperty notes){
		this.notes=notes;
	}
	public StringProperty notesProperty(){
		return notes;
	}
	
	
}
