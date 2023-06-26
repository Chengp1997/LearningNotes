package data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * model of account
 */
public class Account {
	private StringProperty name;
	private StringProperty type;
	private StringProperty overcraft;
	private StringProperty balance;
	public Account(){
		name = new SimpleStringProperty();
		type = new SimpleStringProperty();
		overcraft= new SimpleStringProperty();
		balance= new SimpleStringProperty();
	}
	public String getType() {
		return type.get();
	}
	public void setType(String type) {
		this.type.set(type);
	}
	public String getOvercraft() {
		return overcraft.get();
	}
	public void setOvercraft(String overcraft) {
		this.overcraft.set(overcraft);
	}
	public String getBalance() {
		return balance.get();
	}
	public void setBalance(String balance) {
		this.balance.set(balance);;
	}
	public StringProperty getName() {
		return name;
	}
	public void setName(StringProperty name) {
		this.name = name;
	}
	@Override
	public boolean equals(Object o){
		if(o instanceof Account){
			return name.get().equals(((Account) o).name.get());
		}else
			return false;
	}
	@Override
	public int hashCode(){
		return name.get().hashCode();		
	}
}
