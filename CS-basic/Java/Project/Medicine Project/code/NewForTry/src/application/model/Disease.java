package application.model;

import java.io.Serializable;

import javafx.beans.property.StringProperty;

public class Disease implements Serializable{
	protected StringProperty name;
	protected StringProperty ID;
	protected StringProperty type;
	protected StringProperty sign;
	protected StringProperty notes;
	
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
	public String getType(){
		return type.get();
	}
	public void setType(StringProperty type){
		this.type=type;
	}
	public StringProperty typeProperty(){
		return type;
	}
	public String getSign(){
		return sign.get();
	}
	public void setSign(StringProperty sign){
		this.sign=sign;
	}
	public StringProperty signProperty(){
		return sign;
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
