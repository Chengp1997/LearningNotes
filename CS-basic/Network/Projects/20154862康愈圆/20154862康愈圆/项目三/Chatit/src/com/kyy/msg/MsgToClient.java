package com.kyy.msg;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;

public class MsgToClient {
	
	private String message;
	
	private ArrayList<String> usernames = new ArrayList<String>();
	
	private static String date;
	
	private static Gson gson = new Gson();
	
	
	public MsgToClient(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		this.date ="["+ df.format(new Date())+"]";// new Date()为获取当前系统时间
	}
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ArrayList<String> getUsernames() {
		return usernames;
	}

	public void setUsernames(ArrayList<String> username) {
		this.usernames = username;
	}

	public String toJson(){
		return gson.toJson(this);
		
	}
	
	public void setMessage(String name, String content){
		message = date + name+": "+content+"\n";
	}
	
	

}
