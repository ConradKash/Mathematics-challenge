package org.example.server;
import java.io.File;
import java.io.IOException;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        // define server default port
        int port = 8080;

        try (// start server
             ServerSocket socket = new ServerSocket(port)) {

            System.out.println("Listening for connections");

            while (true) {
                Socket sock = socket.accept();

                System.out.println("New client connection");

                ServerThread serverThread = new ServerThread(sock);
                serverThread.start();

            }
            // accept incoming connection and create a thread

            // start a thread for continous


            // accept client connection
        } catch (Exception e) {
            e.printStackTrace();
        }


        }





    }
