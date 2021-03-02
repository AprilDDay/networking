package ie.gmit.dip;

import java.net.*;
import java.io.*;

public class Read extends Thread {

	private Client client;
	private Socket socket;

	private BufferedReader read;
	private static String TERMINATE = "\\q";// to terminate, backslash q not possible because a literal, use two
											// backslashes

	public Read(Socket socket, Client client) {// start of ReadWrite constructor

		this.socket = socket;
		this.client = client;

		try {// beginning of try/catch block

			InputStream input = socket.getInputStream();
			read = new BufferedReader(new InputStreamReader(input));

		} catch (IOException ie) {// end of try block
			System.out.println("Oh no...we are having some problems...");
			ie.printStackTrace();
		} // end of catch block

	}// end of ReadWrite constructor

	public void run() {

		while (true) {
			try {
				String response = read.readLine();
				System.out.println("\n" + response);

				if (client.getUsername() != null) {
					System.out.print("[" + client.getUsername() + "]: ");
				}

				if (response.equalsIgnoreCase(TERMINATE)) {
					System.out.println("[" + client.getUsername() + "]: I left."); // THIS WAS NEVER CALLED
					socket.close();
					break;
				}
			} catch (IOException ie) {
				System.out.println(
						"You logged out, we have a problem, or the server shutdown. You can try to reconnect. Bye for now!");
				break;
			}
		}
	}
}
