/*
 * Filename: UDPEchoClient.java
 * Description: An echo client using connectionless delivery system (UDP).
 *              Sends character messages to a server which are echoed capitalized.
 *              No error handling and exceptions are implemented.
 * Operation: java UDPEchoCLient [hostname] [port]
 *
 * Author: Thanos Hatziapostolou
 * Module: Network Computing
 */

/* for the use of arraylist and scanner */
import java.util.*;
import java.net.*;
import java.io.*;

public class Client {

		private static int port, toSendInt ;
		private static String hostname;
		private static String toReceiveMsg, toSendMsg;
		private static int toReceiveAuctionID, toSendAuctionID;

		private static ArrayList<Integer> AuctionNumbers = new ArrayList<Integer>();

    public static void main(String [] argv) throws Exception {

		hostname = new String( argv[0] );	
		port = Integer.parseInt( argv[1] );

		Socket socket1 = new Socket(hostname, port);

		OutputStream outStream = socket1.getOutputStream();
		DataOutputStream dataOutStream = new DataOutputStream(outStream);

		InputStream inStream = socket1.getInputStream();
		DataInputStream dataInStream = new DataInputStream(inStream);

		Scanner scanner = new Scanner(System.in);

		while ( true ) {


		  System.out.println( "\n -------------------------------------- \n Type > Register --> To register a new item for Auction \n Type > Search --> To search for an Auction using the Auction ID. \n Type > Bid --> To bid on an Auction using the Auction ID.");
		  System.out.println(" Type > Exit --> To quit! \n -------------------------------------- ");

		  
		  toSendMsg = scanner.nextLine();
		  sendMsgStr(dataOutStream,toSendMsg);

		  if(toSendMsg.equals("Exit")) {

		    System.out.println(" You have Disconnected Successfully!");
			socket1.close();
			break;

		  } 
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
		  else if(toSendMsg.equals("Search")){

			 System.out.println("Please enter the auction ID");

			 toSendInt = scanner.nextInt();
			 sendMsgInt(dataOutStream,toSendInt);
		     System.out.println("Sent : " + toSendInt);

		    }  	

		   toReceiveMsg = dataInStream.readUTF();
		   System.out.println(toReceiveMsg);
	    }
	} 

	public static String sendMsgStr(DataOutputStream dataOutStream, String message) throws java.io.IOException{

 
		      dataOutStream.writeUTF(message);
		      dataOutStream.flush();
		      return message;
		    
	}

	public static int sendMsgInt( DataOutputStream dataOutStream, int message) throws java.io.IOException{

			  dataOutStream.writeInt(message);
		      dataOutStream.flush();
		      return message;	
	}  	


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


	/*Argyropoulos! This is a method in order to generate an AuctionID when a client is listing an item *
/It is not possible to generate the same number!!!! but there is a limited range of numbers from 1000 to 10000*/
	
	public static int generateAuctionID() {
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		Random random = new Random();
			for (int i=1000; i<=10000; i++)
    		numbers.add(i);
			Collections.shuffle(numbers);
			// System.out.println(numbers.get(0));
			AuctionNumbers.add(numbers.get(0));
			return numbers.get(0);

	}

	/*Argyropoulos! a method where the user will be able to list an item for an auction */
	public  static void listItem() {
		Scanner scanner = new Scanner(System.in);
		/*Argyropoulos! Have to make exceptions if a user enters int instead of string and so on */
		System.out.println("Enter the name of the Item ");
		String itemName = scanner.nextLine();

		System.out.println("Enter a description for your item");
		String itemDescription = scanner.nextLine();

		System.out.println("Enter your item's Starting price");
		int itemStartPrice = scanner.nextInt();

		//Argyropoulos! (for later) System.out.println("Enter the type of the auction");

		System.out.println("\n Thank you! These are the cresendials you entered.\n Item name : " + itemName + "\n Item Description : " + itemDescription + " \n Starting Price : " +itemStartPrice);

		System.out.println("\n The auction ID for this auction is : " + generateAuctionID());
	}


	/*Argyropoulos! When a client wants to enter in an Auction */
	public static void enterAuction(){
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the auction ID");
		int auctionID = sc.nextInt();
		/*Argyropoulos! checks to our AuctionNumbers Arraylist if the auctionID of the user it exists*/
		if (AuctionNumbers.contains(auctionID)) {
			/*Argyropoulos! probably make the auction room */
			System.out.println("YEA");
		}
		
	}
}

/*
 * Example:
 *   java UDPEchoClient 192.168.12.12 4567
 * Output:
 *	 Type text to send to server:
 *   Hello server.
 *   Received: HELLO SERVER.
 */
		
/*** EXTRA INFORMATION ***/		
/*
DatagramPacket: it implements UDP Socket communication.
DatagramSocket: it is used for UDP communication.
BufferedReader: it supports input buffering. It provides the readLine() method for reading an entire line at a
                time from a stream.
InputStreamReader: reads a stream. It is used to convert between byte streams and character streams. It provides
                   a bridge between byte-oriented and character-oriented input streams.
*/

/*
If the server is not 'alive' the program blocks. To resolve this, you need to catch the exceptions and handle them.
*/