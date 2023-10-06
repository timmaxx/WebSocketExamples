package com.timmax.example.socket.p01.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SocketServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket( 80)) {
            Socket socket = serverSocket.accept();
            System.out.println("A client connected.");
            Scanner scanner = new Scanner( socket.getInputStream());
            PrintWriter printWriter = new PrintWriter( socket.getOutputStream(), true);
            while (scanner.hasNextLine()) {
                String str = scanner.nextLine();
                printWriter.println( "you've send: " + str);
                System.out.println( str);
                if (str.equals("exit")) {
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
