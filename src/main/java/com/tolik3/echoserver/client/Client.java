package com.tolik3.echoserver.client;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class Client {
    private static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) throws IOException {

        try (Socket socket = new Socket("localhost", 3000);
             BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
             InputStream serverInputStream = socket.getInputStream();
             BufferedReader serverReader = new BufferedReader(new InputStreamReader(serverInputStream));
             PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);) {

            String serverConnectionMessage = serverReader.readLine(); //read info from server about successfully connection
            System.out.println(serverConnectionMessage);

            while (true) {
                System.out.println("Please type your message:");
                String typedMessage = consoleReader.readLine();
                printWriter.println(ANSI_GREEN + typedMessage + ANSI_RESET);
                String serverMessage = serverReader.readLine();
                if (Objects.isNull(serverMessage)) {
                    System.out.println(ANSI_RED + "The connection with Server was closed!" + ANSI_RESET);
                    break;
                }
                System.out.println("Message received from server:");
                System.out.println(ANSI_GREEN + serverMessage + ANSI_RESET);
            }
        }
    }
}
