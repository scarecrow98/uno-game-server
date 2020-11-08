package client;

import client.interfaces.SocketClientEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GameServerConnector implements Runnable {
    private Socket socket;
    private String hostName;
    private int port;
    private PrintWriter outputStream;
    private BufferedReader inputStream;
    private SocketClientEvent socketClientEvent;
    private Thread currentThread;

    public GameServerConnector(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
        try {
            socket = new Socket(hostName, port);
            socket.setKeepAlive(true);
            inputStream = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            outputStream = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String message) {
        outputStream.println(message);
    }

    public void close() {
        try {
            inputStream.close();
            outputStream.close();
            socket.close();
            socketClientEvent.onDisconnect("csak");
        } catch(Exception ex) {
            System.out.println("exception:" + ex.getMessage());
        }
    }

    public void listen(SocketClientEvent socketClientEvent) {
        this.socketClientEvent = socketClientEvent;
        this.socketClientEvent.onConnect();
        currentThread = new Thread(this);
        currentThread.start();
    }

    @Override
    public void run() {
        try {
            String input;
            while ((input = inputStream.readLine()) != null) {
                this.socketClientEvent.onMessage(input);
            }
        } catch(Exception ex) {
            System.out.println("exception: " + ex.getMessage());
        } finally {
            close();
        }
    }
}
