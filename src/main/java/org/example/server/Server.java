package org.example.server;

import org.example.client.Client;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.io.FileReader;
import java.io.FileWriter;

public class Server implements Serverable{
    public ServerGUI serverGUI;

    public ArrayList<Client> clientList = new ArrayList<>();
    public boolean working = false;
    public static final String LOG_PATH = "src/main/java/org/example/server/serverLog.txt";

    public SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    public Date date = new Date();

    File logFile = new File(LOG_PATH);

    public Server() {
        this.serverGUI = new ServerGUI(this);
        try {
            logFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeToLogFile(String text){
        try (FileWriter writer = new FileWriter(LOG_PATH, true)){
            writer.write(text);
            writer.write("\n");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private String readFromLogFile(){
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader reader = new FileReader(LOG_PATH)){
            int c;
            while ((c = reader.read()) != -1){
                stringBuilder.append((char) c);
            }
            //stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length());
            return stringBuilder.toString();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String getDate(){
        return formatter.format(date);
    }

    public boolean connectUser(Client client){
        if (!working){
            return false;
        }
        clientList.add(client);
        client.getMessage(readFromLogFile());
        return true;
    }

    public boolean disconnectAllUsers(){
        if (clientList != null) {
            for (Client client : clientList) {
                client.disconnectFromServer(getDate() + " : disconnected from server.");
            }
            clientList = new ArrayList<>();
        }
        return true;
    }

    public void getMessage(String message) {

        if (working) {
            message = getDate() + " : " + message;
            serverGUI.showMessage(message);
            writeToLogFile(message);
            for (Client client : clientList) {
                client.getMessage(message);
            }
        }
    }

}
