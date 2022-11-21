package application;

import java.net.Socket;

public class GameData {

    int id;
    String message = null;

    Server.ClientHandler clientHandler1 = null;
    Server.ClientHandler clientHandler2 = null;

    public GameData() {

    }

    public GameData(int id, Server.ClientHandler clientHandler1, Server.ClientHandler clientHandler2) {
        this.id = id;
        this.clientHandler1 = clientHandler1;
        this.clientHandler2 = clientHandler2;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void oneToTwo() {
        clientHandler2.setSendMessage(message);
        System.out.println(4);
    }

    public void twoToOne() {
        clientHandler1.setSendMessage(message);
    }
}
