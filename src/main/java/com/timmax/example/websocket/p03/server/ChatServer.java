package com.timmax.example.websocket.p03.server;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

/**
 * A simple WebSocketServer implementation. Keeps track of a "chatroom".
 */
public class ChatServer extends WebSocketServer {

    public ChatServer(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        webSocket.send("Welcome to the server!"); //This method sends a message to the new client
        broadcast("new connection: "
                + clientHandshake.getResourceDescriptor()); //This method sends a message to all clients connected
        System.out.println(
                webSocket.getRemoteSocketAddress().getAddress().getHostAddress() + " entered the room!");
    }

    @Override
    public void onClose(WebSocket webSocket, int code, String reason, boolean remote) {
        broadcast(webSocket + " has left the room!");
        System.out.println(webSocket + " has left the room!");
    }

    @Override
    public void onMessage(WebSocket webSocket, String message) {
        broadcast(message);
        System.out.println(webSocket + ": " + message);
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteBuffer message) {
        broadcast(message.array());
        System.out.println(webSocket + ": " + message);
    }

    @Override
    public void onError(WebSocket webSocket, Exception exception) {
        exception.printStackTrace();
        /*
        if (webSocket != null) {
            // some errors like port binding failed may not be assignable to a specific websocket
        }
        */
    }

    @Override
    public void onStart() {
        System.out.println("Server started!");
        setConnectionLostTimeout(0);
        setConnectionLostTimeout(100);
    }
}