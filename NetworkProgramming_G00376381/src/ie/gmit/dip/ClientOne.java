package ie.gmit.dip;

import java.io.*;
import java.net.*;

public class ClientOne {

	LookForPorts looking;
	
	//variables to allow server to close connection with '\q'
	// '\' is an escape literal https://stackoverflow.com/questions/9445196/invalid-escape-sequence-valid-ones-are-b-t-n-f-r
	// four backslashes to insert one: https://www.regular-expressions.info/java.html
	private static final String TERMINATE = "\\q";//this needs to be a back slash which is a literal
	static volatile boolean finished = false;

	 public void go() throws Exception{ 
	 //IP hard-coded into the program
//    Socket sock = new Socket("127.0.0.1", 3001);
    
			LookForPorts looking = new LookForPorts();
			
			Socket sock = looking.getSocketInfo();
		 
    BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
                             
    OutputStream ostream = sock.getOutputStream(); 
    PrintWriter pwrite = new PrintWriter(ostream, true);

    InputStream istream = sock.getInputStream();
    BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));

    System.out.println("Ready to chat. Type and press Enter key");

    String receiveMessage, sendMessage;               
    while(true)
    {
       sendMessage = keyRead.readLine();  // keyboard reading
       pwrite.println(sendMessage);       // sending to server
       pwrite.flush();                    // flush the data
       if((receiveMessage = receiveRead.readLine()) != null) //receive from server
       {
           System.out.println(receiveMessage); 
       }         
       
		if (sendMessage.equalsIgnoreCase(ClientOne.TERMINATE)) {
			sendMessage = "I left. Ciao!";//message doesn't print
			pwrite.println(sendMessage);//message doesn't print
			finished = true;
			pwrite.flush();
			sock.close();
			break;
		
		}

     }               
   }                  
		}