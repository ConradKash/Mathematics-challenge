package org.example.client;

import java.io.IOException;

public class Client {
    String hostname;
    int port;

    public Client(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public ClientInstance startClientInstance() throws IOException {
        User user = new User();
        ClientInstance clientInstance = new ClientInstance(this.hostname, this.port, user);
        clientInstance.start();
        return clientInstance;
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", 8080);
        client.startClientInstance();
    }
}
