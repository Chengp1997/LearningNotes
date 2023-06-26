import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Client {
	
	
	private static int p1,p2;
	private static String ip;
	private static Socket commandSocket;
	private static Socket dataSocket;
	private static String localPath = "F:\\FtpClient\\";
	private static String serverPath = "F:\\FtpServer\\";
	private static String massageFromServerStr = null;
	private static String feedbackToServer = null;
	private static BufferedReader commandMassageFromServer;
	private static PrintWriter commandFeedbackToServer;
	
	
	public static void main(String[] args){
		
		/*
		 * Initializing the client folder
		 * if there is a folder named "FtpClient", do nothing in this part
		 * if there no folder named "FtpClient", create a folder
		 * the folder is used for file transmission
		 */
		localPath = "F:\\FtpClient";
		File clientFolder = new File(localPath);

		if (!localPath.endsWith(File.separator)&&!clientFolder.exists()) {
			localPath = localPath + File.separator;
			clientFolder.mkdirs();
		}
		
		

		try {
			commandSocket = new Socket("localhost",6666);//port 21 is used on server for command transmission
			
			/*
			 * use BufferedReader to get command information from server,
			 * and PrintWriter to send command to server
			 */
			commandMassageFromServer = new BufferedReader(new InputStreamReader(commandSocket.getInputStream()));
			commandFeedbackToServer = new PrintWriter(new OutputStreamWriter(commandSocket.getOutputStream()));
			
			
			do{
		
				/*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>when the client read from the server<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/
				
				massageFromServerStr = commandMassageFromServer.readLine();
				System.out.println(massageFromServerStr);
				
				
				switch(Integer.valueOf(massageFromServerStr.toString().substring(0,3))){
					
					case 221:
						commandMassageFromServer.close();
						commandFeedbackToServer.close();
						dataSocket.close();
						System.exit(0);
					break;
				
					case 212:
						System.out.println("Directory status");
						break;
					
					
					case 220:
						System.out.println("FTP Server is ready");
						break;
					
						
					case 230:
						System.out.println("User logged in, proceed");
						break;
						
						
					case 331:
						System.out.println("User name okay, need password");
						break;
						
						
					case 550:
						System.out.println("Requested action not taken.File unavailable.");
						break;
				
						
					case 226:
						System.out.println("Transfer complete");
						break;
						
						
					case 227://reply of PASV
						
						ip = massageFromServerStr.substring(massageFromServerStr.indexOf('(')+1,massageFromServerStr.indexOf(')'));

						/*
						 * get the info of p1,p2,ip address in the command code
						 */
						 p1 = Integer.valueOf(ip.substring(getCharacterPosition(ip,4,',')+1, getCharacterPosition(ip,5,',')));
						 p2 = Integer.valueOf(ip.substring(getCharacterPosition(ip,5,',')+1,ip.length()));
						 ip = ip.substring(0,getCharacterPosition(ip,4,',')).replace(',', '.');

						break;
												
						
					default:							
						break;
				}
				

				
				
				/*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>when the client feedback to the server<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/
				
				Scanner scanner = new Scanner(System.in);
				feedbackToServer = scanner.nextLine();

				
				switch(feedbackToServer.substring(0,4)){
				
					case "CWD":
						commandFeedbackToServer.println(feedbackToServer);
						commandFeedbackToServer.flush();
						break;
						
					
					case "LIST":
						commandFeedbackToServer.println(feedbackToServer);
						commandFeedbackToServer.flush();
						dataSocket = new Socket(ip,p1*256+p2);//create a socket in client, to link to data socket on server(not temporary)
//						System.out.println("...");
						
						BufferedReader dataReaderFromServer = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
						PrintWriter dataWriterToServer = new PrintWriter(new OutputStreamWriter(dataSocket.getOutputStream()));
						
						String s = null;
						do{
							s = dataReaderFromServer.readLine();
							if(s!=null){System.out.println(s);}								
						}while(s!=null);
						
						dataReaderFromServer.close();
						dataWriterToServer.close();
						dataSocket.close();
						
						break;
						
						
					case "STOR":
						
//						localPath="F:\\FtpClient\\1\\";
						String fileToUoload = feedbackToServer.substring(5,feedbackToServer.length());
						File fileToServer =  new File(localPath+File.separator+fileToUoload);
						if(!fileToServer.exists()){// if file cannot be found in the client
							System.out.println("File cannot be found.");
							break; 
						}
						
						String path = localPath;
						
						copyAndStore( fileToServer , commandFeedbackToServer, commandMassageFromServer,path);

						break;
						
						
					
					case "RETR":		
						commandFeedbackToServer.println(feedbackToServer);
						commandFeedbackToServer.flush();
						String fileToDownloadName = feedbackToServer.substring(5,feedbackToServer.length());
						File rec;						
						String str= commandMassageFromServer.readLine();
						
						if(str.startsWith("125")){//found the file in server
							if(localPath.endsWith("\\")){
								System.out.println("with \\");
								rec = new File(localPath+fileToDownloadName);
							}else{
								rec = new File(localPath+File.separator+fileToDownloadName);
							}
							
							dataSocket = new Socket(ip,p1*256+p2);

							BufferedInputStream dataFromServer = new BufferedInputStream(dataSocket.getInputStream());
							BufferedOutputStream dataToStoreInFile = new BufferedOutputStream(new FileOutputStream(rec));
							
							/*
							 * write data from server to file
							 */
							int d;
							while((d = dataFromServer.read())!=-1){
								dataToStoreInFile.write(d);
								dataToStoreInFile.flush();
							}
							
							dataFromServer.close();
							dataToStoreInFile.close();
							dataSocket.close();							
						}						
					break;
					
					
					case "QUIT":		
						commandFeedbackToServer.println(feedbackToServer);
						commandFeedbackToServer.flush();
						break;
					
					
					default:				
						commandFeedbackToServer.println(feedbackToServer);
						commandFeedbackToServer.flush();
						break;			
				}
				
				
			}while(!feedbackToServer.substring(0,4).equals("QUIT"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * to get a character's index in a String, in which condition it shows up a certain ( given ) times
	 * 
	 * @param string   the string
	 * @param i   times
	 * @param character  the character
	 * @return the index
	 */
	public static int getCharacterPosition(String string ,int i,char character){  
	       char[] s = string.toCharArray();
	       int index = 0;
		   int times = 0;
	       for(char c :s){
	    	   if(times == i)
	    		   break;
	    	   if(c == character)
	    		   times++;
	    	   index++ ;
    	   }
	       return index-1;
     }  
	
	
	
	
	
	public static void copyAndStore(File file, PrintWriter commandFeedbackToServer, BufferedReader commandMassageFromServer,String path){
		
		try {
			
			if(file.isFile()){
				
				commandFeedbackToServer.println("SIZE "+file.getName());
				commandFeedbackToServer.flush();
				String b = commandMassageFromServer.readLine();
				System.out.println(b);
				
				
				if(Integer.parseInt(b)==0){//size = 0
					commandFeedbackToServer.println("STOR "+file.getName());
					commandFeedbackToServer.flush();
					
					dataSocket = new Socket(ip,p1*256+p2);
									
					BufferedInputStream dataFromFile = new BufferedInputStream(new FileInputStream(file));
					BufferedOutputStream dataToServer = new BufferedOutputStream(dataSocket.getOutputStream());
					
					/*
					 * write data from file to server
					 */
					byte[] bys = new byte[1024];
					int len = 0;
					while((len = dataFromFile.read(bys))!=-1){
						dataToServer.write(bys, 0, len);
						dataToServer.flush();
					}
					
					dataFromFile.close();
					dataToServer.close();
					dataSocket.close();
					
					
				}else{//size>0
					int bits = Integer.parseInt(b);
					
					commandFeedbackToServer.println("STOR "+file.getName());
					commandFeedbackToServer.flush();
					dataSocket = new Socket(ip,p1*256+p2);
									
					BufferedInputStream dataFromFile = new BufferedInputStream(new FileInputStream(file));
					BufferedOutputStream dataToServer = new BufferedOutputStream(dataSocket.getOutputStream());
					
					dataFromFile.skip(bits);
					/*
					 * write data from file to server
					 */
					byte[] bys = new byte[1024];
					int len = 0;
					while((len = dataFromFile.read(bys))!=-1){
						dataToServer.write(bys, 0, len);
						dataToServer.flush();
					}
					
					dataFromFile.close();
					dataToServer.close();
					dataSocket.close();
				}
								
				
			}else if(file.isDirectory()){
				
				commandFeedbackToServer.println("STOR "+file.getName()+file.separator);
				commandFeedbackToServer.flush();
								
				commandFeedbackToServer.println("CWD "+serverPath+file.separator+file.getName());
				commandFeedbackToServer.flush();
				commandMassageFromServer.readLine();
				
				if(!path.endsWith("\\")){
					path = path+file.separator;
				}
				
				path = path + file.getName()+file.separator;
				
				 String[] filelist = file.list();
				 	for (int i = 0; i < filelist.length-1; i++) {
				 		File readfile = new File(path+ filelist[i]);	
				 		
				 		copyAndStore(readfile, commandFeedbackToServer, commandMassageFromServer,path);
				 		System.out.println(commandMassageFromServer.readLine());
				 	}				 	
				 	
				 	
				 	
				 	File readfile = new File(path+ filelist[filelist.length-1]);
				 	
				 	if(!readfile.isDirectory()){
				 	
				 		commandFeedbackToServer.println("STOR "+readfile.getName());
				 		commandFeedbackToServer.flush();
					
				 		dataSocket = new Socket(ip,p1*256+p2);
				 		
				 		BufferedInputStream dataFromFile = new BufferedInputStream(new FileInputStream(readfile));
				 		BufferedOutputStream dataToServer = new BufferedOutputStream(dataSocket.getOutputStream());
					
				 		/*
				 		 * write data from file to server
				 		 */
				 		byte[] bys = new byte[1024];
						int len = 0;
						while((len = dataFromFile.read(bys))!=-1){
							dataToServer.write(bys, 0, len);
							dataToServer.flush();
						}			
				 		dataFromFile.close();
				 		dataToServer.close();
				 		dataSocket.close();
				 	}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}





	