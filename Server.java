import java.util.*;
import java.net.*;
import java.io.*;

public class Server {

    public static void main( String argv[] ) throws Exception {

      int bid,startingPrice, id = 0, port = 4567;
	  String receivedMsg, responceMsg, name, description;
	  boolean connection;
	  


	 ArrayList<String> auctionStrings = new ArrayList();

	 // ArrayList<Integer> id  = new ArrayList<Integer>();
	  ArrayList<String> namesList = new ArrayList<String>();
	  ArrayList<String> descriptionsList = new ArrayList<String>();
      ArrayList<Integer> bidsList = new ArrayList<Integer>();
      ArrayList<Integer> startingPriceList = new ArrayList<Integer>();

      ServerSocket ss = new ServerSocket(4567);
      System.out.println(" Starting a UDP Echo Server on port " + port +"\n");

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


	    while( connection ) {

	      responceMsg = " ";

		  receivedMsg = dataInStream.readUTF();

		  if( receivedMsg.equals("Exit") ){
		  	System.out.println("Client from : " + socket1Use.getAddress() + " ------------> Is now Disconnected!");
			connection = false;
			break; 
		  }
		  else if( receivedMsg.equals("Register") ){

		  	System.out.println(" \n New Auction Registry! \n");
		  	name = dataInStream.readUTF();
		  	namesList.add(name);
		  	System.out.println("Recorded : ' " + name + " ' to --> " + namesList);

		  	description = dataInStream.readUTF();
		  	descriptionsList.add(description);
		  	System.out.println("Recorded : ' " + description + " ' to --> " + descriptionsList);

		  	bid = dataInStream.readInt();
		  	bidsList.add(bid);
		  	System.out.println("Recorded : ' " + bid + " ' to --> " + bidsList);

		  	startingPrice = dataInStream.readInt();
		  	startingPriceList.add(startingPrice);
		  	System.out.println("Recorded : ' " + (int)startingPrice + " ' to --> " + startingPriceList);

		  	System.out.println("Recorded All!");

		  	responceMsg = "Your auction has been Recorder : \n " + namesList + descriptionsList + bidsList + startingPriceList;
		  	dataOutStream.writeUTF(responceMsg);
		  	dataOutStream.flush();

			auctionStrings.add(responceMsg);
			System.out.println(auctionStrings);

		  }
		}
	  }
    }
}
