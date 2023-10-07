package com.timmax.example.websocket.p03.client;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class UnifyWebSocketClient extends WebSocketClient {
    private final LikeWebSocketClient likeWebSocketClient;


    public UnifyWebSocketClient( URI serverUri, LikeWebSocketClient likeWebSocketClient) {
        super( serverUri);

        this.likeWebSocketClient = likeWebSocketClient;
    }

    @Override
    public void onMessage( String message) {
        likeWebSocketClient.inMessage( "got: " + message);
    }

    @Override
    public void onOpen( ServerHandshake handshake) {
        String message = "You are connected to ChatServer: " + getURI( );
        likeWebSocketClient.inOpen( handshake, message);
    }

    @Override
    public void onClose( int code, String reason, boolean remote) {
        String message =
                "You have been disconnected from: " + getURI( )
                + "; Code: " + code
                + "; Reason: " + reason + ".";
        likeWebSocketClient.inClose( code, reason, remote, message);
    }

    @Override
    public void onError( Exception exception) {
        String message = "Exception occurred ...\n" + exception;
        likeWebSocketClient.inError( exception, message);
    }
}