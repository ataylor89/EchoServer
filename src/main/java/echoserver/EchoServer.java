package echoserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 *
 * @author andrewtaylor
 */
public class EchoServer {
    
    private ServerSocket serverSocket;
    private final int port = 8200;
    
    private ConnectionListener connectionListener;
    private Vector<Client> clients;

    public EchoServer() {
        clients = new Vector<>();
    }
    
    public void start() throws IOException {
        System.out.println("EchoServer: Starting Echo Server");
        serverSocket = new ServerSocket(port);
        System.out.println("EchoServer: Listening on port " + port);
        System.out.println("EchoServer: Accepting clients");
        connectionListener = new ConnectionListener(this, clients);
        connectionListener.start();
        System.out.println("EchoServer: Echoing messages");
    }
    
    public void echo(String msg) {
        for (int i = 0; i < clients.size(); i++) {
            PrintWriter out = clients.get(i).getPrintWriter();
            out.println(msg);
            System.out.println("EchoServer: Echoed client message");
        }
    }
    
    public ServerSocket getServerSocket() {
        return serverSocket;
    }
    
    public static void main(String[] args) throws IOException {
        EchoServer server = new EchoServer();
        server.start();
    }
}
