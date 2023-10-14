package org.example.client;

import org.example.server.*;

public class Client implements Userable{

    private Server server;
    private ClientGUI clientGUI;
    public String login;
    public boolean connected = false;

    public Client(Server server) {
        this.server = server;
        this.clientGUI = new ClientGUI(this);
    }

    public void connectToServer() {
        if (server.connectUser(this)){
            clientGUI.showMessage(server.getDate() + " : connected to server.");
            this.login = clientGUI.loginTextField.getText();
            connected = true;
        } else {
            clientGUI.showMessage("Connection to server failed.");
        }
    }

    public void disconnectFromServer(String message) {
        connected = false;
        clientGUI.disconnectFromServer(message);
    }

    public void sendToServer(String message){
        if (connected) {
            if (!message.isEmpty()) {
                server.getMessage(login + " : " + message);
            }
        } else {
            clientGUI.showMessage("No connection esteblished.");
        }
    }

    public void getMessage(String message) {
        clientGUI.showMessage(message);
    }


}
