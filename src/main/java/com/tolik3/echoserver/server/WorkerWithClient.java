package com.tolik3.echoserver.server;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class WorkerWithClient implements Runnable {
    private Socket socket;
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String CLOSE_CONNECTION_PHRASE = "HASTA LUEGO";

    public WorkerWithClient(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
             InputStream inputStream = socket.getInputStream();
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));) {

            printWriter.println(ANSI_YELLOW +
                    "You are successfully connected to the server! " +
                    "To close your connection just type: " + CLOSE_CONNECTION_PHRASE +
                    ANSI_RESET);

            String clientMessage;
            while ((clientMessage = bufferedReader.readLine()) != null) {
                if (endConnectionByClosePhrase(clientMessage)) {
                    break;
                }
                interactWithClient(clientMessage, printWriter);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            closeSocket();
        }
    }

    private static void interactWithClient(String clientMessage, PrintWriter printWriter) {
        System.out.println("Client message from: " + Thread.currentThread().getName() + clientMessage);
        String messageWithPrefix = "ECHO-" + clientMessage;
        System.out.println("Message for client: " + messageWithPrefix);
        printWriter.println(messageWithPrefix);
    }

    private static boolean endConnectionByClosePhrase(String clientMessage) {
        if (Objects.equals(clientMessage, ANSI_GREEN + CLOSE_CONNECTION_PHRASE + ANSI_RESET)) {
            Thread.currentThread().interrupt();
            return true;
        }
        return false;
    }

    private void closeSocket() {
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error closing socket: " + e.getMessage());
        }
    }
}
