package com.timmax.example.websocket.p03.client.console;

import com.timmax.example.websocket.p03.client.LikeWebSocketClient;
import com.timmax.example.websocket.p03.client.UnifyWebSocketClient;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import static java.lang.System.exit;

/**
 * This example demonstrates how to create a websocket connection to a server. Only the most
 * important callbacks are overloaded.
 */
public class ConsoleChatClient implements LikeWebSocketClient {
    private final WebSocketClient webSocketClient;
    public ConsoleChatClient(URI serverURI) throws IOException {
        webSocketClient =
                new UnifyWebSocketClient(
                        serverURI // new URI( uriJTextField.getText( ))
                        , new Draft_6455() // (Draft) draftJComboBox.getSelectedItem( )
                        , this
                );
        webSocketClient.connect();

        BufferedReader sysInBufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String readLine = sysInBufferedReader.readLine();
            if (readLine.equals("exit")) {
                exit(0);
            }
            webSocketClient.send( readLine);
        }
    }

    @Override
    public void inOpen(ServerHandshake serverHandshake, String message) {
        System.out.println(message);
        // if you plan to refuse connection based on ip or httpfields overload: onWebsocketHandshakeReceivedAsClient
    }

    @Override
    public void inMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void inClose(int code, String reason, boolean remote, String message) {
        // The close codes are documented in class org.java_websocket.framing.CloseFrame
        System.out.println( message);
    }

    @Override
    public void inError(Exception exception, String message) {
        System.err.println( message);
        // if the error is fatal then onClose will be called additionally
    }
}