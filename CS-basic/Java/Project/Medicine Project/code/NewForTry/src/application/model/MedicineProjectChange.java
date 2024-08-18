package application.model;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;

public class MedicineProjectChange implements Serializable{
	protected String name;
	protected String ID;
	protected String chargeType;
	protected String chargeLevel;
	protected String hospitalLevel;
	protected String approval;
	protected String unit;
	protected String factory;
	protected String notes;
	protected String scopeOfUse;
	
	public void initialize(MedicineProject per){
		this.name=per.getName();
		this.ID=per.getID();	
		this.chargeType=per.getChargeType();
		this.chargeLevel=per.getChargeLevel();
		this.approval=per.getApproval();
		this.hospitalLevel=per.getHospitalLevel();
		this.scopeOfUse=per.getScopeOfUse();
		this.unit=per.getUnit();
		this.factory=per.getFactory();
		this.notes=per.getNotes();
	}
	
	 public MedicineProject getMedicineProject(){
		 MedicineProject per=new MedicineProject();
			per.setName(new SimpleStringProperty(name));
			per.setID(new SimpleStringProperty(ID));
			per.setChargeType(new SimpleStringProperty(chargeType));
			per.setChargeLevel(new SimpleStringProperty(chargeLevel));
			per.setApproval(new SimpleStringProperty(approval));
			per.setHospitalLevel(new SimpleStringProperty(hospitalLevel));
			per.setScopeOfUse(new SimpleStringProperty(scopeOfUse));
			per.setUnit(new SimpleStringProperty(unit));
			per.setFactory(new SimpleStringProperty(factory));
			per.setNotes(new SimpleStringProperty(notes));
			return per;
		}
}
