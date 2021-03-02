package ie.gmit.dip;

import java.io.*;
import java.net.*;

public class ServerOne {

	//variables to allow server to close connection with '\q'
	// '\' is an escape literal https://stackoverflow.com/questions/9445196/invalid-escape-sequence-valid-ones-are-b-t-n-f-r
	// four backslashes to insert one: https://www.regular-expressions.info/java.html
	private static final String TERMINATE = "\\q";//this needs to be a back slash which is a literal
	static volatile boolean finished = false;

	public void go() throws Exception{
		ServerSocket sersock = new ServerSocket(3001);
		
		System.out.println("Server is ready for chatting.");
		Socket sock = sersock.accept();

		// reading from keyboard (keyRead object)
		BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
		
		// sending to client (pwrite object)
		OutputStream ostream = sock.getOutputStream();
		PrintWriter pwrite = new PrintWriter(ostream, true);

		// receiving from server ( receiveRead object)
		InputStream istream = sock.getInputStream();
		BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));

		String receiveMessage, sendMessage;
		//sendMessage = "";
		while(true) {			
		if ((receiveMessage = receiveRead.readLine()) != null) {
			System.out.println(receiveMessage);
		}
		sendMessage = keyRead.readLine();
		pwrite.println(sendMessage);
		pwrite.flush();

		if (sendMessage.equalsIgnoreCase(ServerOne.TERMINATE)) {
			pwrite.println("Server is closed now.");
			finished = true;
			pwrite.flush();
			//sock.close();
			sersock.close(); //I don't think you want to shut the whole server down?
			break;
		}
		
		if (receiveMessage.equalsIgnoreCase("\\q")) {
			pwrite.println("You are logged out now.");
			finished = true;
			pwrite.flush();
			sock.close();
			break;
		}

	} 

		
	}
	
}
