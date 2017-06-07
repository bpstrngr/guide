package protocol;

import java.io.*;
import java.net.*;
import java.util.Deque;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * TCP service routine
 * */
public class Host {
    public static final Logger log = Logger.getLogger(Host.class.getName());

    private static ServerSocket serverSocket = null;
    private static Socket clientSocket = null;
    private static PrintWriter outgoing;
    private static BufferedReader incoming;
    private static String request, response;
    private static Deque<String> requests;
    private static Protocol protocol = new Protocol();

    /**
     * Primary.
     * Prone to {@link IOException}.
     * */
    public static void main(String[] args) throws IOException {
        try {
            serverSocket = new ServerSocket(4444);
            clientSocket = serverSocket.accept();
            outgoing = new PrintWriter(clientSocket.getOutputStream(), true);
            incoming = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
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

    /**
     *
     * @throws IOException
     */
    public static void communicate() throws IOException {
        if (protocol.state.ignorecase == null) {
            response = protocol.processRequest(null);
            outgoing.println(response);
        } else if ((request = incoming.readLine()) != null) {
            //requests.offer(response);
            log.info("message received");
            response = protocol.processRequest(request);
            outgoing.println(response);
            log.info("response sent");
        }
    }
}