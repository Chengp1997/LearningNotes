import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;

public class Browser {
	public static void main(String[] args) {		
	
				
		try {
			//找到服务器地址，建立连接
			Scanner s = new Scanner(System.in);
			System.out.print("请输入地址：");
			String address = s.nextLine();
			
			if(address.startsWith("http://")){
				address = address.substring(7, address.length());
			}
			
			String[] addr = address.split("/");
			String host = addr[0];
			String fileLongName  = "";
			for(int i = 1; i<addr.length; i++){
				fileLongName +=("/"+ addr[i]);
			}
		
			Socket socket = new Socket(host,80);//120.92.240.130
						
//			Socket socket = new Socket("localhost", 6666);
			//发送请求头			
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			pw.println("GET "+fileLongName+" HTTP/1.1");
			pw.println("Host: "+addr[0]);
			pw.println("Connection: closed");
			pw.println("Accept-Encoding: deflate, sdch");
			pw.println();
			pw.flush();
			//接收响应			
			DataInputStream dis = new DataInputStream(socket.getInputStream());	
			
			
			//读取第一行，判断是否满足200 OK
			ArrayList<Character> ary = new ArrayList<>();			
			int b = 0;
			while(true){
				b = dis.read();
				ary.add((char)b);
				if (b=='\n')
					break;
			}
			String line = "";
			for(char c : ary){
				line += String.valueOf(c);
			}
			System.out.print(line);
			
				//读取剩下的几行反应头
			while(readALine(dis));
			
			
			
			//如果200 OK， 则开始文件传输
			if(line.contains("200 OK")){
				String fileShortName = addr[addr.length-1];
				FileOutputStream fout = new FileOutputStream("F:\\HTTPClient\\"+fileShortName);			
				
				byte[] bys = new byte[1024];
				int len = 0;
				while((len = dis.read(bys))!=-1){
					fout.write(bys,0,len);
					fout.flush();				
				}	
				fout.close();
				System.out.println("Transmission completed!");
			}
				
			socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static boolean readALine(InputStream in) throws IOException{
		boolean tag  =true;
		ArrayList<Character> ary = new ArrayList<>();		
		int b = 0;
		while(true){
			b = in.read();
			ary.add((char)b);
			if (b=='\n')
				break;
		}
		String line = "";
		for(char c : ary){
			line += String.valueOf(c);
		}
		System.out.print(line);
		if(line.equals("\r\n"))
			tag = false;
		return tag;
	}
	
	
	
}

//【案例】
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>东北大学网站下载html
//http://neunews.neu.edu.cn/campus/rencaipeiyang/2017-03-03/54675.html		
//http://aao.neu.edu.cn/ueditor/jsp/upload/20170305/28471488768609224.doc
		
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>从红动中国下载图片
//img.redocn.com/sheji/20161130/shishangshuicaiyedianKTVjiubapaiduikuhaibao_7523915_small.jpg
		

//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>从网上下载歌曲feded.mp3
//m9.music.126.net/20170305210243/e3b1586a8d0f22229f71ce207ee893c7/ymusic/559f/a6e9/8196/13f58c3f617d74f783e3f00923c0498b.mp3

//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>从网上下载网易云音乐的安装包
//s1.music.126.net/download/pc/cloudmusicsetup_2_1_2[168028].exe

//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>从红动中国下载html页面		
//www.redocn.com/shop/4680259/similarzj/1000000181.htm
//http://www.redocn.com/shop/3839344/similarzj/1000000895.htm
