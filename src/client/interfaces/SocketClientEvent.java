package client.interfaces;

public interface SocketClientEvent {
    void onConnect();
    void onMessage(String message);
    void onDisconnect(String reason);
}
