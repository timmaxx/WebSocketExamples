package com.timmax.example.socket.p01.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Написание сервера Socket на Java
// https://runebook.dev/ru/docs/dom/websockets_api/writing_a_websocket_server_in_java

public class SocketServer2 {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        try (ServerSocket server = new ServerSocket(80)) {
            System.out.println("Server has started on 127.0.0.1:80.\r\nWaiting for a connection…");
            Socket client = server.accept();
            System.out.println("A client connected.");
            InputStream in = client.getInputStream();
            OutputStream out = client.getOutputStream();
            try (Scanner s = new Scanner(in, StandardCharsets.UTF_8)) {
                String data = s.useDelimiter("\\r\\n\\r\\n").next();
                Matcher get = Pattern.compile("^GET").matcher(data);
                if (get.find()) {
                    Matcher match = Pattern.compile("Sec-WebSocket-Key: (.*)").matcher(data);
                    match.find();
                    byte[] response = ("HTTP/1.1 101 Switching Protocols\r\n"
                            + "Connection: Upgrade\r\n"
                            + "Upgrade: websocket\r\n"
                            + "Sec-WebSocket-Accept: "
                            + Base64
                                .getEncoder()
                                .encodeToString(
                                        MessageDigest
                                                .getInstance("SHA-1")
                                                .digest( ( match.group(1) + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11")
                                                        .getBytes(StandardCharsets.UTF_8)
                                                )
                                )
                            + "\r\n\r\n").getBytes(StandardCharsets.UTF_8);
                    out.write(response, 0, response.length);
                    byte[] decoded = new byte[6];
                    byte[] encoded = new byte[]{(byte) 198, (byte) 131, (byte) 130, (byte) 182, (byte) 194, (byte) 135};
                    byte[] key = new byte[]{(byte) 167, (byte) 225, (byte) 225, (byte) 210};
                    for (int i = 0; i < encoded.length; i++) {
                        decoded[i] = (byte) (encoded[i] ^ key[i & 0x3]);
                    }
                }
            }
        }
    }
}