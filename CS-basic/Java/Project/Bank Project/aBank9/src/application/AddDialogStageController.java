package application;

import data.Account;
import data.AppData;
import data.Customer;
import database.Data;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainInter.WarnDialogStage;
/**
 * to control the AddDialog
 * @author Œ‚∫È∑¢
 */
public class AddDialogStageController {
	/**
	 * testField for type 
	 */
    @FXML
    private TextField type;
    /**
	 * testField for name of new account
	 */
    @FXML
    private TextField name;
    /**
	 * testField for balance of new account
	 */
    @FXML
    private TextField balance;
    /**
	 * testField for overdraft of new account
	 */  
    @FXML
    private TextField overdroft;
    /**
     * get from the mainIner.AddDialogStage and used for closed it
     * @see  mainInter.AddDialogStage#getStage(int i)
     */
    private Stage stage;
    /**
     * to determine which customer's account
     */
    private int index;
    /**
     * determine whether the input is legal,and add it into database
     * @see database.Data#add(Account a , Customer c)
     * @exception e input is not legal
     */
    @FXML
    private void add(){
    	String aType = type.getText();
    	String aName = name.getText();
    	String aBalance = balance.getText();
    	String aOverdroft = overdroft.getText();
    	boolean cando = true;
    	if(!aType.equals("private")&&!aType.equals("business")){
    		cando = false;
    		WarnDialogStage.getStage("type should be private or public");
    		return;
    	}   		
    	else if(aName==null||aName.equals("")){
    		cando = false;
    	}else if(aBalance==null||aBalance.equals("")){
    		cando = false;
    	}else if(aOverdroft==null||aOverdroft.equals("")){
    		cando = false;
    	}
    	if(!cando){
    		WarnDialogStage.getStage("input Error");
    		return;
    	}
    	for(Customer h : AppData.getCusData()){
    		for(Account g : h.getAccounts()){
    			if(g.getName().get().equals(aName)){
    			  	WarnDialogStage.getStage("this Name has been exist");
    			  	return ;
    			}
    		}
  
    	}
    	try{
    		Double.valueOf(aBalance );
    		Double.valueOf(aOverdroft);
    		Account i = new Account();
    		if(aType.equals("private")){
    			i.setType("private");
    		}else{
    			i.setType("business");
    		}   		
    		i.setBalance(aBalance);
    		i.setName(new SimpleStringProperty(aName));
    		i.setOvercraft(aOverdroft);
    		AppData.getCusData().get(index).add(i);
    		Data.add(i, AppData.getCusData().get(index));
    		stage.close();
    		WarnDialogStage.getStage("sucessful");
    	}catch(Exception e){
    		WarnDialogStage.getStage("input Error");
    	}
    }
    /**
     * initialize the attribute
     * @param stage ,get the stage controlled by this 
     * @param i , get the index of customer
     */
    public void setStage(Stage stage,int i){
    	this.stage = stage;
    	this.index = i;
    }
}
