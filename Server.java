/**
 * Filename: Client
 * Description: A Client Class that determines the functionality of the client and the menu that the user see in order to navigate in the program
 * 
 *
 * Author: Fotakidis Panagiotis
 * 
 * @param receivedMsg A string message received by the Client.
 * @param registryMsg A string message received by the Client in order to utilise the Server Menu.
 * @param responceMsg A string message that will be sent to the Client for informative purposes.
 * @param message A predefined string message utilised by methods and arrays in order to create and hold a message until it is sent to the server.
 * @param receivedInt A Integer message received by the Client.
 * @param receivedId An Integer message received by the Client that is utilised as an Id to search and find proper information in the arrays based on the needs of the Client. 
 * @param showAllAuction A string message that hold a specific message for a specific menu phase that is sent to the user for informative reasons.
 * @param id An integer that holds the count of how many registration phases there have been and basically how many auctions exist.
 * @param port The port on which the server is operating.
 * @param connection A boolean that determines if a Client is connected to the server or not
 * @param auctions [][] A multidimensional array that holds all the String information about an auction.
 * @param bidsNstartingPrice [][] A multidimensional array that holds all the String information about an auction.
 */

import java.util.*;
import java.net.*;
import java.io.*;

public class Server implements Runnable {

	/**
	* Author: Thrasivoulos Passas
	* Description: An attempt to create a thread
	*/
	public void run(){
			System.out.println("Thread is running");
	}

	static Thread myThread;

    public static void main( String argv[] ) throws Exception {
		myThread = new Thread(new Server());
		Server s1 = new Server();
		s1.run();
      int id = 0, port = 4567;
	  String receivedMsg, registryMsg, responceMsg = "",message = "", showAllAuctions="";
	  boolean connection;
	  String [][]auctions = new String[20][2];
	  int [][] bidsNstartingPrice = new int[20][2];
	  int receivedInt, receivedId;

      ServerSocket ss = new ServerSocket(4567);
      System.out.println(" Starting an Auction Server on port " + port +"\n");

		/**
		* Author: Fotakidis Panagiotis
		* Description: A while loop that prevents the Server from shutting down and terminating.
	  	*/
      while(true){

         System.out.println(" Waiting for a connection... ");

         Socket socket1 = ss.accept();
         InetSocketAddress socket1Use = (InetSocketAddress)socket1.getRemoteSocketAddress();

         connection = socket1.isConnected(); 

         if (connection)
	  	  System.out.println("Client from : " + socket1Use.getAddress() + " ------------> Is now Connected! \n");
	  	 else
	  	  System.out.println("Client from : " + socket1Use.getAddress() + " ------------>  Unable to Connect!! \n");

	     InputStream inStream = socket1.getInputStream();
	     DataInputStream dataInStream = new DataInputStream(inStream);

	     OutputStream outStream = socket1.getOutputStream();
	     DataOutputStream dataOutStream = new DataOutputStream(outStream);

			/**
			* Author: Fotakidis Panagiotis
			* Description: A while loop that starts as long as there is a connection and creates the Auction System.
	  		*/
		    while( connection ) {	
		    	 
			  responceMsg = "";

			  receivedMsg = dataInStream.readUTF();

			  	/**
				* Author: Fotakidis Panagiotis
				* Description: If the Server receives a "Register" by the Client, it enters the Registration Phase and processes.
	  			*/
			  if( receivedMsg.equals("Register")){
				
				 System.out.println(" \n New Auction Registry! \n");

			  	 for (int i = 0; i <= 3; i++ ){

			  	 	  if ( i == 0 || i == 1 ){
			  	 	  	 
			  		     responceMsg = registerInStringArray(responceMsg, dataInStream, auctions, id, i);
			  	         
			  	 	    } 
			  	 	  else if (i == 2) {
			  	 	     
			  		     responceMsg= registerInIntegerArray(responceMsg, dataInStream, bidsNstartingPrice, id, i);

			  	 	    }
			  	 	    else {

			  	 	      bidsNstartingPrice[id][i-2] = 0;
			  	    	  responceMsg += " - " + bidsNstartingPrice[id][i-2];
			  	 	    }
			  	    }

			  	 responceMsg = "Your auction has been Recorded : \n " + responceMsg + "\n Your AuctionID is : " + id;
			  	 sendMsg(responceMsg, dataOutStream);

			     System.out.println("Recorded Auctions : " + printAuctions(message, bidsNstartingPrice, auctions,id));
				   

			     id ++;
			    }
			    /**
				* Author: Fotakidis Panagiotis
				* Description: If the Server receives a "Exit" by the Client, it disconnects the Client and awaits for new connections if no other clients are in the System.
	  			*/
				else if( receivedMsg.equals("Exit")){

					System.out.println("Client from : " + socket1Use.getAddress() + " ------------> Is now Disconnected!");
				  connection = false;
				  break;
  
					 
				  }
				/**
				* Author: Argyropoulos Stavros, Fotakidis Panagiotis
				* Description: If the Server receives a "Search" by the Client, it enters the Searching Phase 
				* and starts searching for a specific auction based on a given ID by the Client.
	  			*/
				else if (receivedMsg.equals("Search")) {



					System.out.println("Client enters a Search! \n Client is entering auctionID \n");

					receivedId = dataInStream.readInt();
					System.out.println("Client entered " + receivedId + "\n");
					
					responceMsg = "Name: " + auctions[receivedId][0]+ "\n Description: " + auctions[receivedId][1] +"\n Starting Price " + bidsNstartingPrice[receivedId][0]  + "\n Highest Bid: "  + bidsNstartingPrice[receivedId][1];
					
					dataOutStream.writeUTF(responceMsg);
					dataOutStream.flush();

					System.out.println("Auction found & Returned");
				}
				/**
				* Author: Argyropoulos Stavros, Fotakidis Panagiotis
				* Description: If the Server receives a "Show" by the Client, it enters the Showing Phase
				* shows the Client all the available auction through a method that traverses all the arrays and returns the proper message.
	  			*/
				else if (receivedMsg.equals("Show")) {

					showAllAuctions = "Recorded Auctions : \n ID - NAME - DESCRIPTION - STARTING PRICE - HIGHEST BID \n" + printAuctions(message, bidsNstartingPrice, auctions, id-1);
					dataOutStream.writeUTF(showAllAuctions);
					dataOutStream.flush();

				}
				/**
				* Author: Thrasivoulos Passas,Fotakidis Panagiotis
				* Description: If the Server receives a "Bid" by the Client, it enters the Bidding Phase and based on an auction id that is received by the Client
				* it changed the Highest bid of an item in its proper auction. In case a lower than necessary bid is entered the server prompts a proper message
				* to the Client.
	  			*/
				else if (receivedMsg.equals("Bid")){


					System.out.println("Client is entering auctionID \n");

					receivedId = dataInStream.readInt();
					System.out.println("Client entered " + receivedId);

					receivedInt = dataInStream.readInt();


			        if(receivedInt <= bidsNstartingPrice[receivedId][1]){
						responceMsg = "Bid is too low. Please Bid Higher!";
						sendMsg(responceMsg, dataOutStream);
					}
					else{
						int tmp = bidsNstartingPrice[receivedId][1];

					    bidsNstartingPrice[receivedId][1] = receivedInt;
						responceMsg = "Your bid has been recorded. Highest bid changed from " + tmp + " to " + bidsNstartingPrice[receivedId][1];
						dataOutStream.writeUTF(responceMsg);
					    dataOutStream.flush();

					    System.out.println("Bid placed successfully");
					}	
				} 		
			}
		}
	}


	/**
	* Author: Panagiotis Fotakidis
	* Writes a string message inputed by the user into the proper String array for storing. 
	* @param dataInStream the incoming stream from which the data flow from the Client.
	* @param responceMsg A string message that stores the proper information to be outputed to the Client.
	* @param array [][] A string array that holds all the proper information, inputed by the Client.
	* @param id An integer id that holds the id of the auction
	* @param i An integer for current loop of the registration phase.
	* @throws java.io.IOException 
	* @return A proper message in order to be build and ultimatelly be sent to the Client for informative reasons.
	*/
    public static String registerInStringArray(String responceMsg, DataInputStream dataInStream, String [][] array, int id, int i)throws java.io.IOException{

    		String registryMsg = dataInStream.readUTF();

    		array[id][i] = registryMsg;
			System.out.println("Recorded : ' " + registryMsg + " ' to --> " + array[id][i]);

			responceMsg += " - " + array[id][i];
			return responceMsg;
    }

	/**
	* Author: Panagiotis Fotakidis
	* Writes an Integer message inputed by the user into the proper Integer array for storing. 
	* @param dataInStream the incoming stream from which the data flow from the Client.
	* @param responceMsg A string message that stores the proper information to be outputed to the Client through the dataInStream.
	* @param array [][] An Integer array that holds all the proper information, inputed by the Client.
	* @param id An integer id that holds the id of the auction
	* @param i An integer for current loop of the registration phase.
	* @throws java.io.IOException 
	* @return A proper message in order to be build and ultimatelly be sent to the Client for informative reasons.
	*/
    public static String registerInIntegerArray(String responceMsg, DataInputStream dataInStream, int [][] array, int id, int i)throws java.io.IOException{

      int registryInt = dataInStream.readInt();

      array[id][i-2] = registryInt;
	  System.out.println("Recorded : ' " + registryInt + " ' to --> " + array[id][i-2]);

	  responceMsg += " - " + array[id][i-2];
	  return responceMsg;

    }

	/**
	* Author: Panagiotis Fotakidis
	* Sends a message to the Client
	* @param dataOutStream the outgoing stream from which the data flow to the Client.
	* @param message A string message that stores the proper information to be outputed to the Client through the dataOutStream.
	* @throws java.io.IOException 
	* @return Nothing
	*/
    public static void sendMsg (String message, DataOutputStream dataOutStream)throws java.io.IOException {
      dataOutStream.writeUTF(message);
	  dataOutStream.flush();
	}

	/**
	* Author: Panagiotis Fotakidis
	* Sends a message to the Client
	* @param message A message that is being build though the for loop by traversing the arrays and ultimatelly to be sent to the Client.
	* @param intArray[][] An integer array that holds all the integer information for an item's auction.
	* @param stringArray[][] A string array that holds all the string information for an item's auction.\
	* @param id An integer that holds the amount of all the active actions in the server, and allows the loop to know how many times to loop.
	* @throws java.io.IOException  
	* @return Nothing
	*/
	public static String printAuctions(String message, int[][] intArray, String[][] stringArray,int id) throws java.io.IOException{

		message += "\n";

		for (int i = 0; i <= id; i++ ){

			message += "id:" + i;

			for (int j = 0; j<= 1; j++){

			  message += " - " + stringArray[i][j];
			}

			for ( int x = 0; x<= 1; x++){

			  message += " - " + intArray[i][x];
			}
			message+= "\n";
		}

		return message;

	}
}
