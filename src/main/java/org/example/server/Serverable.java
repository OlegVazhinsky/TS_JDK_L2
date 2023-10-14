package org.example.server;

import org.example.client.Client;

public interface Serverable {
    public boolean connectUser(Client client);
    public boolean disconnectAllUsers();



}
