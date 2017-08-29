package protocol;

import javafx.application.Application;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Deque;
import java.util.logging.Logger;

/**
 * TCP network communication session
 */
public class Client {
    public static final Logger log = Logger.getLogger(Client.class.getName());

    public static int port;
    public static String host;
    public static PrintWriter outgoing;
    public static BufferedReader incoming;
    private static Deque<String> responses;
    public Browser browser;

    public static Client session = new Client();

    private Client(){}

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Usage: java protocol.Client <host> <port>");
            System.exit(1);
        }
        host = args[0];
        port = Integer.parseInt(args[1]);
        log.info("address specified");

        try {
            Socket socket = new Socket(host, port);
            outgoing = new PrintWriter(socket.getOutputStream(), true);
            incoming = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            log.info(host + " picked up");
        } catch (UnknownHostException exception) {
            System.err.println(host + " is not at home.");
            System.exit(1);
        } catch (IOException exception) {
            System.err.println(host + " didn't hear You clearly.");
            System.exit(1);
        }
        log.info("connection established");

        //terminal()
        Application.launch(Browser.class);
        log.info("interface provided");
    }

    private String terminal() throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String request = input.readLine();
        return contact(request);
    }

    public String contact(String request) {
        if (request != null) {
            outgoing.println(request);
            log.info("message sent");
            if (request.equals("Bye")) {
                System.exit(1);
            }
        }
        return listen();
    }

    /**
     * Streaming responses, to be separated from displaying stream on deque.
     * @return boolean whether response arrived. Or yet, the string itself.
     */
    private String listen() {
        String response = null;
        try {
            response = incoming.readLine();
            //responses.offer(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response != null) {
            System.out.println(response);
            log.info("message received");
        }
        //return responses.poll();
        return response;
    }
}
