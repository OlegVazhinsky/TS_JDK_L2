package org.example.server;

import org.example.client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ServerGUI extends JFrame{

    public static final int WIDTH = 400;
    public static final int HEIGHT = 300;
    public JButton startButton, stopButton;
    public JTextArea textArea;

    public Server server;

    public SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    public Date date = new Date();

    public ServerGUI(Server server) {

        this.server = server;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat server.");
        setLocationRelativeTo(null);

        createPanel();

        setVisible(true);
    }

    public String getDate(){
        return formatter.format(date);
    }

    public void createPanel() {
        textArea = new JTextArea();
        add(textArea);
        add(createButtons(), BorderLayout.SOUTH);
    }

    public void showMessage(String message) {
        textArea.append(message + "\n");
    }

    private Component createButtons() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (server.working){
                    showMessage(getDate() + ": server is already running.");
                } else {
                    server.working = true;
                    showMessage(getDate() + ": server started.");
                }
            }
        }
        );

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (server.working){
                    server.working = false;
                    server.disconnectAllUsers();
                    showMessage(getDate() + ": server stopped.");
                } else {
                    showMessage(getDate() + ": server is already stoped.");
                }
            }
        });

        panel.add(startButton);
        panel.add(stopButton);
        return panel;
    }

}
