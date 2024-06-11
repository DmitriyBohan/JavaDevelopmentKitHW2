import client.ClientGUI;
import client.ClientController;
import server.ServerController;
import server.ServerWindow;

public class Main {
    public static void main(String[] args) {
        ServerWindow serverWindow = new ServerWindow();
        ServerController serverController = new ServerController(serverWindow);
        serverController.setServerView(serverWindow);
        serverWindow.setServerController(serverController);

        ClientGUI clientGUI = new ClientGUI();
        ClientController clientController = new ClientController(clientGUI, serverController, clientGUI);
        clientGUI.setClientController(clientController);
    }
}

