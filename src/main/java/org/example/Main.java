package org.example;


import org.example.client.Client;
import org.example.server.Server;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        Client client1 = new Client(server);
        Client client2 = new Client(server);

    }
}