package echoserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andrewtaylor
 */
public class Client {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private ClientService clientService;
    private EchoServer server;
    
    public Client(Socket socket, EchoServer server) {
        this.socket = socket;
        this.server = server;
    }
    
    public Socket getSocket() {
        return socket;
    }
    
    public BufferedReader getBufferedReader() {
        if (in == null) {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return in;
    }
    
    public PrintWriter getPrintWriter() {
        if (out == null) {
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return out;
    }
    
    public ClientService getClientService() {
        if (clientService == null) {
            clientService = new ClientService(this, server);
        }
        
        return clientService;
    }
    
    public void close() {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        server.getClients().remove(this);
    }
}
