package application;

import data.AppData;
import data.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import mainInter.ManagerStage;
import mainInter.UserStage;
import mainInter.WarnDialogStage;
/**
 * @author ·¢Ò¯
 *
 */

public class LoginController {
	 /**
	 * name TextField
	 */
    @FXML
    private TextField name;
    /**
     * password TextField
     */
    @FXML
    private TextField password;
    /**
     * use to get the stage create in main
     */
    private Main main;
    
    /**
     * for manager login
     * determine Whether the account exists,and go to the next page
     */
    @FXML
    private void managerLogin(){
       	if(name.getText().equals("a")&&password.getText().equals("a")){
       		main.getStage().close();
       		main.setStage(ManagerStage.getStage(main));
      		
       	}   		
    	else
    		WarnDialogStage.getStage("password is wrong");
    }
    /**
     * check which user login
     */
    @FXML
    private void userLogin(){
    	for(Customer i : AppData.getCusData()){
    		if(i.getName().get().equals(name.getText())&&i.getPassWord().equals(password.getText())){
    			UserStage.getStage(i);
    			return;
    		}
    	}
    	WarnDialogStage.getStage("password is wrong");
    }
    /**
     * initialize  the main
     * @param main used to get stage
     */
    public void setMain(Main main){
    	this.main =main;
    }
}
