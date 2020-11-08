package server.interfaces;

import java.net.Socket;

public interface ClientManager {
    String handleClient(Socket socket);
    void closeAllConnections();
    GameClient getClientById(String id);
}
