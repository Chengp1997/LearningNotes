package com.kyy.annotation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import com.kyy.msg.MsgFromClient;
import com.kyy.msg.MsgToClient;
import com.kyy.annotation.ChatAnnotation;

@ServerEndpoint("/chatSocket")
public class ChatAnnotation {
	
	
	private Session session;
	private static final Set<ChatAnnotation> serverconnections = new CopyOnWriteArraySet();
	private static final Set<ChatAnnotation> customerconnections = new CopyOnWriteArraySet();
	private static ArrayList<String> serverNames = new ArrayList<String>();
	private static ArrayList<String> customerNames = new ArrayList<String>();
	private  ArrayList<String>  curCustomerNames = new ArrayList<String>();
	boolean tag = true;
	private String username;
	private String id;
	private String currentServerName;
	

	@OnOpen
	public void open(Session session){
		
		
		this.session = session;
		String query = session.getQueryString();
		
		username = (query.split("=")[1]).split("&")[0];
		id = query.split("=")[2];
		MsgToClient msg = new MsgToClient();
		
		
		switch(id){
		case "server":
			
			serverconnections.add(this);
			serverNames.add(username);
			
			msg.setCustomerNames(customerNames);
			msg.setServerNames(serverNames);
			
			if(tag = true){
				msg.setMessage("*Welcome to the system,"+username+"!\n");
				tag = false;
			}
			msg.setServername(username);
			broadcast(msg.toJson(),serverconnections);
			break;
			
			
			
		case "customer":
			
			
			customerconnections.add(this);
			customerNames.add(username);
			
			
			if(serverNames.size()==0){
				msg.setCustomerNames(customerNames);
				msg.setServerNames(serverNames);
				msg.setMessage("*Sorry, there is no server now.");
				sendToCustomer(msg.toJson(),username);
			}else{
				
				currentServerName = serverNames.get(RandomNumber(0,serverNames.size()));
				msg.setCustomerNames(customerNames);
				msg.setServerNames(serverNames);
				
				msg.setMessage(String.format("*Welcome to the system!\n *The server: %s will serve you!\n", currentServerName));
				msg.setCustomername(username);
				msg.setServername(currentServerName);
				
				sendToCustomer(msg.toJson(),username);
				
				
				MsgToClient msgser = new MsgToClient(msg);
				curCustomerNames = getSession(currentServerName).getCurCustomers();
				curCustomerNames.add(username);	
				msgser.setCurcustomerNames(curCustomerNames);
				msgser.setMessage(String.format("Customer:%s need your help.\n", username));
				sendToServer(msgser.toJson(),currentServerName);
			}
			break;		
		}
		
	
	}
	
	
	
	
	@OnMessage
    public void incoming(String message) {
        
		MsgFromClient msg = (new Gson()).fromJson(message, MsgFromClient.class);
		
		String to = msg.getTo();
		String content = msg.getMsg();
		
		 MsgToClient msgToClient = new MsgToClient();
		 msgToClient.setMessage(username,content);
		 System.out.println(to);
		
		 
		 curCustomerNames = getSession(to).getCurCustomers();
		 serverNames = getSession(to).getServerNames();
		 msgToClient.setCurcustomerNames(curCustomerNames);
		 msgToClient.setServerNames(serverNames);
		 msgToClient.setServername(to);
		
		 if (id.equals("customer")){
			 sendToServer(msgToClient.toJson(),to);
				sendToCustomer(msgToClient.toJson(),username);
		 }else{
			 sendToCustomer(msgToClient.toJson(),to);
			 msgToClient.setServername(username);
			 sendToServer(msgToClient.toJson(),username);
		 }
        
    }
	
	
	@OnClose
	public void close(Session session){
		
		if(id.equals("customer")){
			this.customerconnections.remove(this);
			this.customerNames.remove(this.username);
			MsgToClient msgser = new MsgToClient();
			msgser.setCustomerNames(customerNames);
			msgser.setServerNames(serverNames);
			curCustomerNames = getSession(currentServerName).getCurCustomers();
			curCustomerNames.remove(username);	
			msgser.setCurcustomerNames(curCustomerNames);
			msgser.setServername(currentServerName);
			msgser.setMessage(String.format("Customer:%s is quit now.\n", username));
			sendToServer(msgser.toJson(),currentServerName);
		}
	}

	
	
	
	
	
	
	
	
    
    public static int RandomNumber(int min, int max){
    	return ((new Random()).nextInt(max)%(max-min+1) + min);
    }
    
    
    public ChatAnnotation getSession(String name){
    	
    	ChatAnnotation c = null;
    	    	
    		for(ChatAnnotation client: serverconnections){
        		if(client.username.equals(name)){
        			c = client;
        			break;
        		}
        	}
        	        	
    	
    		for(ChatAnnotation client: customerconnections){
        		if(client.username.equals(name)){
        			
        			c = client;
        			break;
        		}
        	}        	
        	
        return c;
    }
    
    
    
    public void sendToCustomer(String msg, String customername){
    	for (ChatAnnotation customer : customerconnections) {
    		
    		if(customer.username.equals(customername)){
    			
    			 try {
		                synchronized (customer) {
		                    customer.session.getBasicRemote().sendText(msg);
		                    
		                }
		            } catch (IOException e) {
		                System.out.println("Chat Error: Failed to send message to client"+e);
		                customerconnections.remove(customer);
		                try {
		                    customer.session.close();
		                } catch (IOException e1) {
		                    // Ignore
		                }
		                String message = String.format("* %s %s",
		                        customer.username, "has been disconnected.");
		                sendToCustomer(message,this.username);
		            }    
    			 break;
    		}
    		
		          
        }
    }
    
    public void sendToServer(String msg, String servername){
    	
		for (ChatAnnotation server : serverconnections) {
		    		
		    		if(server.username.equals(servername)){
		    			
		    			 try {
				                synchronized (server) {
				                    server.session.getBasicRemote().sendText(msg);
				                    
				                }
				            } catch (IOException e) {
				                System.out.println("Chat Error: Failed to send message to client"+e);
				                customerconnections.remove(server);
				                try {
				                    server.session.close();
				                } catch (IOException e1) {
				                    // Ignore
				                }
				                String message = String.format("* %s %s",
				                        server.username, "has been disconnected.");
				                sendToCustomer(message,this.username);
				            }    
		    			 break;
		    		}
		    		
				          
		        }
    }
    
    public void broadcast(String msg, Set<ChatAnnotation> group){
    	for (ChatAnnotation client : group) {
    			 try {
		                synchronized (client) {
		                    client.session.getBasicRemote().sendText(msg);
		                    
		                }
		            } catch (IOException e) {
		                System.out.println("Chat Error: Failed to send message to client"+e);
		                customerconnections.remove(client);
		                try {
		                    client.session.close();
		                } catch (IOException e1) {
		                    // Ignore
		                }
		                String message = String.format("* %s %s",
		                        client.username, "has been disconnected.");
		                broadcast(message,group);
		            }    
        }
    }
    
    public void addCustomer(String name){
    	this.curCustomerNames.add(name);
    }
    
    public ArrayList<String> getCurCustomers(){
    	return this.curCustomerNames;
    }
    
    public ArrayList<String> getServerNames(){
    	
    	return this.serverNames;
    }
//    
//    private static void privateChat(String des, String msg){
//        for (ChatAnnotation client : connections) {
//        	
//        	if(client.username.equals(des)){
//        		try {
//                    synchronized (client) {
//                        client.session.getBasicRemote().sendText(msg);
//                        
//                    }
//                } catch (IOException e) {
//                    System.out.println("Chat Error: Failed to send message to client"+e);
//                    connections.remove(client);
//                    try {
//                        client.session.close();
//                    } catch (IOException e1) {
//                        // Ignore
//                    }
//                    String message = String.format("* %s %s",
//                            client.username, "has been disconnected.");
//                    broadcast(message);
//                }
//        		
//        		break;
//        	}
//        }
//    }

}
