package echoserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andrewtaylor
 */
public class ConnectionManager extends Thread {
    
    private EchoServer server;
    
    public ConnectionManager(EchoServer server) {
        this.server = server;
    }
    
    public void run() {
        while (true) {
            try {
                Socket socket = server.getServerSocket().accept();

                if (socket != null) {
                    Client client = new Client(socket, server);
                    server.getClients().add(client);
                    client.getClientService().start();
                    System.out.println("EchoServer: Added client");
                }
            } catch (IOException ex) {
                Logger.getLogger(EchoServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(EchoServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
