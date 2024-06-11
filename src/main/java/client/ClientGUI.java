package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientGUI extends JFrame implements ClientView {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private JPanel connectionPanel;
    private JPanel chatPanel;

    private JTextField tfIPAddress, tfPort, tfLogin;
    private JPasswordField password;
    private JButton btnConnect;

    private JTextField tfMessage;
    private JButton btnSend;
    private JTextArea log;

    private ClientController clientController;
    private String name;

    public ClientGUI() {
        setupUI();
        setVisible(true);
    }

    private void setupUI() {
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat client");
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        createConnectionPanel();
        createChatPanel();
    }

    private void createConnectionPanel() {
        connectionPanel = new JPanel(new GridLayout(2, 3));

        tfIPAddress = new JTextField("127.0.0.1");
        tfPort = new JTextField("8189");
        tfLogin = new JTextField("Ivan Ivanovich");
        password = new JPasswordField("123456");

        btnConnect = new JButton("Connect");
        btnConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connect();
            }
        });

        connectionPanel.add(tfIPAddress);
        connectionPanel.add(tfPort);
        connectionPanel.add(new JPanel());
        connectionPanel.add(tfLogin);
        connectionPanel.add(password);
        connectionPanel.add(btnConnect);

        add(connectionPanel, BorderLayout.NORTH);
    }

    private void createChatPanel() {
        chatPanel = new JPanel(new BorderLayout());

        tfMessage = new JTextField();
        btnSend = new JButton("Send");
        log = new JTextArea();
        log.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(log);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(tfMessage, BorderLayout.CENTER);
        bottomPanel.add(btnSend, BorderLayout.EAST);

        chatPanel.add(scrollPane, BorderLayout.CENTER);
        chatPanel.add(bottomPanel, BorderLayout.SOUTH);

        chatPanel.setVisible(false);
        add(chatPanel, BorderLayout.CENTER);

        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = tfMessage.getText();
                if (!message.isEmpty()) {
                    clientController.sendMessage(message);
                    tfMessage.setText("");
                }
            }
        });
    }

    private void connect() {
        name = tfLogin.getText();

        if (clientController.connectToServer(name)) {
            showChatPanel();
            showMessage("Connected to server successfully!");
            String log = clientController.getServerLog();
            if (log != null) {
                showMessage(log);
            }
        } else {
            showMessage("Failed to connect to server.");
        }
    }

    private void showConnectionPanel() {
        connectionPanel.setVisible(true);
        chatPanel.setVisible(false);
    }

    private void showChatPanel() {
        connectionPanel.setVisible(false);
        chatPanel.setVisible(true);
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void showMessage(String message) {
        log.append(message + "\n");
    }

    @Override
    public void disconnectedFromServer() {
        showMessage("Server disconnected");


        chatPanel.setVisible(false);


        connectionPanel.setVisible(true);
    }

    public String getName() {
        return name;
    }
    public void hideMessagePanel() {

        tfMessage.setVisible(false);
        btnSend.setVisible(false);

    }


}
