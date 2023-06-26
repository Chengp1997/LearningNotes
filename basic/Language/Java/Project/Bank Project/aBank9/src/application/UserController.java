package application;

import data.Account;
import data.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class UserController {
	/**
	 * accountTable to display accounts of a customer
	 */
    @FXML
    private TableView<Account> accountTable;   
    /**
     * display the name of account
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
    @FXML
    /**
     *initialize the data of table
     */
    private void initialize() {
        accountColumn.setCellValueFactory(cellData -> cellData.getValue().getName());
        showAccount(null);
        accountTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showAccount(newValue)); 
    }
    /**
     *display the data of account
     *@param newValue display the value
     *@return null
     */
	private Object showAccount(Account newValue) {
		if(newValue!=null){
			balance.setText(newValue.getBalance());
			overdraft.setText(newValue.getOvercraft());
			type.setText(newValue.getType());
		}
		return null;
	}
	 /**
     *initialize the customer
     *@param cus set list account into table
     */
	public void setCustomer(Customer cus){
    	accountTable.setItems(cus.getAccounts());
    }
}
