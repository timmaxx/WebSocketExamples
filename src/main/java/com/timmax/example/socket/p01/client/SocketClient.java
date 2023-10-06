package com.timmax.example.socket.p01.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {
    public static void main(String[] args) {
        try ( Socket socket = new Socket()) {
            socket.connect( (new InetSocketAddress( InetAddress.getLocalHost(), 80)), 2000);
            System.out.println( "There is connection to server.");
            Scanner scanner = new Scanner( socket.getInputStream());
            PrintWriter printWriter = new PrintWriter( socket.getOutputStream(), true);
            for (int i = 0; i < 10; i++) {
                printWriter.println( "you've send: " + i);
            }
            while (scanner.hasNextLine()) {
                System.out.println( scanner.nextLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
