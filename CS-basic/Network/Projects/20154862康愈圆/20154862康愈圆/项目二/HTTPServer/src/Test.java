import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.StandardSocketOptions;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
	public static void main(String[] args) {
		
//		File f = new File("C:\\Users\\Kang Yuyuan\\Desktop\\test\\main.c");
//		

		
		
		
//		 Date dt= new Date();
//		 Long time= dt.getTime();//这就是距离1970年1月1日0点0分0秒的毫秒数
//		  System.out.println(System.currentTimeMillis());//与上面的相同
//		
//		  
//			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//			System.out.println(sd.format(new Date(time)));
			
			
			
			
			
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			String dateString = formatter.format(new Date().getTime());
			System.out.println(dateString);
			
			
			
			
	}
		
}

