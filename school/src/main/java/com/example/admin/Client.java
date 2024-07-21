package com.example.admin;

import java.io.IOException;

import com.example.admin.clients.ClientInstance;

public class Client {
   String hostname;
   int port;

   public Client(String hostname, int port) {
      this.hostname = hostname;
      this.port = port;
   }

   public ClientInstance startClientInstance() throws IOException {
      ClientInstance clientInstance = new ClientInstance(this.hostname, this.port);
      clientInstance.start();
      return clientInstance;
   }
   public static void main(String[] args) throws IOException {
      Client client = new Client("localhost", 8080);
      client.startClientInstance();
   }
}
