package application.model;

import java.io.Serializable;

import javafx.beans.property.StringProperty;

public class Work implements Serializable {
    protected StringProperty name;
    protected StringProperty ID;
    protected StringProperty workType;
    protected StringProperty address;
    protected StringProperty postCode;
    protected StringProperty phone;
    
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
	public String getWorkType(){
		return workType.get();
	}
	public void setWorkType(StringProperty workType){
		this.workType=workType;
	}
	public StringProperty workTypeProperty(){
		return workType;
	}
	public String getAddress(){
		return address.get();
	}
	public void setAddress(StringProperty address){
		this.address=address;
	}
	public StringProperty addressProperty(){
		return address;
	}
	public String getPostCode(){
		return postCode.get();
	}
	public void setPostCode(StringProperty postCode){
		this.postCode=postCode;
	}
	public StringProperty postCodeProperty(){
		return postCode;
	}
	public String getPhone(){
		return phone.get();
	}
	public void setPhone(StringProperty phone){
		this.phone=phone;
	}
	public StringProperty phoneProperty(){
		return phone;
	}
}
