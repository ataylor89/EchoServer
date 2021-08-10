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
public class ConnectionListener extends Thread {
    
    private EchoServer server;
    private Vector<Client> clients;
    
    public ConnectionListener(EchoServer server, Vector<Client> clients) {
        this.server = server;
        this.clients = clients;
    }
    
    public void run() {
        while (true) {
            try {
                Socket socket = server.getServerSocket().accept();

                if (socket != null) {
                    Client client = new Client(socket, server);
                    clients.add(client);
                    client.getClientService().start();
                    System.out.println("EchoServer: Added client");
                }
                
                for (int i = 0; i < clients.size(); i++) {
                    if (!clients.get(i).getSocket().isConnected()) {
                        clients.get(i).getSocket().close();
                        clients.remove(i);
                    }
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
