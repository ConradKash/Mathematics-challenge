package com.example.admin;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import com.example.admin.server.*;

public class Server {
    public Server() {
    }

    public static void main(String[] args) throws IOException {
        int port = 8080;

        try {
            ServerSocket socket = new ServerSocket(port);

            try {
                System.out.println("Listening for connections");

                while (true) {
                    Socket sock = socket.accept();
                    System.out.println("New client connection");
                    ServerThread serverThread = new ServerThread(sock);
                    serverThread.start();
                }
            } catch (Throwable var6) {
                try {
                    socket.close();
                } catch (Throwable var5) {
                    var6.addSuppressed(var5);
                }

                throw var6;
            }
        } catch (Exception var7) {
            var7.printStackTrace();
        }
    }
}
