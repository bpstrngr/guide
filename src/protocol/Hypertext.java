class Hypertext
{public static void main(String[] context){try
{java.net.ServerSocket port=new java.net.ServerSocket(new Integer(context[0]));
 while(true)
{java.net.Socket client=port.accept();
 java.io.BufferedReader incoming=new java.io.BufferedReader(new java.io.InputStreamReader(client.getInputStream()));
 java.io.PrintWriter outgoing=new java.io.PrintWriter(client.getOutputStream(),true);
 while(incoming.readLine()!=null)
{if(incoming.readLine().length()==0)
 outgoing.println("HTTP/1.1 200 OK\r\n\r\n<html><head></head><body>"+context[1]+"</body></html>");
}incoming.close();
 outgoing.close();
 client.close();
}}catch(java.io.IOException io){io.printStackTrace();};
}
}
