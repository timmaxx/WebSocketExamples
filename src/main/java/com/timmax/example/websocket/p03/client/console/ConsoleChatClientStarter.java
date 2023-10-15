package com.timmax.example.websocket.p03.client.console;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ConsoleChatClientStarter {
    public static void main( String[ ] args) throws URISyntaxException, IOException {
        // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts
        new ConsoleChatClient( new URI( "ws://localhost:8887"));
    }
}