package ie.gmit.dip;

import java.io.*;
import java.net.Socket;

public class Write extends Thread {
	private Client client;
	private Socket socket;
	private PrintWriter write;
	private static String TERMINATE = "\\q";// to terminate, backslash q not possible because a literal, use two
											// backslashes

	public Write(Socket socket, Client client) {
		this.socket = socket;
		this.client = client;

		try {
			OutputStream output = socket.getOutputStream();
			write = new PrintWriter(output, true);

		} catch (IOException ie) {
			System.out.println("Oh no...we are having some problems...");
			ie.printStackTrace();
		}
	}

	public void run() {
		Console console = System.console();

		String userName = console.readLine("\n What is your name?: ");
		client.setUsername(userName);
		write.println(userName); // allows user to see the name

		String text;// string that will be used for the messages

		do {
			text = console.readLine("[" + userName + "]: ");
			write.println(text);

		} while (!text.equalsIgnoreCase(TERMINATE));

		if (text.equalsIgnoreCase(TERMINATE)) {
			write.println("[" + userName + "]: I left."); // this never called
			try {
				socket.close();
			} catch (IOException ie) {
				ie.printStackTrace();
			}
		}
	}
}
