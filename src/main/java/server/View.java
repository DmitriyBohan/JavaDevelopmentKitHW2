package server;


public interface View {

    void showMessage(String text);

    void disconnect();

    void connect();

    void setServerController(ServerController controller);
}

