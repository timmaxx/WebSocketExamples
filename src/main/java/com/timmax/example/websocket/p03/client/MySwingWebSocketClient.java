package com.timmax.example.websocket.p03.client;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import javax.swing.*;
import java.net.URI;

public class MySwingWebSocketClient extends WebSocketClient {
    private final JTextArea ta;
    private final JButton connect;
    private final JTextField uriField;
    private final JButton close;
    private final JComboBox draft;

    public MySwingWebSocketClient(
            URI serverUri, Draft protocolDraft
            , JTextArea ta
            , JButton connect
            , JTextField uriField
            , JComboBox draft
            , JButton close
    ) {
        super( serverUri, protocolDraft);
        this.ta = ta;
        this.connect = connect;
        this.uriField = uriField;
        this.draft = draft;
        this.close = close;
    }

    @Override
    public void onMessage( String message) {
        ta.append( "got: " + message + "\n");
        ta.setCaretPosition( ta.getDocument( ).getLength( ));
    }

    @Override
    public void onOpen( ServerHandshake handshake) {
        ta.append( "You are connected to ChatServer: " + getURI( ) + "\n");
        ta.setCaretPosition( ta.getDocument( ).getLength( ));
    }

    @Override
    public void onClose( int code, String reason, boolean remote) {
        ta.append(
                "You have been disconnected from: " + getURI( ) + "; Code: " + code + " " + reason
                        + "\n");
        ta.setCaretPosition( ta.getDocument( ).getLength( ));
        connect.setEnabled( true);
        uriField.setEditable( true);
        draft.setEditable( true);
        close.setEnabled( false);
    }

    @Override
    public void onError( Exception ex) {
        ta.append( "Exception occurred ...\n" + ex + "\n");
        ta.setCaretPosition( ta.getDocument( ).getLength( ));
        ex.printStackTrace( );
        connect.setEnabled( true);
        uriField.setEditable( true);
        draft.setEditable( true);
        close.setEnabled( false);
    }
}