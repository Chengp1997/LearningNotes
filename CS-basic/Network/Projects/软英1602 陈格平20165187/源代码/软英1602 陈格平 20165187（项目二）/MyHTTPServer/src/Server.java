import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(80);
            System.out.println("Hello!");
            while (true) {
                Socket socket = serverSocket.accept();// ���������ܵ����׽��ֵ�����,����һ��Socket����
                SocketThread socketThread = new SocketThread(socket);
                socketThread.start();
            }   
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
//			serverSocket.close();
		}
	}

}
