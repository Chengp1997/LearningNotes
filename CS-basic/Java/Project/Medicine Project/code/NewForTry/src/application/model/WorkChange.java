package application.model;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class WorkChange implements Serializable {
	protected String name;
    protected String ID;
    protected String workType;
    protected String address;
    protected String postCode;
    protected String phone;
    
    public void initialize(Work per){
		this.name=per.getName();
		this.ID=per.getID();
		this.workType=per.getWorkType();
		this.address=per.getAddress();
		this.postCode=per.getPostCode();
		this.phone=per.getPhone();
	}
    
    public Work getWork(){
		Work per=new Work();
		per.setName(new SimpleStringProperty(name));
		per.setID(new SimpleStringProperty(ID));
		per.setWorkType(new SimpleStringProperty(workType));
		per.setAddress(new SimpleStringProperty(address));
		per.setPostCode(new SimpleStringProperty(postCode));
		per.setPhone(new SimpleStringProperty(phone));
		return per;
	}
}
