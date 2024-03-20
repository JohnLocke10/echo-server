package com.tolik3.echoserver.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) throws IOException {

        try (ServerSocket serverSocket = new ServerSocket(3000);
             Socket socket = serverSocket.accept();
             OutputStream outputStream = socket.getOutputStream();
             InputStream inputStream = socket.getInputStream();) {

            outputStream.write((ANSI_YELLOW + "You are successfully connected to the server!" + ANSI_RESET).getBytes());

            while (true) {
                byte[] buffer = new byte[100];
                int readBytes = inputStream.read(buffer);
                String messageWithPrefix = "ECHO-" + new String(buffer, 0, readBytes);
                outputStream.write(messageWithPrefix.getBytes());
            }
        }
    }
}
