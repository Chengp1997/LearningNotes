package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import data.Customer;
import data.Account;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Data {
	/**
	 * use to save the balance of account
	 * @param accName the name of account as primary key
	 * @param index the index of column
	 * @param newValue the new value of account
	 */
	public static void updateAccount(String accName ,String index,String newValue){
		Statement st;
		try {
			st = DatabaseConn.getConn().createStatement();
			String str = "update account set "+index+" = "+"'"+newValue+"' "+"where name = '"+accName+"'";
			st.execute(str);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
	}
	/**
	 * use to add a new account into the customer and save into database
	 * @param a  new account
	 * @param c the customer
	 */
	public static void add(Account a , Customer c){
		Statement st;
		try {
			st = DatabaseConn.getConn().createStatement();
			String str ="insert into account values ("+"'"+a.getName().get()+"',"+"'"+a.getType()+"','"+a.getOvercraft()+"','"+a.getBalance()+"')";
			st.execute(str);
			st.close();
			st = DatabaseConn.getConn().createStatement();
			str ="insert into customer values ("+"'"+c.getName().get()+"',"+"'"+a.getName().get()+"','"+c.getPassWord()+"')";
			st.execute(str);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	/**
	 * find all customer in database
	 * @return the list of customer 
	 */
	public static ObservableList<Customer> get(){
		ObservableList<Customer> cusData = FXCollections.observableArrayList();
		HashMap<Customer,ArrayList<Account>> customers =new HashMap<>();
		Statement st;
		try {
			st = DatabaseConn.getConn().createStatement();   
			String str = "select * from customer ";
			ResultSet rs = st.executeQuery(str);
			while(rs.next()){
				Customer i = new Customer();
				i.setName(new SimpleStringProperty(rs.getString("name")));
				i.setPassWord(rs.getString("password"));
				if(customers.get(i)==null){
					customers.put(i, new ArrayList<>());
				}
				Statement st2 = DatabaseConn.getConn().createStatement();
				String str2 = "select * from account  where name = '"+rs.getString("acc_name")+"'";
				ResultSet rs2 = st2.executeQuery(str2);
				while(rs2.next()){
					Account j = new Account();
					j.setBalance(rs2.getString("balance"));
					j.setName(new SimpleStringProperty(rs2.getString("name")));
					j.setOvercraft(rs2.getString("overcraft"));
					j.setType(rs2.getString("type"));
					customers.get(i).add(j);
				}
				rs2.close();
				st2.close();
			}
			for(Customer cus : customers.keySet()){

				cus.setAccounts( FXCollections.observableArrayList(customers.get(cus)));
				cusData.add(cus);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cusData;
		
	}
	
}
