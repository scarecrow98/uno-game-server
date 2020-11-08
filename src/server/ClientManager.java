package server;

import server.interfaces.SocketEvent;

import java.net.Socket;
import java.util.HashMap;
import java.util.UUID;


public class ClientManager {
    private HashMap<String, GameClient> clients;
    private AbstractGameServer server;

    public ClientManager(AbstractGameServer server) {
        this.server = server;
        this.clients = new HashMap<>();
    }

    public String handleClient(Socket socket) {
        String uid = UUID.randomUUID().toString();
        GameClient client = new GameClient(uid, socket);
        clients.put(uid, client);

        client.listen(new SocketEvent() {
            @Override
            public void onConnect(GameClient client) {
                server.onConnect(client);
            }

            @Override
            public void onMessage(GameClient client, String message) {
                server.onMessage(client, message);
            }

            @Override
            public void onDisconnect(String clientId, String reason) {
                clients.remove(clientId);
                server.onDisconnect(clientId, reason);
            }
        });

        return uid;
    }

    public void closeConnections() {
        clients.forEach((clientId, client) -> client.close("Server is shutting down"));
    }
}
