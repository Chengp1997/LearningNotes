package data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * model of a customer
 * @author ·¢Ò¯
 *
 */
public class Customer {
    private StringProperty name;
    private StringProperty passWord;
    private ObservableList<Account> accounts;
	public Customer(){
		accounts = FXCollections.observableArrayList();
    	name =  new SimpleStringProperty();
    	passWord =  new SimpleStringProperty();
    }
	public StringProperty getName() {
		return name;
	}
	public void setName(StringProperty name) {
		this.name = name;
	}
	public String getPassWord() {
		return passWord.get();
	}
	public void setPassWord(String passWord) {
		this.passWord.set(passWord);;
	}
	public void add(Account a){
		accounts.add(a);
	}
	public ObservableList<Account> getAccounts(){
		return accounts;
	}
	public void setAccounts(ObservableList<Account> accounts){
		this.accounts =accounts;
	}
	@Override
	public boolean equals(Object o){
		if(o instanceof Customer){
			return name.get().equals(((Customer) o).name.get());
		}else
			return false;
	}
	@Override
	public int hashCode(){
		return name.get().hashCode();		
	}
}
