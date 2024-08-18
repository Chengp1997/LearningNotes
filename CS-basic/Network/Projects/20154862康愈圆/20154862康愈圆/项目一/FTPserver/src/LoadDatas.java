import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class LoadDatas {
	private static ArrayList<User> users;
	
	static{
		try{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("user data.dat"));
			users = (ArrayList<User>)ois.readObject();
			ois.close();
			
		}catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public static ArrayList<User> getUsers(){
		return users;
	}
}
