package com.timmax.example.websocket.p03.client.console;

import com.timmax.example.websocket.p03.client.LikeWebSocketClient;
import com.timmax.example.websocket.p03.client.UnifyWebSocketClient;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import static java.lang.System.exit;

public class ConsoleChatClient implements LikeWebSocketClient {
    private final WebSocketClient webSocketClient;


    public ConsoleChatClient( URI serverURI) throws IOException {
        webSocketClient = new UnifyWebSocketClient( serverURI, this);
        webSocketClient.connect( );

        BufferedReader sysInBufferedReader = new BufferedReader( new InputStreamReader( System.in));
        while ( true) {
            String readLine = sysInBufferedReader.readLine( );
            if ( readLine.equals( "exit")) {
                exit(0);
            }
            webSocketClient.send( readLine);
        }
    }

    @Override
    public void inOpen( ServerHandshake serverHandshake, String message) {
        System.out.println( message);
    }

    @Override
    public void inMessage( String message) {
        System.out.println( message);
    }

    @Override
    public void inClose( int code, String reason, boolean remote, String message) {
        System.out.println( message);
    }

    @Override
    public void inError( Exception exception, String message) {
        System.err.println( message);
    }
}