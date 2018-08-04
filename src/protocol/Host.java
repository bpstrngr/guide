package protocol;

import java.io.*;
import java.net.*;
import java.util.Deque;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Host {
    public static final Logger log = Logger.getLogger(Host.class.getName());

    private static java.net.ServerSocket serverSocket = null;
    private static java.net.Socket clientSocket = null;
    private static java.io.PrintWriter outgoing;
    private static java.io.BufferedReader incoming;
    private static String request, response;
    private static java.util.Deque<String> requests;
    private static Protocol protocol = new Protocol();

    public static void main(String[] args) throws IOException {
        try {
            serverSocket = new java.net.ServerSocket(4444);
            clientSocket = serverSocket.accept();
            outgoing = new java.io.PrintWriter(clientSocket.getOutputStream(), true);
            incoming = new java.io.BufferedReader(new java.io.InputStreamReader(clientSocket.getInputStream()));
        } catch (java.io.IOException e) {
            System.err.println("Port 4444 was either blocked or occupied by client.");
            System.exit(1);
        }
        log.info("service started");
        while(request != "Bye!" || response != "Bye!") {
            communicate();
        }
        outgoing.close();
        incoming.close();
        clientSocket.close();
        serverSocket.close();
    }

    public static void communicate() throws java.io.IOException {
        if (protocol.state.ignorecase == null) {
            response = protocol.processRequest(null);
            outgoing.println(response);
        } else if ((request = incoming.readLine()) != null) { log.info("message received: "+request);
            //requests.offer(response);
            response = protocol.processRequest(request);
            outgoing.println(response); log.info("response sent: "+ response);
        }
    }
}
