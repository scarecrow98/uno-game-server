package server;

import server.interfaces.ClientManager;
import server.interfaces.GameClient;
import server.interfaces.SocketServerEvent;

import java.net.Socket;
import java.util.HashMap;
import java.util.UUID;


public class MyClientManager implements ClientManager {
    private HashMap<String, MyGameClient> clients;
    private AbstractGameServer server;

    public MyClientManager(AbstractGameServer server) {
        this.server = server;
        this.clients = new HashMap<>();
    }

    @Override
    public String handleClient(Socket socket) {
        String uid = UUID.randomUUID().toString();
        MyGameClient client = new MyGameClient(uid, socket);
        clients.put(uid, client);

        client.listen(new SocketServerEvent() {
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

            @Override
            public void onError(GameClient client, String error) {

            }
        });

        return uid;
    }

    @Override
    public void closeAllConnections() {
        clients.forEach((clientId, client) -> client.close("Server is shutting down"));
    }

    @Override
    public GameClient getClientById(String id) {
        return (clients.containsKey(id) ? clients.get(id) : null);
    }
}
