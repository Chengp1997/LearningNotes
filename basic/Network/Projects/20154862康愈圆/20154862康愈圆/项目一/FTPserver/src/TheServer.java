import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TheServer {
	
	/**
	 * An empty constructor
	 */
	public TheServer(){
		
	}
	
	public static void main(String[] args){
		
		try{
			ServerSocket commandSocket = new ServerSocket(6666); //prepare PORT 21 for command transmission

			while(true){
				
				/*
				 * prepare a temporary PORT for command transmission. 
				 * If this code passed, command sockets of server and client are linked
				 */
				Socket tempCommandSocket = commandSocket.accept();
				
				System.out.println("Linked successfully!");
				ServerThreadCommand commandServerThread = new ServerThreadCommand(tempCommandSocket); 
				commandServerThread.start();
			}
			
		} catch(IOException e){
			e.printStackTrace();
		}		
	}
	
	
}
