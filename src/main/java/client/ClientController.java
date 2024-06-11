package client;

import server.ServerController;

public class ClientController {
    private ClientView ui;
    private ServerController serverController;
    private ClientGUI clientGUI;

    public ClientController(ClientView ui, ServerController serverController, ClientGUI clientGUI) {
        this.ui = ui;
        this.serverController = serverController;
        this.clientGUI = clientGUI;
    }

    public void setClientGUI(ClientGUI clientGUI) {
        this.clientGUI = clientGUI;
    }

    public boolean connectToServer(String name) {
        if (serverController.connectToServer(name, clientGUI)) {
            ui.showMessage("You successfully connected to the server!");
            String log = serverController.getLog();
            if (log != null) {
                ui.showMessage(log);
            }
            return true;
        } else {
            ui.showMessage("Connection failed");
            return false;
        }
    }

    public void disconnectFromServer() {
        ui.showMessage("Server disconnected");
        clientGUI.hideMessagePanel();
    }

    public void sendMessage(String message) {
        serverController.message(clientGUI.getName(), message);
    }
    public String getServerLog() {
        return serverController.getLog();
    }
}