package application.model;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DiseaseChange implements Serializable{
	protected String name;
	protected String ID;
	protected String type;
	protected String sign;
	protected String notes;
	
	public void initialize(Disease per){
		this.name=per.getName();
		this.ID=per.getID();
		this.sign=per.getSign();
		this.type=per.getType();
		this.notes=per.getNotes();
	}
	
	public Disease getDisease(){
		Disease per=new Disease();
		per.setName(new SimpleStringProperty(name));
		per.setID(new SimpleStringProperty(ID));
		per.setSign(new SimpleStringProperty(sign));
		per.setNotes(new SimpleStringProperty(notes));
		per.setType(new SimpleStringProperty(type));
		return per;
	}
}
