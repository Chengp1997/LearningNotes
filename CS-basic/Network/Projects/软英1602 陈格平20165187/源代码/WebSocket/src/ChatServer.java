import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import com.google.gson.Gson;

@ServerEndpoint(value = "/websocket/chat")
public class ChatServer {
	private static final Set<ChatServer> customerConnections = new CopyOnWriteArraySet<ChatServer>();// 用户列表
	private static final Set<ChatServer> serverConnections = new CopyOnWriteArraySet<ChatServer>();
    private static ArrayList<String> curCustomerNames=new ArrayList<>();
	private static ArrayList<String> customerNames = new ArrayList<>();
	private static ArrayList<String> serverNames = new ArrayList<>();
	private String server;
	private String username = "user";
	private String password;
	private String type;
	private Session session;
	// private String destination="all";//destination初始化为all，当目的改变时
	private Gson gson = new Gson();// 新建Gson对象用来格式化信息

	/**
	 * 连接对话
	 *
	 * @param session
	 */
	@OnOpen
	public void start(Session session) {
		this.session = session;
		String query = session.getQueryString();
		// System.out.println(query);
		this.username = (query.split("=")[1]).split("&")[0];
		this.type = query.split("=")[3];
		// System.out.println(type);
		// 建立新的连接
		ToClient toClient = new ToClient();
		if (type.equals("customer")) {

			customerConnections.add(this);
			customerNames.add(username);

			String message = String.format("* %s %s ", username, "has joined.");
			if(serverNames.size()==0){
				 message=message+"\n"+"no servers available now!";
				 toClient.setMessage(message);
				 curCustomerNames.add(username);
					toClient.setCurcustomerNames(curCustomerNames);
					toClient.setCustomerList(curCustomerNames);
					toClient.setServerList(serverNames);
					toClient.setName(username);
					
					String jsonMessage = toClient.toJson();
					System.out.println(jsonMessage);
					System.out.println(username);
				 
				 broadcast(jsonMessage,customerConnections);
			}else{
				// 将message变为对象，将信息包装起来
			server = serverNames.get(RandomNumber(0,serverNames.size()));//当前要为你服务的server
			message=message+server+"will help you!";
			toClient.setMessage(message);
			
			toClient.setCustomerList(customerNames);
			toClient.setServerList(serverNames);
			toClient.setName(username);
			String jsonMessage = toClient.toJson();
			System.out.println(jsonMessage);
			System.out.println(username);
			broadcastToCustomer(jsonMessage,username);//告诉customer，有人为你服务
			
			ToClient t=new ToClient();
			curCustomerNames = getSession(server).getCurCustomers();
			curCustomerNames.add(username);	//告诉server，此为当前的人数，更新了！！！
			t.setCustomerList(curCustomerNames);
			t.setCurcustomerNames(curCustomerNames);
			t.setServerList(serverNames);
			t.setMessage(String.format("Customer:%s need your help.\n", username));
			broadcastToServer(t.toJson(), server);
			}
			
		} else if (type.equals("server")) {

			serverConnections.add(this);
			serverNames.add(username);
			
			toClient.setMessage("Hello! " + username);
			toClient.setName(username);
			toClient.setCustomerList(customerNames);
			toClient.setServerList(serverNames);
			String jsonMessage = toClient.toJson();
			System.out.println(jsonMessage);
			System.out.println(username);
			broadcast(jsonMessage, serverConnections);
		}
	}
	 public ChatServer getSession(String name){
	    	
		 ChatServer c = null;
	    	    	
	    		for(ChatServer client: serverConnections){
	        		if(client.username.equals(name)){
	        			c = client;
	        			break;
	        		}
	        	}
	        	        	
	    	
	    		for(ChatServer client: customerConnections){
	        		if(client.username.equals(name)){
	        			
	        			c = client;
	        			break;
	        		}
	        	}        	
	        	
	        return c;
	    }

	/**
	 * 关闭对话
	 */
	@OnClose
	public void end() {
		if (type.equals("customer")) {
			customerConnections.remove(this);
			customerNames.remove(this);
			ToClient toClient = new ToClient();
			toClient.setCustomerList(customerNames);
			String message = String.format("* %s %s", username, "has disconnected.");
			broadcast(message, customerConnections);

		} else {
			serverConnections.remove(this);
			serverNames.remove(this);
			String message = String.format("* %s %s", username, "has disconnected.");
			broadcast(message, serverConnections);
		}
		// ToClient toClient = new ToClient();
		// toClient.setUsernames(names);
		// String message = String.format("* %s %s", username, "has
		// disconnected.");
		// broadcast(message);
	}

	/**
	 * 接收消息，进行处理
	 *
	 * @param message
	 * @param session
	 */
	@OnMessage
	public void incoming(String message, Session session) {
		if (type.equals("server") || type.equals("customer")) {
			System.out.println(message);
			FromClient fromClient = new FromClient();
			// 通过Gson,将来自于ToClient的message传给FromClient来调用
			fromClient = gson.fromJson(message, FromClient.class);
			// 此为gson转码后返回的东西
			String msg = fromClient.getMessage();
			// 此为要格式化的东西,最终传过去的语句
			String filteredMessage = String.format("* %s: %s", username, msg);
			String destination = fromClient.getDestination();
			System.out.println(filteredMessage);

			ToClient msgToClient = new ToClient();
			// 转换成Json语句
			msgToClient.setMessage(filteredMessage);
			msgToClient.setCustomerList(curCustomerNames);
			msgToClient.setServerList(serverNames);
			String m = msgToClient.toJson();
			System.out.println(m);
			if (destination.equals("all")) {
				System.out.println("11111111");
				// 若向所有人发送，则是普通的broadcast，否则发给destination
				broadcast(m, serverConnections);
				broadcast(m, customerConnections);
			} else {
				System.out.println("00000000");
				System.out.println(m + "   " + destination);
				//此处需要判断，是发给顾客还是发给客服
				if(customerNames.contains(destination)){
					//如果传的人是顾客，则都要传
					broadcastToCustomer(m,destination);
					broadcastToCustomer(m,username);
				}else{
					broadcastToServer(m,destination);
					broadcastToCustomer(m,username);
				}
				
			}
		} else {

		}

	}

	@OnError
	public void onError(Throwable t) throws Throwable {
	}

	// 发送给所有人
	private void broadcast(String msg, Set<ChatServer> connections) {
		// if (type.equals("customer")) {
		for (ChatServer people : connections) {
			try {
				synchronized (people) {
					people.session.getBasicRemote().sendText(msg);
				}
			} catch (IOException e) {
				connections.remove(people);
				try {
					people.session.close();
				} catch (IOException e1) {
					// Ignore
				}
				String message = String.format("* %s %s", people.username, "has been disconnected.");
				broadcast(message, connections);
			}
		}
	}

	public static int RandomNumber(int min, int max) {
		return ((new Random()).nextInt(max) % (max - min + 1) + min);
	}

	private void broadcastToCustomer(String msg, String destination) {
		System.out.println(msg);
		System.out.println(username);
		for (ChatServer client : customerConnections) {
			System.out.println("asdf");
			if (destination.equals(client.username)) {
				System.out.println(msg + 11111111);
				try {
					synchronized (client) {
						System.out.println("Aaaaa");
						client.session.getBasicRemote().sendText(msg);
					}
				} catch (IOException e) {
					customerConnections.remove(client);
					try {
						client.session.close();
					} catch (IOException e1) {
						// Ignore
					}
					String message = String.format("* %s %s", client.username, "has been disconnected.");
					broadcast(message, customerConnections);
				}
//				if (client.session.equals(this.session)) {
//					break;
//				}
			}
		}

	}

	private void broadcastToServer(String msg, String destination) {
		System.out.println(msg);
		System.out.println(username);
		for (ChatServer server : serverConnections) {
			System.out.println("asdf");
			if (destination.equals(server.username)) {
				System.out.println(msg + 11111111);
				try {
					synchronized (server) {
						System.out.println("Aaaaa");
						server.session.getBasicRemote().sendText(msg);
					}
				} catch (IOException e) {
					serverConnections.remove(server);
					try {
						server.session.close();
					} catch (IOException e1) {
						// Ignore
					}
					String message = String.format("* %s %s", server.username, "has been disconnected.");
					broadcast(message, serverConnections);
				}
//				if (server.session.equals(this.session)) {
//					break;
//				}
			}
		}

	}
	public void addCustomer(String name){
    	this.curCustomerNames.add(name);
    }
    
    public ArrayList<String> getCurCustomers(){
    	return this.curCustomerNames;
    }
    
    public ArrayList<String> getServerNames(){
    	
    	return this.serverNames;
    }
}