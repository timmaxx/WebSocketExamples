package com.timmax.example.websocket.p03.client.console;

import java.net.URI;
import java.net.URISyntaxException;

public class ConsoleChatClientStarter {
    public static void main(String[] args) throws URISyntaxException {
        ConsoleWebSocketClient consoleWebSocketClient =
                new ConsoleWebSocketClient(
                        new URI("ws://localhost:8887")
        ); // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts
        consoleWebSocketClient.connect();
    }
}