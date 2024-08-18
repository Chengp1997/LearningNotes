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
	private Socket socket;// ��Ӧsocket
	private Socket dataSocket;// �������ݵ�socket
	private String command;// ��Ӧ����
	private long offset;
	public String remoteFolderPath = "D:/eclipse/JAVA/FTPServer/Remote";// Զ���ļ���Ŀ¼
	public String localFolderPath = "D:/eclipse/JAVA/FTPClient/Local";// �����ļ���Ŀ¼
	public String dir="D:/eclipse/JAVA";
	final String USER_NAME = "anonymous";
	final String PASS_NAME = "anonymous";
	ServerSocket ss = null;
	Random generator = new Random(1000);
	int port1, port2;
	/* �����������ݵ�socket */
	PrintWriter dataWriter;

	public ClientThread(Socket socket) {
		this.socket = socket;
	}

	@SuppressWarnings("deprecation")
	public void run() {

		try {
			/* ��������ʱ���Կͻ��˺ͷ����������Ӧ,���ص�Ϊ��Ӧ�룡�� */
			/* ��������������շ���˵���Ϣ */
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			/* ����������������Ϣ������ˣ���ת�����ֽ����� */
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			/* ��ӡ��ӭ��Ϣ */
			String info = reader.readLine();
			System.out.println("Successful!");
			System.out.println("information" + info + ",IP:" + socket.getInetAddress().getHostAddress());
			writer.println("220 Hello!");
			writer.flush();

			while (true) {
				/* ���ղ�ͬ��������ж� */
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
						// �Զ����ɣ�Ѱ�����õĶ˿�
						port1 = 1 + generator.nextInt(20);
						port2 = 100 + generator.nextInt(1000);
						try {
							// �������󶨶˿�
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
					/* ��󣬽�����ģʽ�£��󶨵�socket�������ã��Ա�󷽴������� */
					this.dataSocket = ss.accept();
					/* �����������ݵ�socket */
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
					// ��Ϊ��Ҫ���ص��ļ���·��
					File RETRFile = new File(pathName);
					if(RETRFile.length()==offset){
						System.out.println("aaaaaa");
					}
					if (RETRFile.exists()) {
						writer.println("250 transfer OK");
						writer.flush();
						InputStream fileIn = new FileInputStream(RETRFile);
						System.out.println(offset);
						fileIn.skip(offset);//ʹ��inputStream.skip(length)Խ��offset����
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
						//��ȡ����
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
					// �õ�ǰ�߳�ֹͣ
					ClientThread.currentThread();
					Thread.sleep(1000);
					// ������Ϣ���ɹ��˳�
					writer.println("Quit successfully");
					writer.flush();
					return;
				} else if (command.toUpperCase().startsWith("CWD")) {
					/* ���Ҫ���޸ĵ�Ŀ¼ */
					/* �˴�Ӧ�����жϣ�1.���ļ�ԭ�����ڣ�������ı�Ŀ¼2.���ļ������ڣ�Ӧ�޸�Ŀ¼ */
					String folderPath = command.substring(4); // ΪԶ�̵��ļ���
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
					/* չʾ�ļ�Ŀ¼�������ļ������ƣ��ļ���С���ļ��Ĵ���ʱ�� */
					String remoteFolderPath = command.substring(5);
					System.out.println(remoteFolderPath);
					File folder = new File(remoteFolderPath);
					if (folder.exists()) {
						writer.println("250 Transfer OK");
						writer.flush();
						// ����ļ����ڣ����ȡ����Ŀ¼��������Ϣ�������ش��ͳɹ�����Ϣ
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
		/* չʾ�ļ�Ŀ¼�������ļ������ƣ��ļ���С���ļ��Ĵ���ʱ�� */
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
