package com.timmax.example.websocket.p03.client;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class UnifyWebSocketClient extends WebSocketClient {
    private final LikeWebSocketClient likeWebSocketClient;


    public UnifyWebSocketClient(
            URI serverUri, Draft protocolDraft
            , LikeWebSocketClient likeWebSocketClient
    ) {
        super( serverUri, protocolDraft);

        this.likeWebSocketClient = likeWebSocketClient;
    }

    @Override
    public void onMessage( String message) {
        likeWebSocketClient.inMessage( "got: " + message + "\n");
    }

    @Override
    public void onOpen( ServerHandshake handshake) {
        String message = "You are connected to ChatServer: " + getURI( ) + "\n";
        likeWebSocketClient.inOpen( handshake, message);
    }

    @Override
    public void onClose( int code, String reason, boolean remote) {
        String message =
                "You have been disconnected from: " + getURI( )
                + "; Code: " + code
                + " " + reason + "\n";
        likeWebSocketClient.inClose( code, reason, remote, message);
    }

    @Override
    public void onError( Exception exception) {
        String message = "Exception occurred ...\n" + exception + "\n";
        likeWebSocketClient.inError( exception, message);
    }
}