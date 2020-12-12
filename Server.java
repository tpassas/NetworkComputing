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

	  /* Create a receiving datagram data buffer */
	  byte[] buffer = new byte[255];

	  /* Create receiving datagram object of maximum size maxLength */
	  DatagramPacket indatagram = new DatagramPacket( buffer, maxLength );

	  /* Create a UDP socket on a specific port */
	  DatagramSocket socket = new DatagramSocket( port );

	  /* Display message that the UDP echo server is running */
      System.out.println( "Starting a UDP Echo Server on port " + port );

      /* By Panagiotis Fotakidis
      		Creates Server socket and a normal socket for other clients to connect.*/

      ServerSocket serverSocket = new ServerSocket(4567);
      Socket socket1 = serverSocket.accept();

      /* By Panagiotis Fotakidis
      		Checks if the socket is connected to client. */

      if (socket1.isConnected())
	  	System.out.println(" A Client got Connected!");


	  while( true ) {

		/* Set the max length of datagram to 255 */
		indatagram.setLength( maxLength );
	
		/* Receive the datagram from the client  */
		socket.receive( indatagram );
		
		/* Convert the message from the byte array to a string array for displaying */
		String msgFromClient = new String( indatagram.getData(), 0, indatagram.getLength() );
		
		/* Display the message on the screen */
		System.out.println( "\n Message received from " + indatagram.getAddress() + " from port "
							+ indatagram.getPort() + ".\nContent: " + msgFromClient );

		/*By Panagiotis Fotakidis
			Checks if the user wants to exit by typing exit, and shows the appropriate message, while the socket is being closed by the client. */

		if(msgFromClient.equals("exit")) {
			System.out.println("Client got Disconnected!");
			continue;
		}
		
		/* Capitalize the received message */
		capitalizedSentence = msgFromClient.toUpperCase() + '\n';
		
		/* Create a new datagram data buffer (byte array) for echoing capitalized message */
		byte[] msgToClient = capitalizedSentence.getBytes(); 
		
		/* Create an outgoing datagram by extracting the client's address and port from incoming datagram */
		DatagramPacket outdatagram = new DatagramPacket( msgToClient, msgToClient.length,
									 indatagram.getAddress(), indatagram.getPort() );
		
		/* Send the reply back to the client */
		socket.send( outdatagram );
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