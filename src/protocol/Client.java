package protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Deque;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.application.Platform;

public class Client {
    public static final Logger log = Logger.getLogger(Client.class.getName());

	private String name;
    private Socket socket;
    private PrintWriter outgoing;
    private BufferedReader incoming;
    private Browser browser;
    //private static Deque<String> responses;

    public Client(String name,String host,int port,Browser browser){
		this.name=name;
		try{this.socket=new Socket(host,port);
		    this.incoming=new BufferedReader(new InputStreamReader(socket.getInputStream())); 
		    this.outgoing=new PrintWriter(socket.getOutputStream(), true); log.info(host + " connected");}
		catch (UnknownHostException exception) { System.err.println(host+":"+port+" is not online."); System.exit(1);}
		catch (IOException exception) { System.err.println(host+":"+port+" didn't hear You clearly."); System.exit(1);}
		this.browser=browser;
	}

    public void listen() {
        while(socket.isConnected()) {
	        try {
				String response=incoming.readLine();
                if(response!=null) Platform.runLater(new Runnable(){
                    @Override 
                    public void run(){
                        browser.bubble(response,true); log.info("response received: "+response);
                    }
                });
	        } catch (IOException fail) {fail.printStackTrace();};
        }
    }

	public void contact(String request) {
        if (request != null) {
            outgoing.println(request); log.info("request sent: "+request);
			browser.bubble(request,false); 
            if (request.equals("Bye")) System.exit(1);
        }
    }
	
	public static void main(String[] arguments) throws IOException {
        if (arguments.length == 3) terminal(arguments);
        else Application.launch(Browser.class);
    }
	
    private static void terminal(String[] arguments) throws IOException {
        try {
            Socket socket = new Socket(arguments[1], Integer.parseInt(arguments[2]));
            PrintWriter outgoing = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader incoming = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
   			Thread thread = new Thread(new Runnable(){
   	            @Override 
   	            public void run(){
   		    		while(socket.isConnected()) {
   			    	    try {
   				    	    String response=incoming.readLine();
   	                        if(response!=null) System.out.println(response);
   	                    } catch (IOException fail) {fail.printStackTrace();};
   		    	    }
   			    }
   			});
   	        thread.setDaemon(true);
   	        thread.start();
   			while(socket.isConnected()) {
   	            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
   	            String request = input.readLine();
   				outgoing.println(request);
   	   	    }
        } catch (UnknownHostException exception) { exception.printStackTrace(); System.exit(1);
        } catch (IOException exception) { exception.printStackTrace(); System.exit(1);
        }
    }
/*
    public void ping(Object packet){
    }
    public void trace(Object packet){
    }
*/
}
