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
	public String remoteFolderPath = "D:/eclipse/JAVA/FTPServer/Remote";// Զ���ļ���Ŀ¼
	public String localFolderPath = "D:/eclipse/JAVA/FTPClient/Local";// �����ļ���Ŀ¼
	Socket dataSocket;// �������ݵ�Socket
	DataInputStream dis;// �������ݵ�·��
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
		/* ����socket����,������������ */
		socket = new Socket(host, port);
		Scanner u=new Scanner(System.in);
		Scanner p=new Scanner(System.in);
		/*
		 * ��������������շ���˵���Ϣ��socket.getInputStream),��ת�����ֽ��������Ч��(inputStreamReader)
		 * ,ʹ�û�����(BufferedReader)
		 */
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		/* ����������������Ϣ������ˣ���ת�����ֽ����� */
		writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		writer.println("�������ã����ǿͻ�");
		writer.flush();
		reponse = reader.readLine();
		System.out.println(reponse);

		/* �����������USER,PASS�����½FTP������ */
		while(true){
		System.out.println("User_Name: ");
		user=u.next();
		writer.println("USER " + user);
		writer.flush();
		reponse = reader.readLine();// ����ʱ��Ӧ�յ�331 User name okay, need password.
		System.out.println(reponse);
		if(reponse.substring(0, 3).equals("530")){
			continue;
		}
		System.out.println("Password: ");
		pass=p.next();
		writer.println("PASS " + pass);
		writer.flush();
		reponse = reader.readLine(); // ����ʱӦ�յ� 230 logged on
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
		/* �Ͽ����ӣ�������QUIT�����˳� */
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
		/* ʹ��List�������ļ��б� */
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
		String localPathName = localFolderPath + "/" + downloadFileName;// ��Ϊ�жϱ����Ƿ���ڴ��ļ�
		String remotePathName = remoteFolderPath + "/" + downloadFileName;// ��Ϊ����˵��ļ�
		File file = new File(localPathName);
		/* ���жϱ����Ƿ���ڴ��ļ�������ļ����ڣ�����REST Size�������ƫ�������ٽ������أ�����ֱ������ */
		long size = file.length();
		int offset=(int) size;
		if (file.exists()) {
			
//			writer.println("SIZE "+remotePathName);
//			writer.flush();
//			reponse=reader.readLine();
//			System.out.println(reponse);
//			long fileSize=Long.parseLong(reponse.substring(11));
//			System.out.println(fileSize);
			/* ����ļ������ڣ��򷵻�-1,����ļ����ڣ��򷵻��ļ��Ĵ�С */
			if (size > 0) {
//				if(size<fileSize){
				writer.println("REST " + size);
				writer.flush();
				reponse = reader.readLine();}
//			}
		} else {
			file.createNewFile();
		}
		/* ʹ��RETR �������ļ� */
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
		String localPathName = localFolderPath + "/" + uploadFileName;// ��Ϊ�жϱ����Ƿ���ڴ��ļ�
		String remotePathName = remoteFolderPath + "/" + uploadFileName;// ��Ϊ����˵��ļ�
		File file = new File(remotePathName);
		long size = file.length();
		int offset=(int) size;
		
		if (file.exists()) {
			
			System.out.println("Size of the file: " + size);
			/* ����ļ������ڣ��򷵻�-1,����ļ����ڣ��򷵻��ļ��Ĵ�С */
			if (size > 0) {
				writer.println("REST " + size);
				writer.flush();
				reponse = reader.readLine();
			}
		} else {
			System.out.println("not");
			file.createNewFile();
		}
		// �����ݴ��ļ��ж�ȡ����
		File localFile = new File(localPathName);
		if (localFile.exists()) {
			InputStream fileIn=new FileInputStream(localFile);
			//ʹ��skip�������ڵĵط�
			fileIn.skip(offset);
			DataInputStream fis = new DataInputStream(new BufferedInputStream(fileIn));
			byte[] buffer = new byte[4096];
			int readByte = 0;
			while ((readByte = fis.read(buffer)) != -1) {
				dos.write(buffer, 0, readByte);
			}
			dos.close();
			fis.close();

			/* ʹ��STOR���� ���ϴ��ļ� */
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
		/* ʹ��PASV������뱻��ģʽ */
		writer.println("PASV");
		writer.flush();
		reponse = reader.readLine(); // 227 entering passive mode
										// (h1,h2,h3,h4,p1,p2)
		System.out.println(reponse);
		/* ���IP��ַ��port,ʹ��StringTokenizer�����ַ����ֶ� */
		int first = reponse.indexOf('(');
		int last = reponse.indexOf(')');
		if (first != -1 || last != -1) {
			String subString = reponse.substring(first + 1, last);
			StringTokenizer st = new StringTokenizer(subString, ",");
			this.dataHost = st.nextToken() + "." + st.nextToken() + "." + st.nextToken() + "." + st.nextToken();
			this.dataPort = Integer.parseInt(st.nextToken()) * 256 + Integer.parseInt(st.nextToken());
		}
		/* �������ݶ˿ڣ�׼���������� */
		Socket dataSocket = new Socket(dataHost, dataPort);
		/* �����ݶ˿ڽ������� */
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