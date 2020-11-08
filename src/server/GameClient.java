package server;

import server.interfaces.SocketEvent;

import java.io.*;
import java.net.Socket;

public class GameClient implements Runnable {
    private Socket socket;
    private String clientId;
    private Thread currentThread;
    private PrintWriter outputStream;
    private BufferedReader inputStream;
    private SocketEvent socketEvent;
    private boolean isClosed = true;

    public GameClient(String clientId, Socket socket) {
        this.socket = socket;
        this.clientId = clientId;
        try {
            this.socket.setKeepAlive(true);
            this.outputStream = new PrintWriter(socket.getOutputStream(), true);
            this.inputStream = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            isClosed = false;
        } catch(Exception ex) {
            close("Could not create data streams");
        }
    }

    public void listen(SocketEvent socketEvent) {
        this.socketEvent = socketEvent;
        this.socketEvent.onConnect(this);
        currentThread = new Thread(this);
        System.out.println("Client handling started on thread " + currentThread.getId());
        currentThread.start();
    }

    public void close(String reason) {
        isClosed = true;
        try {
            this.currentThread.interrupt();
            this.inputStream.close();
            this.outputStream.close();
            this.socket.close();
            socketEvent.onDisconnect(clientId, reason);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(String message) {
        outputStream.println(message);
    }

    @Override
    public void run() {
        try {
            String line;
            while ((line = inputStream.readLine()) != null) {
                System.out.print(line);
                socketEvent.onMessage(this, line);
            }
        } catch(Exception ex) {
            System.out.println("dffffffffffffffffffffffffffffffffffffffffffffffffffffff");
        } finally {
            close("Socket disconnected on purpose");
        }
    }

    public String getClientId() {
        return clientId;
    }
}
