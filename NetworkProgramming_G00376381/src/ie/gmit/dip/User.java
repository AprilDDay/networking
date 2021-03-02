package ie.gmit.dip;

import java.io.*;
import java.util.*;
import java.net.*;

public class User extends Thread {

	private Server server;
	private Socket socket;
	private PrintWriter print;
	private static String TERMINATE = "\\q";

	public User(Socket socket, Server server) {
		this.server = server;
		this.socket = socket;
	}

	public void run() {

		try {
			InputStream in = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			OutputStream out = socket.getOutputStream();
			print = new PrintWriter(out, true);

			printConnectedUsers();

			String userName = br.readLine();
			server.addOtherUserNames(userName);

			String serverSends = "Another user is here now: " + userName;
			server.serverSays(serverSends, this);

			String statement;

			do {
				statement = br.readLine();
				serverSends = "[" + userName + "] says:" + statement;
				server.serverSays(serverSends, this);// refer to this particular user

			} while (!statement.equalsIgnoreCase(TERMINATE));// gracefully leave chat

			server.serverSays(userName + " left", this); // let others know this user left
			server.removeOtherUserNames(userName, this);
			socket.close();// close connection

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void printConnectedUsers() {
		if (server.hasOthers()) {
			print.println("These users are connected: " + server.otherUsers() + " Chat away.");
		} else {
			print.println("There are no other users. You can either wait or leave by pressing backslash q.");
		}
	}

	public void send(String statement) {
		print.println(statement);
	}
}
