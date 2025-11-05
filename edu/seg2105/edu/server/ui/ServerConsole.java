package edu.seg2105.edu.server.ui;

import edu.seg2105.client.common.*;
import edu.seg2105.edu.server.backend.EchoServer;

import java.io.*;
import java.util.Scanner;

public class ServerConsole implements ChatIF{
	
	//Class variables *************************************************
	  
	  /**
	   * The default port to connect on.
	   */
	  final public static int DEFAULT_PORT = 5555;
	
	//Instance variables **********************************************
	  
	/**
	  * The instance of the server that created this ConsoleChat.
	  */
	EchoServer server;
	
	/**
	  * Scanner to read from the console
	  */
	Scanner fromConsole;
	
	//Constructors ****************************************************

	  /**
	   * Constructs an instance of the ServerConsole UI.
	   *
	   * @param port The port to connect on.
	   */
	public ServerConsole(int port) {
		try {
			server=new EchoServer(port, this);
			server.listen();
		}catch(IOException e) {
			System.out.println("Error: Can't listen to new connections!"
	                + " Terminating server.");
	      System.exit(1);
		}
		fromConsole=new Scanner(System.in);
	}
	
	//Instance methods ************************************************
	/**
	   * This method waits for input from the console.  Once it is 
	   * received, it sends it to the client's message handler.
	   */
	  public void accept(){
	    try {
	    	String message;
	    	while(true) {
	    		message = fromConsole.nextLine();
	            server.handleMessageFromServerUI(message);
	    	}
	    }catch(Exception e) {
	    	System.out.println
	        ("Unexpected error occurred while reading from console!");
	    }
	  }
	
	/**
	   * This method overrides the method in the ChatIF interface.  It
	   * displays a message onto the screen.
	   *
	   * @param message The string to be displayed.
	   */
	  @Override
	  public void display(String message){
	    System.out.println("SERVER MSG> " + message);
	  }
	
	//Class methods ***************************************************

	public static void main(String[] args) {
		int port = 0; //Port to listen on

	    try
	    {
	      port = Integer.parseInt(args[0]); //Get port from command line
	    }
	    catch(Throwable t)
	    {
	      port = DEFAULT_PORT; //Set port to 5555
	    }
	    
	    ServerConsole sc=new ServerConsole(port); //Starts listening for connections
	    sc.accept(); //Wait for console data from the server
	}
}
