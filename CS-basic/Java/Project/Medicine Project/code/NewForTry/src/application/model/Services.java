package application.model;

import java.io.Serializable;

import javafx.beans.property.StringProperty;

public class Services implements Serializable{
	protected StringProperty name;
	protected StringProperty ID;
	protected StringProperty chargeType;
	protected StringProperty notes;
	protected StringProperty scopeOfUse;
	
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
	public String getChargeType(){
		return chargeType.get();
	}
	public void setChargeType(StringProperty chargeType){
		this.chargeType=chargeType;
	}
	public StringProperty chargeTypeProperty(){
		return chargeType;
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
