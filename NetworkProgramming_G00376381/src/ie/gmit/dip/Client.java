package ie.gmit.dip;

import java.util.*;
import java.net.*;
import java.io.*;

public class Client {

	private Socket socket;
	private static String hostname;
	private static int port;
	private static String TERMINATE = "\\q";
	private String username;
	private ClientOne clientOne;
	
	private static Scanner scanner;
	
	public Client(Socket socket) {
		this.socket = socket;
	}

	public void connectClientSocket() {
		
			System.out.println("We are connected.");
			
			new Read(socket, this).start();
			new Write(socket, this).start();
	}
		
	protected void setUsername(String username) {
		this.username = username;	
	}
	
	protected String getUsername() {
		return this.username;
	}
	
	public static void main(String[] args) {

			System.out.println("Hi! Let's get you connected!");
			System.out.println("First, a quick question: Do you want to talk to other users or the server? Please enter 1 for server and 2 for other users.");
			scanner = new Scanner(System.in);

			if(scanner.nextLine().toString().equalsIgnoreCase("1")) {
				
				System.out.println("You chose to have a chat with the server.");
				System.out.println("PLEASE NOTE: If the server is set to accept multiple users,");
				System.out.println("you will be put into a chat room. You may quit by pressing backslash q.");
				System.out.println("You can try again later to have an anonymous conversation with the server."); 
				System.out.println("Wait while we connect to the server.");
				
				ClientOne clientOne = new ClientOne();
				try {
					clientOne.go();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Sorry. There was a problem. Try again.");
					e.printStackTrace();
				}
				
			}else {
				System.out.println("You want to talk with other users. Great!");
				System.out.println("PLEASE NOTE: If the server is configured to have one-on-one chat, you will be");
				System.out.println("chatting with the server. You may quit by pressing backslash q and join later.");
				System.out.println("If you are not notified about who else is in the chat, then the server is not currently");
				System.out.println("supporting a multi-chat session. Please log off and try again later.");
				System.out.println("Please wait a moment while we connect you to chat with other users.");
				LookForPorts looking = new LookForPorts();
				
				Socket socket = looking.getSocketInfo();
				
				Client client = new Client(socket);
			client.connectClientSocket();
			
			}			
				
			}
			
}
