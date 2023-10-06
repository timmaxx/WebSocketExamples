package com.timmax.example.websocket.p03.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A simple WebSocketServer implementation. Keeps track of a "chatroom".
 */
public class ChatServerStarter {
    public static void main(String[] args) throws InterruptedException, IOException {
        int port;
        try {
            port = Integer.parseInt(args[0]);
        } catch (Exception ex) {
            port = 8887; // 843 flash policy port
        }
        ChatServer chatServer = new ChatServer(port);
        chatServer.start();
        System.out.println("MyChatServer started on port: " + chatServer.getPort());

        BufferedReader sysInBufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String readLine = sysInBufferedReader.readLine();
            chatServer.broadcast(readLine);
            if (readLine.equals("exit")) {
                chatServer.stop(1000);
                break;
            }
        }
    }
}