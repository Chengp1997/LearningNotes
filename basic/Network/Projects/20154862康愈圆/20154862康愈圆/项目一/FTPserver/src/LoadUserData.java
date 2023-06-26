import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class LoadUserData {
	public static ArrayList<User> users;

	
	public static void main(String[] args){
		
		users = new ArrayList<User>();
		
		User u1 = new User("20150001","000000");
		User u2 = new User("20150002","000000");
		User u3 = new User("20150003","000000");
		User u4 = new User("20150004","000000");
		User u5 = new User("20150005","000000");
		User u6 = new User("20150006","000000");
		User u7 = new User("20150007","000000");
		User u8 = new User("20150008","000000");
				
		users.add(u1);
		users.add(u2);
		users.add(u3);
		users.add(u4);
		users.add(u5);
		users.add(u6);
		users.add(u7);
		users.add(u8);

		ObjectOutputStream output;
		try{
			File userInfo = new File("user data.dat");
			if(userInfo.exists()){
				userInfo.delete();
				userInfo.createNewFile();
			}else{
				userInfo.createNewFile();
			}
			
			output = new ObjectOutputStream(new FileOutputStream(userInfo));
			output.writeObject(users);
			output.flush();
			output.close();
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
}
