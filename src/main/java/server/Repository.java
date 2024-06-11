package server;

public interface Repository {
    void save(String text);
    String read();
}
