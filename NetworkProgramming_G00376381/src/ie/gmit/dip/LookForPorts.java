package ie.gmit.dip;

import java.util.*;
import java.net.*;
import java.io.*;

public class LookForPorts {

	public Socket getSocketInfo() {

		Socket client = null;
		int port = 0;
		String host = "127.0.0.1";//This can be changed. 

		for (int i = 3000; i < 3020; i++) {
			try {
				client = new Socket(host, i);
				System.out.println("There is a server on port " + i + " of " + host);
				return client;

			} catch (UnknownHostException e) {
				System.err.println(e);
				break;
			} catch (IOException io) {
				System.err.println(io);
			}
		}
		return client;
	}
}
