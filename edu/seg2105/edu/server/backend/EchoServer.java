package edu.seg2105.edu.server.backend;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 


import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) 
  {
    super(port);
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  {
    System.out.println("Message received: " + msg + " from " + client);
    this.sendToAllClients(msg);
  }
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }
  
  /**
   * Implements hook method in the superclass. run() method in ConnectionToClient calls
   * clientConnected(...) method below.
   * 
   * Prints out a nice message whenever a client connects.
   **/
  @Override
  protected void clientConnected(ConnectionToClient client){
	  System.out.println("New client "+client+" connected to the server");
  }
  
  /**
   * Implements hook method in the superclass. close() method in ConnectionToClient calls
   * clientDisconnected(...) method below. 
   * 
   * Prints out a nice message whenever a client disconnects.
   **/
  @Override
  synchronized protected void clientDisconnected(ConnectionToClient client) {
		// Since we don't track which ID belongs to this client directly,
		// remove by value.
	  System.out.println("Unknown client disconnected from the server");
	  /*
	   * Note: I did not write the line above as "System.out.println(client+"disconnected from the server");"
	   *      since it would print null as the client is null. Once we will be adding the Id (in exercise 3), 
	   *      we will have more adapted code. Until then, this is my solution to that problem.
	   * */
	  super.clientDisconnected(client); //Since we do not have access to the private instance clientConnections
	}
  
  /**
   * Implements hook method in the superclass. Called by the OCSF framework when a client
   * disconnects abruptly. 
   * 
   * Prints out a nice message whenever a client disconnects unexpectedly.
   **/
  @Override
  synchronized protected void clientException(ConnectionToClient client, Throwable exception) {
	  System.out.println("Unknown client disconnected unexpectedly from the server: "+exception.getMessage());
	  /*
	   * Note: I did not write the line above as "System.out.println(client+"disconnected unexpectedly from the server");"
	   *      since it would print null as the client is null. Once we will be adding the Id (in exercise 3), 
	   *      we will have more adapted code. Until then, this is my solution to that problem.
	   * */
	  super.clientDisconnected(client); //Since we do not have access to the private instance clientConnections
  }
  
  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
  public static void main(String[] args) 
  {
    int port = 0; //Port to listen on

    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }
	
    EchoServer sv = new EchoServer(port);
    
    try 
    {
      sv.listen(); //Start listening for connections
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
  }
}
//End of EchoServer class
