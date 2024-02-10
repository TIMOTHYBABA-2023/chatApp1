package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost",5555);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(),true)){

            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.println("Send a  message to the Server or 'exit' to quit:");
                String userInput = consoleReader.readLine();


                if ("exit".equalsIgnoreCase(userInput)){
                    writer.println(socket.getInetAddress() +" disconnected");
                    break;
                }

                writer.println(userInput);



                String serverResponse = reader.readLine();
                System.out.println("Server says: " + serverResponse);
        }

    } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();        }
    }
}
