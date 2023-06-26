import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;

public class ToClient {

	private String message;
	private String date;
	private static Gson gson = new Gson();
	private ArrayList<String> customerList = new ArrayList<String>();
	private ArrayList<String> serverList=new ArrayList<String>();
//	private ArrayList<String> currentUserNames=new ArrayList<String>();
	private String name;
	private String type;
	private ArrayList<String> curCustomerNames=new ArrayList<String>();
//	private String guestName;

	public ToClient() {
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 格式化时间
		this.date = "[" + df.format(new Date()) + "]";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setType(String type){
		this.type=type;
	}
	public String getType(){
		return type;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public String getGuestName() {
//		return guestName;
//
//	}
//
//	public void setGuestName(String guestName) {
//		this.guestName = guestName;
//	}

	// 此为新的message，用来发送消息时
	public void setMessage(String message, String data) {
		message = date + customerList + ": " + data + "\n";
	}

	public ArrayList<String> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(ArrayList<String> customerList) {
		this.customerList = customerList;
	}

	
	public ArrayList<String> getServerList() {
		return serverList;
	}
	public void setServerList(ArrayList<String> serverList) {
		this.serverList = serverList;
	}
public String toJson() {
		return gson.toJson(this);
	}

public void setCurcustomerNames(ArrayList<String> curCustomerNames) {
	// TODO Auto-generated method stub
	this.curCustomerNames=curCustomerNames;
}

}
