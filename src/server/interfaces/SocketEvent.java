package server.interfaces;

import server.GameClient;

public interface SocketEvent {
    void onConnect(GameClient client);
    void onMessage(GameClient client, String message);
    void onDisconnect(String clientId, String reason);
}
