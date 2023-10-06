package com.timmax.example.websocket.p03.client;

import org.java_websocket.handshake.ServerHandshake;

public interface LikeWebSocketClient {
    void inOpen( ServerHandshake serverHandshake, String message);
    void inMessage( String message);
    void inClose( int code, String reason, boolean remote, String message);
    void inError( Exception exception, String message);
}