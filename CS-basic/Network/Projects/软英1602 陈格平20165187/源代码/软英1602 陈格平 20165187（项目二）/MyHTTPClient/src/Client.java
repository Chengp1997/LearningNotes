import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
	public int port;
	private static Socket s;
	public static PrintStream writer;
	public static DataInputStream reader;
	public static String saveLocation = "D:/";

	public static void main(String[] args) throws UnknownHostException, IOException {
		System.out.println("please input the address");
		Scanner input = new Scanner(System.in);
		String address = input.nextLine();// e.g.
											// http://www.lib.neu.edu.cn/index.html
		input.close();
		// 对输入的网址进行操作，截取需要的部分
		String hostName = null;
		String filename = "";
		// 如果是网站
		if (address.startsWith("http://") || address.startsWith("www")) {
			if (address.startsWith("http://")) {
				address = address.substring(7);// e.g.
												// www.lib.neu.edu.cn/index.html
			}
			hostName = address.split("/")[0];
			for (int i = 1; i < address.split("/").length; i++) {
				filename = filename + "/" + address.split("/")[i];
			}
		} else {
			// 如果是本地的地址
			int length = address.split("/").length;

			filename = "/" + address.split("/")[length - 1];// D:/eclipse/JAVA/MyHTTPServer/WebApp/neu.htm
			hostName = "127.0.0.1";
		}

		// 连接服务端并发送请求头
		sendRequest(filename, hostName);

		// 做出反应,获得信息，写入文件
		if (filename.endsWith("html") || filename.endsWith("htm")) {
			System.out.println(filename);
			// 新建文件夹
			File newFile = new File(saveLocation + "/" + filename.substring(0, filename.indexOf(".")) + "_files");
			// saveLocation=saveLocation+"/"+filename.substring(1,
			// filename.indexOf("."))+"_files";
			// filename=filename.substring(1);
			newFile.mkdir();

			response(filename, hostName);
			// 读取html文件
			String html = readHTMLfile(saveLocation + filename);
			String[] img = getImgaddress(html);
			for (int j = 0; j < img.length; j++) {
				System.out.println(img[j]);
				sendRequest("/" + img[j], hostName);
				response(img[j], hostName);
			}
			File list[]=newFile.listFiles();
			if(list.length==0){
				newFile.delete();
			}
		} else {
			response(filename, hostName);
		}
		System.out.println("transfer terminated!");

		//如果文件夹不存在，则删除
		
	}

	/**
	 * connect to the server
	 * 
	 * @param inetAddress
	 *            the host address of the server
	 * @param port
	 *            the port for the server and the client to communicate
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	// public void connect(InetAddress inetAddress, int port) throws
	// UnknownHostException, IOException {
	// // 连接服务器
	//
	// this.s= new Socket(inetAddress, port);
	// // 绑定通信端口
	// writer = new PrintStream(s.getOutputStream());
	// reader = new DataInputStream(s.getInputStream());
	// }

	/**
	 * send GET request to the Server
	 * 
	 * @param filename
	 *            the filename of the file to be requested
	 * @param hostName
	 *            the host name of the client's host
	 * @throws IOException
	 */
	public static void sendRequest(String filename, String hostName) throws IOException {
		// 连接服务端
		s = new Socket(InetAddress.getByName(hostName), 80);
		writer = new PrintStream(s.getOutputStream());
		reader = new DataInputStream(s.getInputStream());
		// 发送请求
		writer.println("GET " + filename + " HTTP/1.1");
		writer.println("Host:127.0.0.1");
		writer.println("connection:keep-alive");
		writer.println();
		writer.flush();
	}

	/**
	 * get message from the Server
	 * 
	 * @return the message from the Server
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public static String getMessage(String hostName) throws IOException {
		String Line = "";
		String reponse;
		while ((reponse = reader.readLine()) != null) {
			Line += reponse + "\n";
			if (reponse.equals("")) {
				break;

			}
		}
		return Line;
	}

	/**
	 * write data file into the local file
	 * 
	 * @param filename
	 *            the name of the file
	 * @throws IOException
	 */
	public static void writeFile(String filename, String hostName) throws IOException {
		byte[] b = new byte[1024];
		// 写入文件中
		// System.out.print(filename);
		// System.out.println(saveLocation+"1111"+"/"+filename);
		try {
			OutputStream out = new FileOutputStream(saveLocation + "/" + filename);
			int len = reader.read(b);
			while (len != -1) {
				out.write(b, 0, len);
				len = reader.read(b);
			}
			out.close();
		} catch (FileNotFoundException e) {
			System.out.print("File can not be downloaded, it's a link!\n\n");
		}

	}

	/**
	 * response from the server, to get the message from the server and write
	 * the data into the file
	 * 
	 * @param filename
	 *            the file to be written into the file
	 * @param hostName
	 *            the hostName of the client
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public static void response(String filename, String hostName) throws IOException {
		// 接收响应状态
		String Line1 = "";
		Line1 = reader.readLine();// HTTP/1.1 200 OK
		String Line = getMessage(hostName);
		System.out.print("Transmiting……\n");
		// 读取响应数据，做出反应，写入文件中
		if (Line1.substring(9).startsWith("200")) {
			// 响应成功时
			writeFile(filename, hostName);
			System.out.print(Line1 + "\n" + Line + "\n");
		} else if (Line1.substring(9).startsWith("404")) {
			System.out.println(Line1 + "\n" + Line + "\n");
			System.out.println("file not found! terminated!");
		}
		reader.close();
		writer.close();
	}

	/**
	 * get all the image src of the file
	 * 
	 * @param s
	 * @return 所有img的语句//<img src="assets/images/roll_5.jpg" />
	 */
	public static List<String> getImg(String s) {
		String regex;
		List<String> list = new ArrayList<String>();
//		regex = "src=\"(.*?)\"";
		regex = "src=\"(.*?)\"|ref=\"(.*?)\"";
		Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
		Matcher ma = pa.matcher(s);
		while (ma.find()) {
			list.add(ma.group());
		}
//		System.out.println(list);   //获得所有文件的名称  [ref="favicon.ico", ref="neu_files/base.css", ref="neu_files/common.css", ……
		return list;// 所有文件的名字
	}

	/**
	 * 获得图片的名字组成的String数组
	 * 
	 * @param tar
	 *            html文件内容
	 * @return 图片名字
	 */
	public static String[] getImgaddress(String tar) {
		List<String> imgList = getImg(tar);
		String res[] = new String[imgList.size()];
		if (imgList.size() > 0) {
			for (int i = 0; i < imgList.size(); i++) {
				int begin = imgList.get(i).indexOf("\"") + 1;
				int end = imgList.get(i).lastIndexOf("\"");
				String url[] = imgList.get(i).substring(begin, end).split("'");
				// System.out.println(i);
				// System.out.println("imgList"+imgList.size());
				// System.out.println("url"+url.length);
//				System.out.println(url[url.length - 1]);  //获得所有截取后的文件  的名称
				if (url.length == 0) {
					continue;
				}
				res[i] = url[url.length - 1];
				// System.out.println(res[i]);
			}
		} else {
		}
		return res;
	}

	/**
	 * 读取HTML文件
	 * 
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String readHTMLfile(String filePath) throws FileNotFoundException, IOException {
		System.out.println(filePath);
		File file = new File(filePath);
		InputStream input = new FileInputStream(file);

		StringBuffer buffer = new StringBuffer();
		byte[] bytes = new byte[1024];
		for (int n; (n = input.read(bytes)) != -1;) {
			buffer.append(new String(bytes, 0, n, "GBK"));
		}
		return buffer.toString();
	}

}
