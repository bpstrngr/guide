class Hypertext
{public static void main(String[] context)
{try
{java.net.ServerSocket port=new java.net.ServerSocket(new Integer(context[0]));
 while(true)
{java.net.Socket client=port.accept();
 java.io.BufferedReader request=new java.io.BufferedReader(new java.io.InputStreamReader(client.getInputStream()));
 java.io.PrintWriter response=new java.io.PrintWriter(client.getOutputStream(),true);
 while(request.readLine()!=null)
{if(request.readLine().length()==0)
 response.println("HTTP/1.1 200 OK\r\n\r\n<html><head></head><body>"+context[1]+"</body></html>");
}request.close();
 response.close();
 client.close();
 port.close();
}}catch(java.io.IOException report){report.printStackTrace();};
}
}
