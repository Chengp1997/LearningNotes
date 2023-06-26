package application.model;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;

public class ServicesChange implements Serializable {
	protected String name;
	protected String ID;
	protected String chargeType;
	protected String scopeOfUse;
	protected String notes;
	
	public void initialize(Services per){
		this.name=per.getName();
		this.ID=per.getID();
		this.chargeType=per.getChargeType();
		this.scopeOfUse=per.getScopeOfUse();
		this.notes=per.getNotes();
	}
	
	 public Services getServices(){
			Services per=new Services();
			per.setName(new SimpleStringProperty(name));
			per.setID(new SimpleStringProperty(ID));
			per.setChargeType(new SimpleStringProperty(chargeType));
			per.setScopeOfUse(new SimpleStringProperty(scopeOfUse));
			per.setNotes(new SimpleStringProperty(notes));
			return per;
		}
}
