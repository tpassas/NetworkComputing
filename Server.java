import java.util.*;
import java.net.*;
import java.io.*;

public class Server {

    public static void main( String argv[] ) throws Exception {

      int bid,startingPrice, registryInt, id = 0, port = 4567;
	  String receivedMsg, registryMsg, name, responceMsg = "",message = "";
	  boolean connection;
	  String [][]auctions = new String[20][2];
	  int [][] bidsNstartingPrice = new int[20][2];

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
		    	
		    	 message = "";
			  	 responceMsg = "";

			  receivedMsg = dataInStream.readUTF();


			  if( receivedMsg.equals("Exit") ){
			  	System.out.println("Client from : " + socket1Use.getAddress() + " ------------> Is now Disconnected!");
				connection = false;
				break; 
			  }
			  else if( receivedMsg.equals("Register") ){

			  	 System.out.println(" \n New Auction Registry! \n");

			  	 for (int i = 0; i <= 3; i++ ){

			  	 	  if ( i == 0 || i == 1 ){

			  	 	  	 registryMsg = dataInStream.readUTF();
			  		     auctions[id][i] = registryMsg;
			  		     System.out.println("Recorded : ' " + registryMsg + " ' to --> " + auctions[id][i]);
			  		     responceMsg += " - " + auctions[id][i];
			  	         registryMsg = null;

			  	 	    } 
			  	 	  else if (i == 2) {


			  	 	     registryInt = dataInStream.readInt();
			  		     bidsNstartingPrice[id][i-2] = registryInt;
			  		     System.out.println("Recorded : ' " + registryInt + " ' to --> " + bidsNstartingPrice[id][i-2]);
			  		     responceMsg += " - " + bidsNstartingPrice[id][i-2];
			  	         registryInt = 0; 
			  	 	    }
			  	 	    else {
			  	 	      bidsNstartingPrice[id][i-2] = 0;
			  	    	  responceMsg += " - " + bidsNstartingPrice[id][i-2];
			  	 	    }
			  	    }

			  	 responceMsg = "Your auction has been Recorded : \n " + responceMsg + "\n Your AuctionID is : " + id;
			  	 dataOutStream.writeUTF(responceMsg);
			  	 dataOutStream.flush();



			  	 for (int i = 0; i <= id; i++ ){

			  	 	message += "\n";

			  		 for (int j = 0; j<= 1; j++){
			  			 message += " - " + auctions[i][j];
			  		    }
			  		 for ( int x = 0; x<= 1; x++){
			  		 	message += " - " + bidsNstartingPrice[i][x];
			  		 }
			  	    }

			  	 System.out.println("Recorded Auctions : \n" + message);

			     id ++;
			    }
			}
	    }
    }
    // public static String recordedMessage(String registryMsg, int id, int j, bidsNstartingPrice, auctions){
    // 	String message;
    // 	message = "Recorded : ' " + registryMsg + " ' to --> " + bidsNstartingPrice[id][j]
    // 	return message;
    // }
}
