import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Client {

	/**
	 * Socket for Client
	 */
	public Socket socket;
	public String dataHost;
	public int dataPort;
	public String reponse;
	public BufferedReader reader;
	public PrintWriter writer;
	public String remoteFolderPath = "D:/eclipse/JAVA/FTPServer/Remote";// 远程文件夹目录
	public String localFolderPath = "D:/eclipse/JAVA/FTPClient/Local";// 本地文件夹目录
	Socket dataSocket;// 接收数据的Socket
	DataInputStream dis;// 接收数据的路径
	DataOutputStream dos;

	/**
	 * rewrite the function connect, using the fixed user name and the password
	 * 
	 * @param host
	 *            the IP address of the server
	 * @param port
	 *            the port prepared to transfer the data
	 * @throws UnknownHostException
	 * @throws IOException
	 */
//	public synchronized void connect(String host, int port) throws UnknownHostException, IOException {
//		connect(host, port, "anonymous", "anonymous");
//	}

	/**
	 * connect to the server
	 * 
	 * @param host
	 *            the IP address of the server
	 * @param port
	 *            the port prepared to transfer the data
	 * @param user
	 *            the user to be connect to the server
	 * @param pass
	 *            the password of the user
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public synchronized void connect(String host, int port)
			throws UnknownHostException, IOException {
		String user,pass;
		/* 创建socket对象,用来传输命令 */
		socket = new Socket(host, port);
		Scanner u=new Scanner(System.in);
		Scanner p=new Scanner(System.in);
		/*
		 * 获得输入流，接收服务端的信息（socket.getInputStream),并转换成字节流，提高效率(inputStreamReader)
		 * ,使用缓冲区(BufferedReader)
		 */
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		/* 获得输出流，发送信息给服务端，并转换成字节流。 */
		writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		writer.println("服务端你好，我是客户");
		writer.flush();
		reponse = reader.readLine();
		System.out.println(reponse);

		/* 向服务器发送USER,PASS命令登陆FTP服务器 */
		while(true){
		System.out.println("User_Name: ");
		user=u.next();
		writer.println("USER " + user);
		writer.flush();
		reponse = reader.readLine();// 正常时，应收到331 User name okay, need password.
		System.out.println(reponse);
		if(reponse.substring(0, 3).equals("530")){
			continue;
		}
		System.out.println("Password: ");
		pass=p.next();
		writer.println("PASS " + pass);
		writer.flush();
		reponse = reader.readLine(); // 正常时应收到 230 logged on
		System.out.println(reponse);
		if(reponse.substring(0,3).equals("530")){
			continue;}else{
				break;
			}
		}
	}

	/**
	 * to quit the program
	 * 
	 * @throws UnknownHostException
	 */
	public synchronized void quit() throws IOException {
		/* 断开连接，并发出QUIT命令退出 */
		writer.println("QUIT " + "\r\n");
		writer.flush();
		reponse = reader.readLine();
		System.out.println(reponse);
		return;
	}

	/**
	 * list all the file of a folder
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public synchronized void listFile() throws UnknownHostException, IOException {
		enterPASVMode();
		/* 使用List命令获得文件列表 */
		Scanner input = new Scanner(System.in);
		String path;
		System.out.println("Please input the list that you want to see\n1.-------Local\n2.-------Remote");
		String name = input.next();
		if (name.equals("1")||name.equals("Local")) {
			writer.println("CWD Local");
			writer.flush();
			reponse=reader.readLine();
			System.out.println(reponse);
			path =localFolderPath;
		} else if (name.equals("2")||name.equals("Remote")) {
			writer.println("CWD Remote");
			writer.flush();
			reponse=reader.readLine();
			System.out.println(reponse);
			path = remoteFolderPath;
		} else {
			System.out.println("invalid input,try it again");
			dis.close();
			return;
		}
		writer.println("LIST " + path);
		writer.flush();
		reponse = reader.readLine();
		System.out.println(reponse);
		String s = "";
		System.out.println("Here is the list of the folder\n---------------------------");
		while (!(s = dis.readLine()).equals("")) {
			String l = new String(s.getBytes("ISO-8859-1"), "utf-8");
			System.out.println(l);
		}
		System.out.println("------------------------");

		dis.close();
	}

	/**
	 * download the file of a folder
	 * 
	 * @param fileName
	 *            the name of the file to be download
	 * @throws IOException
	 */
	public synchronized void downloadFile(String downloadFileName) throws IOException {
		enterPASVMode();
		String localPathName = localFolderPath + "/" + downloadFileName;// 此为判断本地是否存在此文件
		String remotePathName = remoteFolderPath + "/" + downloadFileName;// 此为服务端的文件
		File file = new File(localPathName);
		/* 先判断本地是否存在此文件，如果文件存在，则发送REST Size命令，设置偏移量，再进行下载，否则，直接下载 */
		long size = file.length();
		int offset=(int) size;
		if (file.exists()) {
			
//			writer.println("SIZE "+remotePathName);
//			writer.flush();
//			reponse=reader.readLine();
//			System.out.println(reponse);
//			long fileSize=Long.parseLong(reponse.substring(11));
//			System.out.println(fileSize);
			/* 如果文件不存在，则返回-1,如果文件存在，则返回文件的大小 */
			if (size > 0) {
//				if(size<fileSize){
				writer.println("REST " + size);
				writer.flush();
				reponse = reader.readLine();}
//			}
		} else {
			file.createNewFile();
		}
		/* 使用RETR 来下载文件 */
		writer.println("RETR " + remotePathName);
		writer.flush();
		reponse = reader.readLine();
		System.out.println(reponse);
			OutputStream dos = new FileOutputStream(localPathName,true);
			// in.close();
			String s = "";
			while ((s = dis.readLine())!=null) {
				String l = new String(s.getBytes("ISO-8859-1"), "utf-8");
				System.out.println(l);
				dos.write(s.getBytes());
			}
		dos.close();
		dis.close();

	}

	/**
	 * upload the file
	 * 
	 * @param fileName
	 *            the file to be uploaded
	 * @throws IOException
	 */
	public synchronized void uploadFile(String uploadFileName) throws IOException {
		enterPASVMode();
		String localPathName = localFolderPath + "/" + uploadFileName;// 此为判断本地是否存在此文件
		String remotePathName = remoteFolderPath + "/" + uploadFileName;// 此为服务端的文件
		File file = new File(remotePathName);
		long size = file.length();
		int offset=(int) size;
		
		if (file.exists()) {
			
			System.out.println("Size of the file: " + size);
			/* 如果文件不存在，则返回-1,如果文件存在，则返回文件的大小 */
			if (size > 0) {
				writer.println("REST " + size);
				writer.flush();
				reponse = reader.readLine();
			}
		} else {
			System.out.println("not");
			file.createNewFile();
		}
		// 把数据从文件中读取出来
		File localFile = new File(localPathName);
		if (localFile.exists()) {
			InputStream fileIn=new FileInputStream(localFile);
			//使用skip跳过存在的地方
			fileIn.skip(offset);
			DataInputStream fis = new DataInputStream(new BufferedInputStream(fileIn));
			byte[] buffer = new byte[4096];
			int readByte = 0;
			while ((readByte = fis.read(buffer)) != -1) {
				dos.write(buffer, 0, readByte);
			}
			dos.close();
			fis.close();

			/* 使用STOR命令 来上传文件 */
			writer.println("STOR " + remotePathName);
			writer.flush();
			reponse = reader.readLine();
			System.out.println(reponse);
		} else {
			System.out.println("not e");
		}
		dis.close();

	}

	/**
	 * enter passive mode, set the IP and the Port
	 * 
	 * @throws IOException
	 */
	public synchronized void enterPASVMode() throws IOException {
		/* 使用PASV命令进入被动模式 */
		writer.println("PASV");
		writer.flush();
		reponse = reader.readLine(); // 227 entering passive mode
										// (h1,h2,h3,h4,p1,p2)
		System.out.println(reponse);
		/* 获得IP地址，port,使用StringTokenizer，将字符串分段 */
		int first = reponse.indexOf('(');
		int last = reponse.indexOf(')');
		if (first != -1 || last != -1) {
			String subString = reponse.substring(first + 1, last);
			StringTokenizer st = new StringTokenizer(subString, ",");
			this.dataHost = st.nextToken() + "." + st.nextToken() + "." + st.nextToken() + "." + st.nextToken();
			this.dataPort = Integer.parseInt(st.nextToken()) * 256 + Integer.parseInt(st.nextToken());
		}
		/* 连接数据端口，准备接收数据 */
		Socket dataSocket = new Socket(dataHost, dataPort);
		/* 从数据端口接收数据 */
		dis = new DataInputStream(dataSocket.getInputStream());
		dos = new DataOutputStream(dataSocket.getOutputStream());
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		Client a = new Client();
		a.connect("127.0.0.1", 8888);
		Scanner input = new Scanner(System.in);
		Scanner name = new Scanner(System.in);
		String temp, choose;
		boolean i = true;
		while (i) {
			System.out.println("Please choose the mode you want:");
			System.out.println("LIST ------ListFile\n" + "RETR ------DownloadFile\n" + "STOR ------UploadFile\n"
					+ "QUIT ------Quit");
			choose = input.next().toUpperCase();
			switch (choose) {
			case "LIST":
				a.listFile();
				break;
			case "RETR":
				System.out.println("Please input the name of the file you want to download:");
				temp = name.next();
				a.downloadFile(temp);
				break;
			case "STOR":
				System.out.println("Please input the name of the file you want to upload");
				temp = name.nextLine();
				a.uploadFile(temp);
				break;
			case "QUIT":
				a.quit();
				i = false;
				break;
			default:
				System.out.println("invalid input, try again!");
				break;
			}
		}
		name.close();
		input.close();
	}

}