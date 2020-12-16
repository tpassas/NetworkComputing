import java.util.*;
import java.net.*;
import java.io.*;

public class Server {

    public static void main( String argv[] ) throws Exception {

      int bid,startingPrice, id = 0, port = 4567;
	  String receivedMsg, responceMsg, registryMsg, name,message = "";
	  boolean connection;
	  String auctions [][] = new String[20][20];
	  int [][] bidsNstartingPrice;

      ServerSocket ss = new ServerSocket(4567);
      System.out.println(" Starting an Auction Server on port " + port +"\n");

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

		  receivedMsg = dataInStream.readUTF();

		  if( receivedMsg.equals("Exit") ){
		  	System.out.println("Client from : " + socket1Use.getAddress() + " ------------> Is now Disconnected!");
			connection = false;
			break; 
		  }
		  else if( receivedMsg.equals("Register") ){

		  	System.out.println(" \n New Auction Registry! \n");


		  	  name = dataInStream.readUTF();
		  	  auctions[id][0] = name;
		  	  System.out.println("Recorded : ' " + name + " ' to --> " + auctions[id][0]);
		  	  responceMsg = name ;

		  	  for (int j = 1; j <= 2; j++){

		  	  	registryMsg = dataInStream.readUTF();
		  		auctions[id][j] = registryMsg;
		  		System.out.println("Recorded : ' " + registryMsg + " ' to --> " + auctions[id][j]);
		  		responceMsg = responceMsg + " - " + auctions[id][j];
		  		registryMsg = null;

		  	  }

		  	responceMsg = "Your auction has been Recorded : \n " + responceMsg + "\n Your AuctionID is : " + id;
		  	dataOutStream.writeUTF(responceMsg);
		  	dataOutStream.flush();


		  	for (int i = 0; i <= id; i++ ){
		  		for (int j = 0; j<= 2; j++){
		  			message = message + " - "+ auctions[i][j] ;
		  		}
		  		message += "\n"; 
		  	}
		  	System.out.println("Recorded Auctions : \n" + message);
		  	message = "";

		    id ++;
		  }
		}
	  }
    }
}
