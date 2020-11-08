package server;

import server.interfaces.GameServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            GameServer server = new MyGameServer(8500);
            server.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
