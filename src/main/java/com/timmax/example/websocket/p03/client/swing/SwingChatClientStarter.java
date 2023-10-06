package com.timmax.example.websocket.p03.client.swing;

public class SwingChatClientStarter {
    public static void main( String[ ] args) {
        String location;
        if ( args.length != 0) {
            location = args[ 0];
            System.out.println( "Default server url specified: '" + location + "'");
        } else {
            location = "ws://localhost:8887";
            System.out.println( "Default server url not specified: defaulting to '" + location + "'");
        }
        new SwingChatClient( location);
    }
}