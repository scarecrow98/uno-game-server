package server.interfaces;

public interface GameClient extends Runnable {
    void listen(SocketServerEvent listener);
    void send(String message);
    void close(String reason);
    String getClientId();
}
