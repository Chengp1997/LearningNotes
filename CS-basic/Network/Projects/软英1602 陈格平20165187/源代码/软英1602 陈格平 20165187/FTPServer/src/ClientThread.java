import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ClientThread extends Thread {
	private Socket socket;// 响应socket
	private Socket dataSocket;// 传输数据的socket
	private String command;// 响应命令
	private long offset;
	public String remoteFolderPath = "D:/eclipse/JAVA/FTPServer/Remote";// 远程文件夹目录
	public String localFolderPath = "D:/eclipse/JAVA/FTPClient/Local";// 本地文件夹目录
	public String dir="D:/eclipse/JAVA";
	final String USER_NAME = "anonymous";
	final String PASS_NAME = "anonymous";
	ServerSocket ss = null;
	Random generator = new Random(1000);
	int port1, port2;
	/* 用来传输数据的socket */
	PrintWriter dataWriter;

	public ClientThread(Socket socket) {
		this.socket = socket;
	}

	@SuppressWarnings("deprecation")
	public void run() {

		try {
			/* 当链接上时，对客户端和服务端做出反应,返回的为响应码！！ */
			/* 获得输入流，接收服务端的信息 */
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			/* 获得输出流，发送信息给服务端，并转换成字节流。 */
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			/* 打印欢迎信息 */
			String info = reader.readLine();
			System.out.println("Successful!");
			System.out.println("information" + info + ",IP:" + socket.getInetAddress().getHostAddress());
			writer.println("220 Hello!");
			writer.flush();

			while (true) {
				/* 接收不同命令，做出判断 */
				command = reader.readLine();
				System.out.println(command);
				if (command.toUpperCase().startsWith("USER")) {
					String user = command.substring(5);
					if (user.equals(USER_NAME)) {
						writer.println("331 Password required for anonymous");
						writer.flush();
					} else {
						writer.println("530 invalid USER_NAME");
						writer.flush();
					}
				} else if (command.toUpperCase().startsWith("PASS")) {
					String pass = command.substring(5);
					if (pass.equals(PASS_NAME)) {
						writer.println("230 Logged on");
						writer.flush();
					} else {
						writer.println("530 Login incorrect");
						writer.flush();
					}
				} else if (command.toUpperCase().startsWith("PASV")) {
					while (true) {
						// 自动生成，寻找闲置的端口
						port1 = 1 + generator.nextInt(20);
						port2 = 100 + generator.nextInt(1000);
						try {
							// 服务器绑定端口
							ss = new ServerSocket(port1 * 256 + port2);
							break;
						} catch (IOException e) {
							continue;
						}
					}
					InetAddress i = null;
					try {
						i = InetAddress.getLocalHost();
					} catch (UnknownHostException e1) {
						e1.printStackTrace();
					}
					writer.println("227 Entering Passive Mode(" + i.getHostAddress().replace(".", ",") + "," + port1
							+ "," + port2 + ")");
					writer.flush();
					/* 最后，将被动模式下，绑定的socket进行设置，以便后方传输数据 */
					this.dataSocket = ss.accept();
					/* 用来传输数据的socket */
					dataWriter = new PrintWriter(new OutputStreamWriter(dataSocket.getOutputStream()));
					
				} else if (command.toUpperCase().startsWith("SIZE")) {
					String filePath = command.substring(5);
					File file = new File(filePath);
					if (file.exists()) {
						long size = file.length();
						writer.println("213 File Size: "+size);
						writer.flush();
					} else {
						writer.println("213 File does not exist");
						writer.flush();
					}
				} else if (command.toUpperCase().startsWith("REST")) {
					String size = command.substring(5);
					this.offset=Long.parseLong(size);
					writer.println("250 set offset successfully");
					writer.flush();

				} else if (command.toUpperCase().startsWith("RETR")) {
					System.out.println("start retr");
					String pathName = command.substring(5);
					// 此为我要下载的文件的路径
					File RETRFile = new File(pathName);
					if(RETRFile.length()==offset){
						System.out.println("aaaaaa");
					}
					if (RETRFile.exists()) {
						writer.println("250 transfer OK");
						writer.flush();
						InputStream fileIn = new FileInputStream(RETRFile);
						System.out.println(offset);
						fileIn.skip(offset);//使用inputStream.skip(length)越过offset部分
						DataInputStream dis = new DataInputStream(new BufferedInputStream(fileIn));
						DataOutputStream dos=new DataOutputStream(dataSocket.getOutputStream());
						/*Transfer data*/
						byte[] buffer = new byte[4096];
	
						int readByte = 0;
						while((readByte=dis.read(buffer)) != -1){
							dos.write(buffer,0,readByte);
						}
						System.out.println("RETR SUCCESSFULLY");
						dos.flush();
						dos.close();
						dis.close();
					} else {
						writer.println("213  file dose not exist");
						writer.flush();
					}
				} else if (command.toUpperCase().startsWith("STOR")) {
					System.out.println("START STOR!");
					String uploadFileNmae=command.substring(5);
					File STORFile = new File(uploadFileNmae);
					DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(uploadFileNmae,true)));
					if (STORFile.exists()) {
						writer.println("250 transfer OK");
						writer.flush();
						//获取数据
						DataInputStream dis=new DataInputStream(dataSocket.getInputStream());
						
						/*Transfer data*/
						String s="";
						while((s = dis.readLine())!=null){
							String l = new String(s.getBytes("ISO-8859-1"), "utf-8");
							System.out.println(l);
							dos.write(s.getBytes());
						}	
					} else {
						writer.println("213 file not exist");
						writer.flush();
					}
					System.out.println("STOR SUCCESSFULLY");
					dos.close();
					
				} else if (command.toUpperCase().startsWith("QUIT")) {
					// 让当前线程停止
					ClientThread.currentThread();
					Thread.sleep(1000);
					// 返回信息，成功退出
					writer.println("Quit successfully");
					writer.flush();
					return;
				} else if (command.toUpperCase().startsWith("CWD")) {
					/* 获得要被修改的目录 */
					/* 此处应进行判断，1.若文件原本存在，则无需改变目录2.若文件不存在，应修改目录 */
					String folderPath = command.substring(4); // 为远程的文件夹
					String tempPath = dir+"/"+folderPath;
					File newFile = new File(tempPath);
					System.out.println("1");
					if (newFile.exists()) {
						System.out.println("2");
						writer.println("212 current directory"+tempPath);
						writer.flush();
					} else {
						System.out.println("3");
						writer.println("212 the directory does not exist");
						writer.flush();
					}

					dataWriter.println(tempPath);
					dataWriter.flush();

				} else if (command.toUpperCase().startsWith("LIST")) {
					/* 展示文件目录，包括文件的名称，文件大小，文件的创建时间 */
					String remoteFolderPath = command.substring(5);
					System.out.println(remoteFolderPath);
					File folder = new File(remoteFolderPath);
					if (folder.exists()) {
						writer.println("250 Transfer OK");
						writer.flush();
						// 如果文件存在，则获取当下目录的所有信息，并返回传送成功的信息
						getFileDetails(dataWriter, remoteFolderPath);
					} else {
						writer.println("213 The folder is not exits");
						writer.flush();
					}
				}
			}
		} catch (

		IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * to show all the details of the folder;
	 * 
	 * @param folderPath
	 * @return
	 */
	public static File[] getFileDetails(PrintWriter pw, String folderPath) {
		/* 展示文件目录，包括文件的名称，文件大小，文件的创建时间 */
		File folder = new File(folderPath);
		File f[] = folder.listFiles();
		String temp = "";
		System.out.println(f.length);
		for (int i = 0; i < f.length; i++) {
			temp = temp + "Name: " + f[i].getName() + " Size: " + f[i].length() + " bytes" + " Date: "
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(f[i].lastModified())) + "\n";
		}
		pw.println(temp);
		pw.flush();
		System.out.println(temp);
		return f;
	}
	public static void main(String[] args) {

	}

}
