package server;

import java.io.IOException;

public class MyGameServer extends AbstractGameServer {
    public MyGameServer(int port) throws IOException {
        super(port);
    }

    @Override
    public void onConnect(GameClient client) {
        System.out.println("[Server]: client " + client.getClientId() + " connected");
    }

    @Override
    public void onMessage(GameClient client, String message) {
        System.out.println("[Server]: client " + client.getClientId() + " messaged: " + message);
        client.send(message);
    }

    @Override
    public void onDisconnect(String clientId, String reason) {
        System.out.println("[Server]: client " + clientId + " disconnect; reason:" + reason);
    }
}
