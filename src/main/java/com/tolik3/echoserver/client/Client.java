package com.tolik3.echoserver.client;

import java.io.*;
import java.net.Socket;

public class Client {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) throws IOException {

        byte[] buffer = new byte[100];

        try (Socket socket = new Socket("localhost", 3000);
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream();) {

            int readBytes = inputStream.read(buffer); //read info from server about successfully connection
            System.out.println(new String(buffer, 0, readBytes));

            while (true) {
                System.out.println("Please type your message:");

                String typedMessage = reader.readLine();
                outputStream.write(typedMessage.getBytes());

                readBytes = inputStream.read(buffer);
                System.out.println("Message received from server:");
                System.out.println(ANSI_GREEN + new String(buffer, 0, readBytes) + ANSI_RESET);
            }
        }
    }
}
