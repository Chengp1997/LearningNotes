package application;

import data.Account;
import data.AppData;
import data.Customer;
import database.Data;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import mainInter.AddDialogStage;
import mainInter.WarnDialogStage;

/**
 * @author ·¢Ò¯
 *
 */
public class ManagerController {
	
    /**
     * the tableView to show the list of customers
     */
    @FXML
    private TableView<Customer> customerTable;
    /**
     * the tableView to show the account of the selected customer
     */
    @FXML
    private TableView<Account> accountTable;   
    /**
     * only display the name of customer
     */
    @FXML
    private TableColumn<Customer, String> nameColumn;
    /**
     * only display the name of account
     */
    @FXML
    private TableColumn<Account, String> accountColumn;
    /**
     * the label to show the balance of the selected account
     */
    @FXML
    private Label balance;
    /**
     * the label to show the overdraft of the selected account
     */
    @FXML
    private Label overdraft;
    /**
     * the label to show the type of the selected account
     */
    @FXML
    private Label type;  
    /**
     * the textField let managers can add or minus the balance
     */
    @FXML
    private TextField input;
   // private Main main;
    /**
     * to initialize the name column of the table and add the change listener 
     */
    @FXML
    private void initialize() {
    	
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getName());
        accountColumn.setCellValueFactory(cellData -> cellData.getValue().getName());
        showPersonDetails(null);
        customerTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }
    /**
     * to show the data of a customer
     * @param customer which is selected
     */
	private void showPersonDetails(Customer customer) {
		if(customer!=null){
			accountTable.setItems(customer.getAccounts());
			showAccount(null);
	        accountTable.getSelectionModel().selectedItemProperty().addListener(
	                (observable, oldValue, newValue) -> showAccount(newValue)); 			
		}   
	}
	   /**
     * to show the data of a customer
     * @param newValue account which is selected
     * @return null
     */
	private Object showAccount(Account newValue) {
		if(newValue!=null){
        	balance.setText(newValue.getBalance());
        	overdraft.setText(newValue.getOvercraft());
        	type.setText(newValue.getType());
		}else{
        	balance.setText(" ");
        	overdraft.setText(" ");
        	type.setText(" ");
		}
		return null;
	}

	public void setMain(Main main){
		//this.main = main;
		customerTable.setItems(AppData.getCusData());
	}
	/**
	 *  to call changeBalance and pass 1 
	 */
    @FXML
    private void handleAddMoney() {
    	changeBalance(1);
    }
	/**
	 *  to call changeBalance and pass 0 
	 */
    @FXML
    private void handleMinusMoney() {
    	changeBalance(0);
    }
	/**
	 *  to add or minus the balance
	 *  @param i to determine whether add or minus
	 */
    private void changeBalance(int i){
        int selectedIndex = customerTable.getSelectionModel().getSelectedIndex();
        int accountIndex = accountTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex>=0){
        	double balance = Double.valueOf(customerTable.getItems().get(selectedIndex).getAccounts().get(accountIndex).getBalance()) ;
        	double overdraft = Double.valueOf(customerTable.getItems().get(selectedIndex).getAccounts().get(accountIndex).getOvercraft()) ;
        	try{
        		double thisInput = Double.valueOf(input.getText());
            	if(i==0){
            		if(balance+overdraft>=thisInput){
            			balance-=thisInput;
            		}else{
            			WarnDialogStage.getStage("balance is too little");
            		}
            	}else{
            		balance+=thisInput;
            	}
        	}catch(Exception e){
        		WarnDialogStage.getStage("input error");
        	}        	
        	Account h = customerTable.getItems().get(selectedIndex).getAccounts().get(accountIndex);
        	h.setBalance(String.valueOf(balance));
        	AppData.getCusData().get(selectedIndex).getAccounts().set(accountIndex, h);
        	Data.updateAccount(h.getName().get(), "balance", String.valueOf(balance));
        	
        }
    }
    /**
     * call the add AddDialogStage to addAccount
     */
    @FXML
    private	void addCustomer(){
    	 int selectedIndex = customerTable.getSelectionModel().getSelectedIndex();
    	AddDialogStage.getStage(selectedIndex);
    }
}
