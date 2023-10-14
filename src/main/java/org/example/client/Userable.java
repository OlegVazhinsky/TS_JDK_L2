package org.example.client;

public interface Userable {
    public void connectToServer();
    public void disconnectFromServer(String message);
    public void sendToServer(String message);
}
