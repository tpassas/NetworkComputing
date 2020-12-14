/*
 * Filename: UDPEchoServer.java
 * Description: An echo server using connectionless delivery system (UDP).
 *              Receives character messages at a specified (hardcoded) port.
 *              The message is send back to the client capitalized.
 *              No error handling and exceptions are implemented.
 * Operation: java UDPEchoServer
 *
 * Author: Thanos Hatziapostolou
 * Module: Network Computing
 */

import java.net.*;
import java.io.*;

public class Server {

    public static void main( String argv[] ) throws Exception {

      String capitalizedSentence = null; 
      int port = 4567;
	  int maxLength = 255;
	  String receivedMsg, responceMsg;
	  boolean connection ;

	 // byte[] buffer = new byte[255];

	 // DatagramPacket indatagram = new DatagramPacket( buffer, maxLength );
	 // DatagramSocket socket = new DatagramSocket( port );
	  
      ServerSocket ss = new ServerSocket(4567);
      System.out.println(" Starting a UDP Echo Server on port " + port +"\n");

      while(true){
        System.out.println(" Waiting for a connection... ");

        Socket socket1 = ss.accept();
        InetSocketAddress socket1Use = (InetSocketAddress)socket1.getRemoteSocketAddress();

        connection = socket1.isConnected(); //HERE

        if (connection)
	  	 System.out.println("Client from : " + socket1Use.getAddress() + " ------------> Is now Connected! \n");
	  	else
	  	 System.out.println("Client from : " + socket1Use.getAddress() + " ------------>  Unable to Connect!! \n");

	    InputStream inStream = socket1.getInputStream();
	    DataInputStream dataInStream = new DataInputStream(inStream);

	    OutputStream outStream = socket1.getOutputStream();
	    DataOutputStream dataOutStream = new DataOutputStream(outStream);


	    while( connection ) {

		  receivedMsg = dataInStream.readUTF();

		  if(receivedMsg.equals("exit")) {

			System.out.println("Client from : " + socket1Use.getAddress() + " ------------> Is now Disconnected!");
			//ss.close();//HERE
			// socket1.close(); //HERE
			connection = false;
			break;
		  }

		  System.out.println("The message received is " + receivedMsg + "\n");

		  responceMsg = " Your message : ' " + receivedMsg + ". Has been recorded to the Server! \n";

		  dataOutStream.writeUTF(responceMsg);
		  dataOutStream.flush();
		}
	  }
    }
}



/*
 * Example:
 *   java UDPEchoServer
 * Output:
 *	 Starting a UDP Echo Server on port 4567:
 *   Message Received from: hatziapostolou/197.87.76.3 on port 1354
 *   Content: Hello server
 */