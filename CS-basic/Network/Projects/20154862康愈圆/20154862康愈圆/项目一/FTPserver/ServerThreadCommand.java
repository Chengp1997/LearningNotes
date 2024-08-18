import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class ServerThreadCommand extends Thread{
	/*
	 * the socket is used to store the information of temporary command socket on server
	 * an ArrayList named "users" used to store the information of all users
	 */
	private Socket commandSocket;
	private ServerSocket dataSocket = null;
	private ArrayList<User> users = new ArrayList<>();
	private String hostIP = null;
	private String currentPath = "f:\\FtpServer\\";
	int size = 0;
	
	
	int dataPort  = 0;
	int p1=0,p2=0;
	
	/**
	 * a constructor
	 * take the socket as a parameter, and store it.
	 * @param s
	 */
	public ServerThreadCommand(Socket s){
		this.commandSocket = s;
	}
	

	
	public void run(){
		
		try {
			
			String commandMassageFromClientStr;
			
			BufferedReader commandReaderFromClient = new BufferedReader(new InputStreamReader(commandSocket.getInputStream()));
			PrintWriter commandWriterToClient = new PrintWriter(new OutputStreamWriter(commandSocket.getOutputStream()));
			
			users = LoadDatas.getUsers();//load users' data
			
			User currentUser=new User();//as a tag and data storage. Judge if a user is logged on. Store the user's info if logged on
			
			
			commandWriterToClient.println("220 FTP Server ready");
			commandWriterToClient.flush();
			
			
			
			do
			{
				
				commandMassageFromClientStr=commandReaderFromClient.readLine();
				System.out.println(commandMassageFromClientStr);
				
				switch(commandMassageFromClientStr.substring(0,4)){ //get the first 4 characters and decide what to do according to the command
				
				
				
							
					case "USER":
						String userID = commandMassageFromClientStr.substring(5,commandMassageFromClientStr.length()); //to get the user's ID number in the total command
						
						currentUser = getTheUser(userID);
						
						System.out.println(currentUser.getID());
						
						if(currentUser.getID()!= null){
							commandWriterToClient.println("331 User name okay, need password");
							commandWriterToClient.flush();
						}else{
							commandWriterToClient.println("503 Wrong ID number or password");
							commandWriterToClient.flush();
						}
						break;
					
						
						
						
										
					case "PASS":
						if(currentUser.getID()!= null){// if id is ok
							
							String passwordToCheck = commandMassageFromClientStr.substring(5,commandMassageFromClientStr.length());//to get the user's password
							if(currentUser.getPassword().equals(passwordToCheck)){//if password is okay
								commandWriterToClient.println("230 user logged in, proceed.");
								commandWriterToClient.flush();								
							}else{
								commandWriterToClient.println("503 Wrong ID number or password");
								commandWriterToClient.flush();
							}							
						}else{
							commandWriterToClient.println("503 Wrong ID number or password");
							commandWriterToClient.flush();
						}						
						break;
						
						
						
						
					case "LIST":						
						if(dataSocket!=null&&p1!=0&&p2!=0){							
							Socket tempDataSocket = dataSocket.accept(); 							
							BufferedReader dataReaderFromClient = new BufferedReader(new InputStreamReader(tempDataSocket.getInputStream()));
							PrintWriter dataWriterToClient = new PrintWriter(new OutputStreamWriter(tempDataSocket.getOutputStream()));
														
							if(!currentPath.endsWith("\\")){
								currentPath += File.separator;
							}
							
							System.out.println(currentPath);
							
							File file = new File(currentPath);
							 
							 String[] filelist = file.list();
							 
							 System.out.println(filelist.length);
							 
							 
							 	for (int i = 0; i < filelist.length; i++) {
							 		File readfile = new File(currentPath+ filelist[i]);
							        if (!readfile.isDirectory()) {
							        	dataWriterToClient.printf("<FILE>         %-80s%-20s%-20s\n",readfile.getPath(),getFileSize(readfile),getModifiedTime(readfile));
							        	dataWriterToClient.flush();							        	
							        } else if (readfile.isDirectory()) {
							            dataWriterToClient.printf("<DIR>          %-80s%-20S%-20s\n",readfile.getPath(),getFileSize(readfile),getModifiedTime(readfile));
							            dataWriterToClient.flush();
							        }
							  }
							 tempDataSocket.close();
							 commandWriterToClient.println("111 finished");////////////////////////////////
							 commandWriterToClient.flush();
						}else{
							commandWriterToClient.println("426 Connection closed; transfer aborted.");//no data port on server are created
							commandWriterToClient.flush();
						}
						break;
					
										
						
					case "CWD ":
						currentPath = commandMassageFromClientStr.substring(4,commandMassageFromClientStr.length());
						if (!currentPath.endsWith("\\")){
							currentPath += File.separator;
						}
						
						commandWriterToClient.println("212 Directory status");
						commandWriterToClient.flush();
						break;
						
						
						
						
					case "PASV":						
						if(currentUser.getID()==null||currentUser.getPassword()==null){// if not logged on
							System.out.println("...");
							commandWriterToClient.println("332 Need account for login.");
							commandWriterToClient.flush();
						}else{														
							try	{ 						
								hostIP = InetAddress.getLocalHost().getHostAddress();
								hostIP = hostIP.replace('.', ',');																
								if(dataSocket!=null&&p1!=0&&p2!=0){//data socket is created																		
									commandWriterToClient.println("227 entering passive mode ("+hostIP+","+p1+","+p2+")");
									commandWriterToClient.flush();									
								}else{
																		
									/*
									 * prepare for data
									 * use random number to get a useful port and get p1,p2
									 * open a port according to the port number we get
									 */
									
									do{
										dataPort = getRandomInteger(1025,65535);
										p1 = getRandomInteger(0,256);
										p2 = dataPort-256*p1;
									}while(p1<0||p2<0||isLoclePortUsing(dataPort));
									commandWriterToClient.println("227 entering passive mode ("+hostIP+","+p1+","+p2+")");
									commandWriterToClient.flush();
									dataSocket = new ServerSocket(dataPort);			
								}
								
							} catch (UnknownHostException e){ 
								e.printStackTrace();
							}catch(IOException e){
								e.printStackTrace();
							}
						}
						break;
						
						
						
					case "PORT":
						
						break;
						
						
						
						
					case "RETR":
						
						String fileName = commandMassageFromClientStr.substring(5,commandMassageFromClientStr.length());
						File currentFile = new File(currentPath+fileName);
						if(!currentFile.exists()){
							commandWriterToClient.println("550 Requested action not taken.File unavailable (e.g., file not found, no access).");
							commandWriterToClient.flush();							
							commandWriterToClient.println("550 Requested action not taken.File unavailable (e.g., file not found, no access).");
							commandWriterToClient.flush();
						}else{
							commandWriterToClient.println("125 Data connection already open; transfer starting");
							commandWriterToClient.flush();							
							Socket tempDataSocket = dataSocket.accept(); 
							
							BufferedInputStream bis = new BufferedInputStream(new FileInputStream(currentFile));
							BufferedOutputStream bos = new BufferedOutputStream(tempDataSocket.getOutputStream());
							
							int c;
							while((c = bis.read())!=-1){
								bos.write(c);
								bos.flush();
							}
							
							bis.close();
							bos.close();
							System.out.println("...");
							commandWriterToClient.println("226 Closing data connection.Requested file action successful ");
							commandWriterToClient.flush();
						}
						break;
						
					
					
					
					
					case "SIZE":
						String fileNameSize = commandMassageFromClientStr.substring(5,commandMassageFromClientStr.length());
						File currentFileSize = new File(currentPath+fileNameSize);
						
						
//						BufferedInputStream thefile = new BufferedInputStream(new FileInputStream(currentFileSize));
						System.out.println("222");
						size = Integer.parseInt(String.valueOf(currentFileSize.length()));
						commandWriterToClient.println(currentFileSize.length());
						commandWriterToClient.flush();
						
						
						break;
						
					case "STOR":
						if(dataSocket!=null&&p1!=0&&p2!=0){//data socket is created
							/*
							 * create a temporary port according to the port we get using random number
							 * if this code is passed the data link is established
							 */
							
						
							
							if(!commandMassageFromClientStr.endsWith("\\")){
								
								Socket tempDataSocket = dataSocket.accept(); 
								if(!currentPath.endsWith("\\")){
									currentPath+=File.separator;
								}
								
//								System.out.println(currentPath);
								String fileNameStor = commandMassageFromClientStr.substring(5,commandMassageFromClientStr.length());
								File currentFileStor = new File(currentPath+fileNameStor);

								
								
								BufferedInputStream bis = new BufferedInputStream(tempDataSocket.getInputStream());
								BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(currentFileStor,true));
								
							
								byte[] bys = new byte[1024];
								int len = 0;
								while((len = bis.read(bys))!=-1){
									bos.write(bys, 0, len);
									bos.flush();
								}
								
								
								System.out.println("2");
								
								commandWriterToClient.println("226 transfer complete");
								commandWriterToClient.flush();
								bis.close();
								bos.close();
							}else{
								String fileNameStor = commandMassageFromClientStr.substring(5,commandMassageFromClientStr.length());
								File currentFileStor = new File(currentPath+fileNameStor);
								currentFileStor.mkdir();
															
								
							}
							
						}else{//data socket closed 
							commandWriterToClient.println("426 Connection closed; transfer aborted.");//no data port on server are created
							commandWriterToClient.flush();
							commandWriterToClient.close();
						}				
						
						break;
						
						
						
					case "REST":
						
						break;
						
						
						
						
						
					case "QUIT":
						//how to quit?
						currentUser.setID(null);
						currentUser.setPassword(null);
						commandWriterToClient.print("221 Service closing control connection.Logged out if appropriate.");
						commandWriterToClient.flush();
						commandWriterToClient.close();
						commandReaderFromClient.close();
						commandSocket.close();
					break;
						
						
						
						
					default:
						commandWriterToClient.println("500 Syntax error, command unrecognized.");
						commandWriterToClient.flush();
						break;
						
				}
				
				
			}while(!commandMassageFromClientStr.equals("QUIT"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * When enter the user's ID number,
	 * search it in the collection (users).
	 * If there is a user whose ID number is as entered,
	 * return the user.
	 * If not,
	 * return null.
	 * @param userID(type->String)
	 * @return an object of the user, or null
	 */
	public User getTheUser(String userID){
		
		User theUser = new User();
		boolean hasTheUser = false;
		
		for(User testUser : users){
			if(testUser.getID().equals(userID)){
				theUser=testUser;
				hasTheUser = true;
				break;
			}
		}
		return theUser;
	}
	
	
	/**
	 * to judge if a port is occupied
	 * @param port
	 * @return
	 */
	 public static boolean isLoclePortUsing(int port){  
	        boolean flag = true;  
	        try {  
	            flag = isPortUsing("localhost", port);  
	        } catch (Exception e) {  
	        }  
	        return flag;  
	    }  
	 
	 public static boolean isPortUsing(String host,int port) throws UnknownHostException{  
	        boolean flag = false;  
	        InetAddress theAddress = InetAddress.getByName(host);  
	        try {  
	            Socket socket = new Socket(theAddress,port);  
	            flag = true;  
	        } catch (IOException e) {  
	              
	        }  
	        return flag;  
	    }  
	 
	 
	 /**
	  * to get a random number according to certain limitation
	  * @param min
	  * @param max
	  * @return
	  */
	 public static int getRandomInteger(int min, int max){
		 Random random = new Random();
		return random.nextInt(max)%(max-min+1) + min;
	 }
	 
	 
	 public static int countFile(String path, DataOutputStream dataDOS){
		 
		 int sum = 0;
		 try{
			 File file = new File(path);
				File[] list = file.listFiles();
				for(int i = 0; i<list.length;i++){
					
					if(list[i].isFile()){
						sum++;
					}else{
						sum += countFile(list[i].getPath(), dataDOS);
						System.out.println(list[i].getPath());
					}
					
					
				}
			 
		 }catch(NullPointerException e){
			 e.printStackTrace();
		 }
		 
		 
			return sum;
	 }
	 
	 
	 
	 
	 /**
	  * to get a file's create date
	  * @param f
	  * @return a string
	  */
	 @SuppressWarnings("deprecation")
	public static String getModifiedTime(File f){   
		Calendar cal = Calendar.getInstance();   
		long time = f.lastModified();   
		cal.setTimeInMillis(time);     
		return  cal.getTime().toLocaleString();
	} 
	 
	 
	 /**
	  * to get a file's size
	  * @param f
	  * @return
	  */
	 public static String getFileSize(File f){
		 long s = f.length();
		 double size = Double.valueOf(s);
		 
		 if(size>=0&&size<1024){
			 return String.format("%10.4f B", size);
		 }else if(size>=1024&&(size/1024.0)<1024){
			 return String.format("%10.4f KB", size/1024.0);
		 }else if((size/1024.0)>=1024&&(size/1024.0/1024.0)<1024){
			 return String.format("%10.4f MB", size/1024.0/1024.0);
		 }else if((size/1024.0/1024.0)>=1024&&((size/1024.0/1024.0/1024)<1024)){
			 return String.format("%10.4f GB", size/1024.0/1024.0/1024.0);
		 }else if((size/1024.0/1024.0/1024)>=1024&&(size/1024.0/1024.0/1024.0/1024.0)<1024){
			 return String.format("%10.4f TB", size/1024.0/1024.0/1024.0/1024.0);
		 }else{
			 return "too large";
		 }
	 }
	

	 
	 
}
