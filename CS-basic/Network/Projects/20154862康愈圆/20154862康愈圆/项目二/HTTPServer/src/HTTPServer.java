import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTPServer {
	
	public static void main(String[] args) {
		

		ServerSocket ss = null;
		try{
			ss = new ServerSocket(80);
		}catch(IOException e){
			e.printStackTrace();
		};
		while(true){
			try{
				Socket s = ss.accept();
				System.out.println(">>"+s.getRemoteSocketAddress()+"is linked");
				ServerThread t = new ServerThread(s);
				t.start();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
	}
	
}
