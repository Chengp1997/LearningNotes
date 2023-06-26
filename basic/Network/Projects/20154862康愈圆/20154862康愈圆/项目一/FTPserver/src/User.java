import java.io.Serializable;

public class User implements Serializable{
	String userID;
	String password;
	
	public User(String userID, String password){
		this.userID = userID;
		this.password = password;
	}
	
	public User(){
		this.userID = null;
		this.password = null;
	}
	
	public String getID(){
		return this.userID;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public void setID(String s){
		this.userID = s;
	}
	
	public void setPassword(String s){
		this.password = s;
	}

}
