package com.timmax.example.websocket.p03.client.swing;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import javax.swing.*;
import java.net.URI;

public class SwingWebSocketClient extends WebSocketClient {
    private final JTextArea jTextArea;
    private final JButton connectJButton;
    private final JTextField uriField;
    private final JButton close;
    private final JComboBox draft;


    public SwingWebSocketClient(
            URI serverUri, Draft protocolDraft
            , JTextArea jTextArea
            , JButton connectJButton
            , JTextField uriJTextField
            , JComboBox draftJComboBox
            , JButton closeJButton
    ) {
        super( serverUri, protocolDraft);
        this.jTextArea = jTextArea;
        this.connectJButton = connectJButton;
        this.uriField = uriJTextField;
        this.draft = draftJComboBox;
        this.close = closeJButton;
    }

    @Override
    public void onMessage( String message) {
        jTextArea.append( "got: " + message + "\n");
        jTextArea.setCaretPosition( jTextArea.getDocument( ).getLength( ));
    }

    @Override
    public void onOpen( ServerHandshake handshake) {
        jTextArea.append( "You are connected to ChatServer: " + getURI( ) + "\n");
        jTextArea.setCaretPosition( jTextArea.getDocument( ).getLength( ));
    }

    @Override
    public void onClose( int code, String reason, boolean remote) {
        jTextArea.append(
                "You have been disconnected from: " + getURI( ) + "; Code: " + code + " " + reason
                        + "\n");
        jTextArea.setCaretPosition( jTextArea.getDocument( ).getLength( ));
        connectJButton.setEnabled( true);
        uriField.setEditable( true);
        draft.setEditable( true);
        close.setEnabled( false);
    }

    @Override
    public void onError( Exception exception) {
        jTextArea.append( "Exception occurred ...\n" + exception + "\n");
        jTextArea.setCaretPosition( jTextArea.getDocument( ).getLength( ));
        exception.printStackTrace( );
        connectJButton.setEnabled( true);
        uriField.setEditable( true);
        draft.setEditable( true);
        close.setEnabled( false);
    }
}