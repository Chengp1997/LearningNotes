
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class SocketThread extends Thread {

	private Socket socket;
	DataInputStream dis;
	PrintStream writer;
	FileInputStream in;
//	String path = "D:/eclipse/JAVA/MyHTTPServer/WebApp";// ��Ϊ�ļ�����·��

	public SocketThread(Socket socket) {
		this.socket = socket;
	}
/**
 * �����߳�
 */
	@SuppressWarnings("deprecation")
	public void run() {
		try {
			dis = new DataInputStream(socket.getInputStream());
			writer = new PrintStream(socket.getOutputStream());

			String firstLineOfRequest;
			if ((firstLineOfRequest = dis.readLine()).startsWith("GET")) {
				// ʹ��split������������ı��ķָ��ȡ��һ����Ϣ����Ϊ������ļ�������
				String filename = null;
				String Line = "";
				String reponse;
				while ((reponse = dis.readLine()) != null) {
					Line += reponse + "\n";
					if (reponse.equals("")) {
						break;
					}
				}
				System.out.println(firstLineOfRequest + "\n" + Line);
				filename = firstLineOfRequest.split(" ")[1];

//				path = path + filename;
//System.out.println(System.getProperty("user.dir")+"/WebApp"+filename+"9999999999");
				// �ж�GET ����������µ��ļ��Ƿ���ڣ�������ڣ��򷵻�200 ok�����򣬷���404NOT FOUND
				File file = new File(System.getProperty("user.dir")+"/WebApp"+filename);
				if (file.exists()) {
					System.out.println("exists!");
					// �����һ���������ļ�
					if (file.isFile()) {
						in = new FileInputStream(System.getProperty("user.dir")+"/WebApp"+filename);
						// ��Ӧͷ
						writer.println("HTTP/1.1 200 OK");
						if (filename.endsWith(".html")) {
							writer.println("Content-Type:text/html");
						} else if (filename.endsWith(".htm")) {
							writer.println("Content-Type:text/htm");
						} else if (filename.endsWith(".jpg")) {
							writer.println("Content-Type:image/jpeg");
						} else if (filename.endsWith(".JPG")) {
							writer.println("Content-Type: image/JPG");
						} else if (filename.endsWith(".png")) {
							writer.println("Content-Type: image/png");
						} else if (filename.endsWith(".gif")) {
							writer.println("Content-Type: image/gif");
						}else if(filename.endsWith(".css")){
						    writer.println("Content-Type: text/html");	
						}else if(filename.endsWith(".js")){
							writer.println("Content-Type:application/javascript");
						}else {
							writer.println("Content-Type:application/octet-stream");
						}
						writer.println("Content-Length:" + in.available());
						writer.println();// ���н�����Ϣ
						writer.flush();

						// ��Ӧ��,�����ļ�������
						byte[] b = new byte[1024];
						int len = 0;
						len = in.read(b);
						while (len != -1) {
							writer.write(b, 0, len);
							len = in.read(b);
						}
						writer.flush();
						writer.close();
						dis.close();
					} else {

					}
				} else {
					writer.println("HTTP/1.1 404 Not Found");
					writer.println("Content-Type:text/plain");
					writer.println("Content-Length:14");
					writer.println();

					// ��Ӧ��
					writer.print("Not Found");
					writer.flush();
					writer.close();
					dis.close();
				}

			} else if (dis.readLine().startsWith("POST")) {

			} else {

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
