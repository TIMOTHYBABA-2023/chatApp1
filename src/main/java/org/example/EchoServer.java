package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    private static volatile boolean serverRunning = true;
    public static void main(String[] args) {
        System.out.println("Hello world!");

        try (ServerSocket serverSocket = new ServerSocket(5555)){
            System.out.println("Server listening on port 5555...");

            while (serverRunning){
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void handleClient(Socket clientSocket){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(),true)){


            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                System.out.println("Message from Client: " + inputLine);

                System.out.println("Reply the Client or enter 'exit' to close server");
                String serverResponse = generateServerResponse();
                if ("exit".equalsIgnoreCase(serverResponse)){
                    serverResponse = "Server terminated...";
                    stopServer();
                    break;
                }else
                 writer.println(serverResponse);
            }
            System.out.println("Server terminated...");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    private static String generateServerResponse() throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new BufferedReader(new InputStreamReader(System.in)));
        String consoleReader = bufferedReader.readLine();
        return consoleReader;
    }
    private static void stopServer(){
        serverRunning = false;
    }
}