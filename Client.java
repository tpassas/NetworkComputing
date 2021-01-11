/**
 * Filename: Client
 * Description: A Client Class that determines the functionality of the client and the menu that the user see in order to navigate in the program
 * 
 *
 * Author: Fotakidis Panagiotis
 * 
 * @param port The port that the Client will connect through.
 * @param toSendInt An integer message that is sent to the server.
 * @param hostname The hostname that the Client will connect with.
 * @param toReceiveMsg A string message that is received from the server.
 * @param toSendMsg A string message that is sent to the server.
 */


import java.util.*;
import java.net.*;
import java.io.*;

public class Client {

		private static int port, toSendInt ;
		private static String hostname;
		private static String toReceiveMsg, toSendMsg;


    public static void main(String [] argv) throws Exception {

		hostname = new String( argv[0] );	
		port = Integer.parseInt( argv[1] );

		Socket socket1 = new Socket(hostname, port);

		OutputStream outStream = socket1.getOutputStream();
		DataOutputStream dataOutStream = new DataOutputStream(outStream);

		InputStream inStream = socket1.getInputStream();
		DataInputStream dataInStream = new DataInputStream(inStream);

		Scanner scanner = new Scanner(System.in);


		/**
		* Author: Fotakidis Panagiotis
		* Description: A while loop that prevents the Client from exiting the server connection and terminating.
	  	*/
		while ( true ) {

			toReceiveMsg = null;

		  System.out.println( "\n -------------------------------------- \n Type > Register --> To register a new item for Auction \n Type > Show --> To see all the live Auctions \n Type > Search --> To search for an Auction using the Auction ID \n Type > Bid --> To Bid");
		  System.out.println(" Type > Exit --> To quit! \n -------------------------------------- ");

		  
		  toSendMsg = scanner.nextLine();
		  sendMsgStr(dataOutStream,toSendMsg);


		  /**
		  * Author: Fotakidis Panagiotis
		  * Description: If the user enters Exit the programm disconnects from the server.
		  */
		  if(toSendMsg.equals("Exit")) {

		    System.out.println(" You have Disconnected Successfully!");
			socket1.close();
			break;

		  }
		  /**
		  * Author: Fotakidis Panagiotis
		  * Description: If the user enters Register the programm starts the registration phase where the user registers a new auction by inputting the proper information.
		  */ 
		  else if(toSendMsg.equals("Register")) {

		     for (int i = 0; i <= 2; i++){

		  	     System.out.println(registerMessages(i));
		  	  
		  	     if(i == 2){

		  	 	     toSendInt = scanner.nextInt();
		  	  	      sendMsgInt(dataOutStream, toSendInt);
		  	        }

			     toSendMsg = scanner.nextLine();
		         sendMsgStr(dataOutStream, toSendMsg);
		        }
            }
          /**
		  * Author: Fotakidis Panagiotis
		  * Description: If the user enters Show the programm starts the Showing phase where in order to show to the user all the active auctions and their Id's.
		  */ 
            else if(toSendMsg.equals("Show")){

            	sendMsgStr(dataOutStream,toSendMsg);

            }
          /**
		  * Author: Argyropoulos Stavros
		  * Description: If the user enters Search the programm starts the Searching phase where the user can search for a specific auction using a specific auction Id.
		  */ 
		  else if(toSendMsg.equals("Search")){
		  	//argy
			System.out.println("Please enter the auction ID");
			toSendInt = scanner.nextInt();
			sendMsgInt(dataOutStream, toSendInt);

			System.out.println("Sent : " + toSendInt);

			toSendMsg = scanner.nextLine();
		    sendMsgStr(dataOutStream, toSendMsg);

		    }
		  /**
		  * Author: Thrasivoulos Passas, Fotakidis Panagiotis
		  * Description: If the user enters Bid the programm starts the Bidding phase where the user selects an auction through an auction ID and then he can bid
		  * in order to have the highest bid and win an auction. If the user bids a lower than neccesarry ammount a proper message is prompted.
		  */ 
		  else if (toSendMsg.equals("Bid")){

			  //passas
			  System.out.println("Please enter the auction ID");
			  toSendInt = scanner.nextInt();
			  sendMsgInt(dataOutStream, toSendInt);

			  System.out.println("How much you want to bid");

			  toSendInt = scanner.nextInt();
			  sendMsgInt(dataOutStream, toSendInt);    
		    } 

		    System.out.println(promptReceivedMsg(toReceiveMsg,dataInStream));
	    }
	} 

	/**
	* Author: Panagiotis Fotakidis
	* Writes a string message from the user input to the out going stream towards the Server. 
	* @param dataOutStream the out going stream from which the data flow to the server
	* @param message the user input that is going to be sent
	* @throws java.io.IOException 
	* @return Nothing
	*/
	public static void sendMsgStr(DataOutputStream dataOutStream, String message) throws java.io.IOException{

 
		      dataOutStream.writeUTF(message);
		      dataOutStream.flush();
		      		    
	}
	/**
	* Author: Panagiotis Fotakidis
	* Writes an Integer number from the user input to the out going stream towards the Server. 
	* @param dataOutStream the out going stream from which the data flow to the server
	* @param message the user input that is going to be sent
	* @throws java.io.IOException 
	* @return Nothing
	*/
	public static void sendMsgInt( DataOutputStream dataOutStream, int message) throws java.io.IOException{

			  dataOutStream.writeInt(message);
		      dataOutStream.flush();	      	
	}  	
	/**
	* Author: Panagiotis Fotakidis
	* Reads the icoming message through the incoming stream.
	* @param dataInStream the incoming stream from which the data flow from the Server
	* @param message the variable in which the incoming message will be saved.
	* @throws java.io.IOException 
	* @return the message that came from the input stream.
	*/
	public static String promptReceivedMsg( String message, DataInputStream dataInStream) throws java.io.IOException{
		message = dataInStream.readUTF();
		return message;
	}

	/**
	* Author: Panagiotis Fotakidis
	* Creates the proper message for the user during the Registration Phase, in order to navigate him/her.
	* @param i the loop in which the registration phase is currently in to determine which message must be sent.
	* @return the message that needs to be prompted to the user.
	*/
	public static String registerMessages(int i){
		String message;

		if (i == 0)
			message = " Write the name of the Item !";
		else if (i == 1)
			message = " Write the description of the Item !";
		else
			message = " Write the starting price of the Item !";
		
		return message;
	}
}