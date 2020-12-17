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
	  int receivedInt;

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
			  else if( receivedMsg.equals("Exit") ){

			  	System.out.println("Client from : " + socket1Use.getAddress() + " ------------> Is now Disconnected!");
				connection = false;
				break;

			  	 
				}
				/*argy */
				else if (receivedMsg.equals("Search")) {
					System.out.println("Client is entering auctionID");
					receivedInt = dataInStream.readInt();
					System.out.println("Client entered " + receivedInt);
					//tell to the user the item with the auction id given
					dataOutStream.writeUTF("Item name:" + auctions[receivedInt][0]+ "\nItem Description: " + auctions[receivedInt][1] +"\nItem Starting Price " + bidsNstartingPrice[receivedInt][0]  + "\nItem Current Bid: "  + bidsNstartingPrice[receivedInt][1]);
				}
				//argy
				else if (receivedMsg.equals("Show")) {
					//i changed to id-1 because if it was id it would show one more null option
					for (int i = 0; i <= id-1; i++ ){

						message += "\n";
 
						for (int j = 0; j<= 1; j++){
 
							message += " - " + auctions[i][j];
 
						   }
 
						for ( int x = 0; x<= 1; x++){
 
							 message += " - " + bidsNstartingPrice[i][x];
						   }
					   }
 
					System.out.println("Recorded Auctions : \n" + message);
					dataOutStream.writeUTF(message);
					dataOutStream.flush();
				}
				
			}
	    }
	}


	/* 
	Method Name: registerInStringArray
	Author: Fotakidis Panagiotis
	Arguments: 
		responceMsg: A string collected by the method that will hold the message that will be eventually sent back to the user to confirm his registration.
		dataInStream: An input stream that allows the incoming information ( from the client ) to be read and utilised.

    */
    public static String registerInStringArray(String responceMsg, DataInputStream dataInStream, String [][] array, int id, int i)throws java.io.IOException{

    		String registryMsg = dataInStream.readUTF();

    		array[id][i] = registryMsg;
			System.out.println("Recorded : ' " + registryMsg + " ' to --> " + array[id][i]);

			responceMsg += " - " + array[id][i];
			return responceMsg;
    }

    public static String registerInIntegerArray(String responceMsg, DataInputStream dataInStream, int [][] array, int id, int i)throws java.io.IOException{

        	int registryInt = dataInStream.readInt();

    		array[id][i-2] = registryInt;
			System.out.println("Recorded : ' " + registryInt + " ' to --> " + array[id][i-2]);

			responceMsg += " - " + array[id][i-2];
			return responceMsg;
    }

        public static void sendMsg (String message, DataOutputStream dataOutStream)throws java.io.IOException {
    	dataOutStream.writeUTF(message);
		dataOutStream.flush();
	}
}
