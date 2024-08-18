import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		final int PORT=8888;
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(PORT);
            System.out.println("Hello!");
            while (true) {
                Socket socket = serverSocket.accept();// ���������ܵ����׽��ֵ�����,����һ��Socket����
                ClientThread clientThread = new ClientThread(socket);
                clientThread.start();
            }   
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
			serverSocket.close();
		}
	}

}
