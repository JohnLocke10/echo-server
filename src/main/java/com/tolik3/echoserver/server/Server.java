package com.tolik3.echoserver.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) throws IOException {

        try (ServerSocket serverSocket = new ServerSocket(3000);
             Socket socket = serverSocket.accept();
             PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
             InputStream inputStream = socket.getInputStream();
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {

            printWriter.println(ANSI_YELLOW + "You are successfully connected to the server!" + ANSI_RESET);

            while (true) {
                if (inputStream.available() > 0) {
                    String clientMessage = bufferedReader.readLine();
                    System.out.println("Client message: " + clientMessage);
                    String messageWithPrefix = "ECHO-" + clientMessage;
                    printWriter.println(messageWithPrefix);
                }
            }
        }
    }
}
