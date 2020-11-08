package server.interfaces;

public interface GameServer extends SocketServerEvent {
    void start();
    void stop();
}
