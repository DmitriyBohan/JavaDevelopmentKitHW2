package server;

import client.ClientGUI;


import java.util.ArrayList;
import java.util.List;

public class ServerController {
    private Repository repository;
    private View ui;
    private boolean work;
    private List<ClientGUI> clientGUIList;

    public ServerController(View ui) {
        this.repository = new FileStorage();
        this.clientGUIList = new ArrayList<>();
        this.ui = ui;
    }

    public void setServerView(View ui) {
        this.ui = ui;
    }

    public void startServer() {
        if (work) {
            ui.showMessage("The server is already running");
        } else {
            work = true;
            ui.showMessage("The server is running!");
        }
    }

    public void stopServer() {
        if (!work) {
            ui.showMessage("The server has already been stopped");
        } else {
            work = false;
            while (!clientGUIList.isEmpty()) {
                disconnectUser(clientGUIList.get(clientGUIList.size() - 1));
            }
            ui.showMessage("The server has been stopped!");
        }
    }

    public boolean connectToServer(String name, ClientGUI clientGUI) {
        if (!work) {
            return false;
        }
        clientGUIList.add(clientGUI);
        ui.showMessage(name + " connected to the server");
        return true;
    }

    public String getLog() {
        return repository.read();
    }

    public void disconnectUser(ClientGUI clientGUI) {
        clientGUIList.remove(clientGUI);
        if (clientGUI != null) {
            clientGUI.disconnectedFromServer();
        }
    }

    public void message(String senderName, String message) {
        if (!work) {
            return;
        }
        ui.showMessage(senderName + ": " + message);
        answerAll(senderName, message);
        repository.save(senderName + ": " + message);
    }

    private void answerAll(String senderName, String message) {
        for (ClientGUI clientGUI : clientGUIList) {
            clientGUI.showMessage(senderName + ": " + message);
        }
    }
}