package com.kyy.msg;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;

public class MsgToClient {
	
	private ArrayList<String> customerNames = new ArrayList<String>();
	private ArrayList<String> serverNames = new ArrayList<String>();
	private ArrayList<String> curCustomerNames = new ArrayList<String>();
	

	private String message;
	private static Gson gson = new Gson();
	private String date;
	private String servername;
	private String customername;


	
	public MsgToClient(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		this.date ="["+ df.format(new Date())+"]";// new Date()为获取当前系统时间
	}
	
	public MsgToClient(MsgToClient m){
		this.customername = m.getCustomername();
		this.servername = m.getServername();
		this.customerNames =m.getCustomerNames();
		this.serverNames = m.getServerNames();
	}
	
	
	public ArrayList<String> getCurcustomerNames() {
		return curCustomerNames;
	}

	public void setCurcustomerNames(ArrayList<String> curcustomerNames) {
		this.curCustomerNames = curcustomerNames;
	}
	
	
	public String getServername() {
		return servername;
	}
	public void setServername(String servername) {
		this.servername = servername;
	}
	public String getCustomername() {
		return customername;
	}
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	public ArrayList<String> getCustomerNames() {
		return customerNames;
	}
	public void setCustomerNames(ArrayList<String> customerNames) {
		this.customerNames = customerNames;
	}
	public ArrayList<String> getServerNames() {
		return serverNames;
	}
	public void setServerNames(ArrayList<String> serverNames) {
		this.serverNames = serverNames;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String toJson(){
		return gson.toJson(this);
		
	}
	
	public void setMessage(String name, String content){
		message = date + name+": "+content+"\n";
	}

}
