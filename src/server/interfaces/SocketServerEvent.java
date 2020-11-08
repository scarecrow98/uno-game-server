package server.interfaces;

import server.MyGameClient;

public interface SocketServerEvent {
    void onConnect(GameClient client);
    void onMessage(GameClient client, String message);
    void onDisconnect(String clientId, String reason);
    void onError(GameClient client, String error);
}
