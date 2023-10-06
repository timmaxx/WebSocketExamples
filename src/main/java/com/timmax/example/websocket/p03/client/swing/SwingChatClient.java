package com.timmax.example.websocket.p03.client.swing;

import com.timmax.example.websocket.p03.client.LikeWebSocketClient;
import com.timmax.example.websocket.p03.client.UnifyWebSocketClient;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URI;
import java.net.URISyntaxException;

public class SwingChatClient extends JFrame implements ActionListener, LikeWebSocketClient {
    private final JTextField uriJTextField;
    private final JButton connectJButton;
    private final JButton closeJButton;
    private final JTextArea jTextArea;
    private final JTextField chatJTextField;
    private final JComboBox draftJComboBox;
    private WebSocketClient webSocketClient;


    public SwingChatClient(String defaultLocation) {
        super("WebSocket Chat Client");
        Container container = getContentPane( );
        GridLayout gridLayout = new GridLayout( );
        gridLayout.setColumns( 1);
        gridLayout.setRows( 6);
        container.setLayout( gridLayout);

        Draft[ ] drafts = { new Draft_6455( )};
        draftJComboBox = new JComboBox(drafts);
        container.add(draftJComboBox);

        uriJTextField = new JTextField( );
        uriJTextField.setText( defaultLocation);
        container.add(uriJTextField);

        connectJButton = new JButton("Connect");
        connectJButton.addActionListener(this);
        container.add(connectJButton);

        closeJButton = new JButton("Close");
        closeJButton.addActionListener(this);
        closeJButton.setEnabled( false);
        container.add(closeJButton);

        JScrollPane jScrollPane = new JScrollPane( );
        jTextArea = new JTextArea( );
        jScrollPane.setViewportView(jTextArea);
        container.add( jScrollPane);

        chatJTextField = new JTextField( );
        chatJTextField.setText( "");
        chatJTextField.addActionListener(this);
        container.add(chatJTextField);

        Dimension dimension = new Dimension(300, 400);
        setPreferredSize(dimension);
        setSize(dimension);

        addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing( WindowEvent windowEvent) {
                if ( webSocketClient != null) {
                    webSocketClient.close( );
                }
                dispose( );
            }
        });

        setLocationRelativeTo( null);
        setVisible( true);
    }

    @Override
    public void actionPerformed( ActionEvent actionEvent) {
        if ( actionEvent.getSource( ) == chatJTextField) {
            if ( webSocketClient != null) {
                webSocketClient.send( chatJTextField.getText( ));
                chatJTextField.setText( "");
                chatJTextField.requestFocus( );
            }

        } else if ( actionEvent.getSource( ) == connectJButton) {
            try {
                webSocketClient =
                        new UnifyWebSocketClient(
                                new URI( uriJTextField.getText( ))
                                , ( Draft) draftJComboBox.getSelectedItem( )
                                , this
                        );

                closeJButton.setEnabled( true);
                connectJButton.setEnabled( false);
                uriJTextField.setEditable( false);
                draftJComboBox.setEditable( false);
                webSocketClient.connect( );
            } catch ( URISyntaxException ex) {
                jTextArea.append( uriJTextField.getText( ) + " is not a valid WebSocket URI\n");
            }
        } else if ( actionEvent.getSource() == closeJButton) {
            webSocketClient.close( );
        }
    }

    @Override
    public void inMessage( String message) {
        jTextArea.append( message);
        jTextArea.setCaretPosition( jTextArea.getDocument( ).getLength( ));
    }

    @Override
    public void inOpen(ServerHandshake serverHandshake, String message) {
        jTextArea.append( message);
        jTextArea.setCaretPosition( jTextArea.getDocument( ).getLength( ));
    }

    @Override
    public void inClose( int code, String reason, boolean remote, String message) {
        jTextArea.append( message);
        jTextArea.setCaretPosition( jTextArea.getDocument( ).getLength( ));
        connectJButton.setEnabled( true);
        uriJTextField.setEditable( true);
        draftJComboBox.setEditable( true);
        closeJButton.setEnabled( false);
    }

    @Override
    public void inError( Exception exception, String message) {
        jTextArea.append( message);
        jTextArea.setCaretPosition( jTextArea.getDocument( ).getLength( ));
        exception.printStackTrace( );
        connectJButton.setEnabled( true);
        uriJTextField.setEditable( true);
        draftJComboBox.setEditable( true);
        closeJButton.setEnabled( false);
    }
}