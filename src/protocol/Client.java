package protocol;

public class Client 
{public static final java.util.Logger log=java.util.Logger.getLogger(Client.class.getName());
 private String name;
 private java.net.Socket socket;
 private java.io.PrintWriter outgoing;
 private java.io.BufferedReader incoming;
 private Browser browser;
 //private static Deque<String> responses;

 public Client(String name,String host,int port,Browser browser)
{this.name=name;
 try
{this.socket=new java.net.Socket(host,port);
 this.incoming=new java.io.BufferedReader(new java.io.InputStreamReader(socket.getInputStream()));
 this.outgoing=new java.io.PrintWriter(socket.getOutputStream(),true);log.info(host + " connected");
}catch(java.net.UnknownHostException exception){System.err.println(host+":"+port+" is not online.");System.exit(1);}
 catch(java.io.IOException exception){System.err.println(host+":"+port+" didn't hear You clearly.");System.exit(1);}
 this.browser=browser;
}
 public void listen()
{while(socket.isConnected())
{try
{String response=incoming.readLine();
 if(response!=null) javafx.application.Platform.runLater(new Runnable()
{@Override public void run()
{browser.bubble(response,true); log.info("response received: "+response);
}
});
}catch(java.io.IOException fail){fail.printStackTrace();};
}
}
 public void contact(String request)
{if(request!=null)
{outgoing.println(request);log.info("request sent: "+request);
 browser.bubble(request,false); 
 if(request.equals("Bye"))System.exit(1);
}
}
 public static void main(String[] arguments) throws java.io.IOException
{if(arguments.length==3)terminal(arguments);
 else javafx.application.Application.launch(Browser.class);
}
 private static void terminal(String[] arguments) throws java.io.IOException
{try
{java.net.Socket socket=new java.net.Socket(arguments[1],Integer.parseInt(arguments[2]));
 java.io.PrintWriter outgoing = new java.io.PrintWriter(socket.getOutputStream(), true);
 java.io.BufferedReader incoming = new java.io.BufferedReader(new java.io.InputStreamReader(socket.getInputStream())); 
 Thread thread = new Thread(new Runnable()
{@Override public void run()
{while(socket.isConnected())
{try
{String response=incoming.readLine();
 if(response!=null)System.out.println(response);
}catch(java.io.IOException fail){fail.printStackTrace();};
}
}
});
 thread.setDaemon(true);
 thread.start();
 while(socket.isConnected())
{java.io.BufferedReader input=new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
 String request=input.readLine();
 outgoing.println(request);
}
}catch(java.net.UnknownHostException exception){exception.printStackTrace();System.exit(1);}
 catch(java.io.IOException exception){exception.printStackTrace();System.exit(1);}
}
//public void ping(Object packet){}
//public void trace(Object packet){}
}
