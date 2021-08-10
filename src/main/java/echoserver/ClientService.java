package echoserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andrewtaylor
 */
public class ClientService extends Thread {
    
    private Client client;
    private EchoServer server;
    
    public ClientService(Client client, EchoServer server) {
        this.client = client;
        this.server = server;
    }
    
    @Override
    public void run() {
        while (client.getSocket().isConnected()) {        
            try {
                BufferedReader in = client.getBufferedReader();
                String line = in.readLine();

                if (line != null) {
                    System.out.println("Client: " + line);
                    server.echo(line);
                }
            } catch (IOException ex) {
                Logger.getLogger(EchoServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        client.close();
    }
}
