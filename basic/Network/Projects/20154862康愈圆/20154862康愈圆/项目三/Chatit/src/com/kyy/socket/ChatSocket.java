package com.kyy.socket;

import java.io.IOException;
import java.util.ArrayList;
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
import com.sun.corba.se.spi.logging.LogWrapperFactory;

import sun.rmi.runtime.Log;

@ServerEndpoint("/chatSocket")
public class ChatSocket {
	
	
	private String username;
	private String password;
	private Session session;
	private static final Set<ChatSocket> connections = new CopyOnWriteArraySet();
	private static ArrayList<String> names = new ArrayList<String>();
	private static Gson gson = new Gson();//

	@OnOpen
	public void open(Session session){
		
		this.session = session;
		String query = session.getQueryString();
		System.out.println(query);
		connections.add(this);
		 username = (query.split("=")[1]).split("&")[0];
		 password = query.split("=")[2];
		 names.add(username);
		 
		 MsgToClient message = new MsgToClient();
		 
		 message.setMessage(String.format("* %s %s \n", username, "has joined."));
		 message.setUsernames(names);
		 
		
        broadcast(message.toJson());
   
	}
	
	@OnMessage
    public void incoming(String message) {
        
		MsgFromClient msg = gson.fromJson(message, MsgFromClient.class);
		
		String des = msg.getDestication();
		String content = msg.getMessageContent();
		
		 MsgToClient msgToCleint = new MsgToClient();
		 msgToCleint.setMessage(username,content);
		 msgToCleint.setUsernames(names);
		
		if(des.equals("GroupChat")){
		     broadcast(msgToCleint.toJson());
		}else{
			privateChat(des,msgToCleint.toJson());
			privateChat(username,msgToCleint.toJson());
		}
        
        
    }
	
	
	@OnClose
	public void close(Session session){
		this.connections.remove(this);
		this.names.remove(this.username);
		MsgToClient message = new MsgToClient();
		message.setUsernames(names);
		message.setMessage(String.format("* %s %s \n", username, "has quit."));
		
		broadcast(message.toJson());
	}

	
	
	
    private static void broadcast(String msg) {
    	
        for (ChatSocket client : connections) {
            try {
                synchronized (client) {
                    client.session.getBasicRemote().sendText(msg);
                    
                }
            } catch (IOException e) {
                System.out.println("Chat Error: Failed to send message to client"+e);
                connections.remove(client);
                try {
                    client.session.close();
                } catch (IOException e1) {
                    // Ignore
                }
                String message = String.format("* %s %s",
                        client.username, "has been disconnected.");
                broadcast(message);
            }
        }
    }
    
    private static void privateChat(String des, String msg){
        for (ChatSocket client : connections) {
        	
        	if(client.username.equals(des)){
        		try {
                    synchronized (client) {
                        client.session.getBasicRemote().sendText(msg);
                        
                    }
                } catch (IOException e) {
                    System.out.println("Chat Error: Failed to send message to client"+e);
                    connections.remove(client);
                    try {
                        client.session.close();
                    } catch (IOException e1) {
                        // Ignore
                    }
                    String message = String.format("* %s %s",
                            client.username, "has been disconnected.");
                    broadcast(message);
                }
        		
        		break;
        	}
        }
    }
    

}
