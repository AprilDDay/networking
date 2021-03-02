package ie.gmit.dip;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server{

	private ServerOne serveOne;
	
	private static String hostname;
	private static int port = 3003;
	private static String TERMINATE = "\\q";

	protected Set<String> otherUserNames = new HashSet<>();
	protected Set<User> users = new HashSet<>();
	//private Set<User> users = new HashSet<>();
	private ServerSocket serverSocket;
	
	//volatile boolean finished = this.otherUserNames.isEmpty();
	volatile boolean finished;

	private static Scanner scanner;
	
	private long start = System.currentTimeMillis();
	private long end = start + 700000; //wait 9 seconds
//	static volatile boolean finished = false;

	//ServerQuit serverQuit;

	public Server(int port) {
		this.port = port;
//		this.serverSocket = serverSocket; 
	}

	protected void removeOtherUserNames(String userName, User theUser) {
		boolean left = otherUserNames.remove(userName);
		if (left) {
			users.remove(theUser);
			System.out.println(userName + " has left.");
		}
	}

	protected void addOtherUserNames(String userName) {
		otherUserNames.add(userName);
	}

	public boolean hasOthers() {
		return !this.otherUserNames.isEmpty();
	}

	public Set<String> otherUsers() {
		return this.otherUserNames;
	}

	protected void serverSays(String statement, User self) {
		for (User aUser : users) {
			if (aUser != self) {
				aUser.send(statement);
			}
		}
	}

	// to try to close the server socket
	// THIS IS NOT CALLED NOW
	public void serverSocketClose() {
		try {
			// ServerSocket listener;
			this.serverSocket.close();
		} catch (Exception e) {
			System.out.println("There was a problem closing the server socket. Try again.");
		}
	}

	public void go() {

		try (ServerSocket listener = new ServerSocket(port)) {
			System.out.println("Server is ready for connecting users.");
			//String statement = "";
	
			while(true) {
//			while (!statement.equalsIgnoreCase(TERMINATE)) {
				Socket client;

				client = listener.accept();

				System.out.println("Welcome the new user.");
				User newUser = new User(client, this);
				users.add(newUser);

				newUser.start();// start the thread for this user
				checkNumberUsers();							
//				serverQuit.serverQuit();
				
//				new ServerThread(listener, client).start();
			//	new ServerThread(client, this).start();
				
				//CAN CUT FROM HERE
				/*if ((System.currentTimeMillis() == end)) {// to allow the server to exit
					
					try {
						BufferedReader brServer = new BufferedReader(new InputStreamReader(System.in));
						String serverMessage = brServer.readLine();
						if (serverMessage.equalsIgnoreCase(TERMINATE)) {
							System.out.println("Server is closed.");
							//finished = true;
							this.serverSocket.close();
							break;
						}
					} catch (Exception e) {
						System.out.println("There was a problem closing the server socket. Bye!");
					}
					}*/

				//CAN END CUT FROM HERE
			}
		} catch (Exception e) {
			System.out.println("There was a problem with this connection. Bye!");
		}
		
		System.out.println("You may shut down by pressing backslash q.");
	
	}

	public static void main(String[] args) {

		System.out.println("Do you want to chat with one client or facilitate multiple clients? Press 1 for one client and 2 for many.");
		scanner = new Scanner(System.in);
		if(scanner.nextLine().toString().equalsIgnoreCase("1")){
			
			ServerOne serveOne = new ServerOne();
			try {
				serveOne.go();
			} catch (Exception e) {
				System.out.println("There was a problem. Please try again");
				e.printStackTrace();
			}
			
		}else {

			Server server = new Server(port);
			server.go();		

		}
		
		
	}
		
	public void emptyUsersCheck() {
		
		System.out.println("The result of there being no users:" + this.users.isEmpty());
	}
	
	private void checkNumberUsers() {
		Timer time = new Timer();
		time.schedule(new TimerTask(){
			@Override
			public void run() {
				emptyUsersCheck();
			}
		
		}, 7000 //this will execute automatically after 7 seconds
				);
	}
	
}
