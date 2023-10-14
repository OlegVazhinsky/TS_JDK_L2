package org.example.client;

import org.example.server.ServerGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ClientGUI extends JFrame {

    public static final int WIDTH = 400;
    public static final int HEIGHT = 300;

    public JTextArea textArea;
    public JTextField IPtextField, portTextField, loginTextField, messageTextField;
    public JPasswordField password;
    public JButton loginButton, sendButton;
    public JPanel headerPanel;

    public Client client;

    public ClientGUI(Client client) {

        this.client = client;

        setSize(WIDTH, HEIGHT);
        setResizable(false);

        setTitle("Chat client.");
        setLocationRelativeTo(null);

        createPanel();
        setVisible(true);

    }

    public void createPanel() {
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createTextArea());
        add(createFooterPanel(), BorderLayout.SOUTH);
    }

    public Component createHeaderPanel(){

        headerPanel = new JPanel(new GridLayout(2, 3));
        IPtextField = new JTextField("127.0.0.1");
        portTextField = new JTextField("1234");
        loginTextField = new JTextField("Client");
        password = new JPasswordField("12345");

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.connectToServer();
                if (client.connected) {
                    headerPanel.setVisible(false);
                }
            }
        });

        headerPanel.add(IPtextField);
        headerPanel.add(portTextField);
        headerPanel.add(new JPanel());
        headerPanel.add(loginTextField);
        headerPanel.add(password);
        headerPanel.add(loginButton);

        return headerPanel;
    }

    private Component createTextArea(){
        textArea = new JTextArea();
        textArea.setEditable(false);
        return new JScrollPane(textArea);
    }

    private Component createFooterPanel() {

        JPanel panel = new JPanel(new BorderLayout());
        messageTextField = new JTextField();

        messageTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n'){
                    sendMessage();
                }
            }
        });

        sendButton = new JButton("Send");

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        panel.add(messageTextField);
        panel.add(sendButton, BorderLayout.EAST);
        return panel;
    }

    public void sendMessage(){
        client.sendToServer(messageTextField.getText());
        messageTextField.setText(null);
    }

    public void showMessage(String message) {
        textArea.append(message + "\n");
    }

    public void disconnectFromServer(String message) {
        headerPanel.setVisible(true);
        textArea.setText(null);
        showMessage(message);
    }

}
