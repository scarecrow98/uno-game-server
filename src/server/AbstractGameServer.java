package server;

import server.interfaces.ClientManager;
import server.interfaces.GameServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

abstract public class AbstractGameServer implements GameServer {
    private int port;
    private boolean isStopped = true;
    private ClientManager myClientManager;
    private ServerSocket serverSocket;

    public AbstractGameServer(int port) throws IOException {
        this.port = port;
        this.myClientManager = new MyClientManager(this);
        this.serverSocket = new ServerSocket(port);
    }

    public void start() {
        System.out.println("Server is listening on port " + port + "...");
        isStopped = false;
        try {
            launch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void launch() throws IOException {
        while (!isStopped) {
            try {
                Socket clientSocket = serverSocket.accept();
                String clientId = myClientManager.handleClient(clientSocket);
            } catch(SocketException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void stop() {
        try {
            isStopped = true;
            serverSocket.close();
            myClientManager.closeAllConnections();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
