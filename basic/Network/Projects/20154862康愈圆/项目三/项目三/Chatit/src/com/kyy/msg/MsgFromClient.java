package com.kyy.msg;

public class MsgFromClient {
	private  String to;
	private  String msg;
	
	
	
	public String getDestication() {
		return to;
	}
	public  void setDestication(String destication) {
		this.to = destication;
	}
	public  String getMessageContent() {
		return msg;
	}
	public  void setMessageContent(String messageContent) {
		this.msg = messageContent;
	}
	
	

}
