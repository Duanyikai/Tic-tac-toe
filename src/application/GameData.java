package application;

import java.net.Socket;

public class GameData {

    int id;
    String message = null;

    Server.ClientHandler clientHandler1 = null;
    Server.ClientHandler clientHandler2 = null;

    private int[][] Board = new int[3][3];

    public GameData() {

    }

    public GameData(int id, Server.ClientHandler clientHandler1,
                    Server.ClientHandler clientHandler2) {
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
        if (!message.equals("另一个玩家退出")) {
            String[] s = message.split(",");
            Board[Integer.parseInt(s[0])][Integer.parseInt(s[1])] = 1;

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String state = checkState();
            if (state.equals("1")) {
                clientHandler1.hasWinner = true;
                clientHandler2.hasWinner = true;
                clientHandler1.sendMessage = "你赢了！";
                clientHandler2.sendMessage = "你输了...";
                clientHandler2.send();

            } else if (state.equals("2")) {
                clientHandler1.hasWinner = true;
                clientHandler2.hasWinner = true;
                clientHandler1.sendMessage = "你输了...";
                clientHandler2.sendMessage = "你赢了！";
                clientHandler2.send();

            } else if (state.equals("平局")) {
                clientHandler1.hasWinner = true;
                clientHandler2.hasWinner = true;
                clientHandler1.sendMessage = "平局...";
                clientHandler2.sendMessage = "平局...";
                clientHandler2.send();

            }
        }

    }

    public void twoToOne() {
        clientHandler1.setSendMessage(message);
        if (!message.equals("另一个玩家退出")) {
            String[] s = message.split(",");
            Board[Integer.parseInt(s[0])][Integer.parseInt(s[1])] = 2;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String state = checkState();
            if (state.equals("1")) {
                clientHandler1.hasWinner = true;
                clientHandler2.hasWinner = true;
                clientHandler1.sendMessage = "你赢了！";
                clientHandler2.sendMessage = "你输了...";
                clientHandler1.send();

            } else if (state.equals("2")) {
                clientHandler1.hasWinner = true;
                clientHandler2.hasWinner = true;
                clientHandler1.sendMessage = "你输了...";
                clientHandler2.sendMessage = "你赢了！";
                clientHandler1.send();

            } else if (state.equals("平局")) {
                clientHandler1.hasWinner = true;
                clientHandler2.hasWinner = true;
                clientHandler1.sendMessage = "平局...";
                clientHandler2.sendMessage = "平局...";
                clientHandler1.send();

            }
        }

    }

    public String checkState() {
        for (int i = 0; i < 3; i++) {
            if (Board[i][0] == Board[i][1] && Board[i][0] == Board[i][2] && Board[i][2] != 0) {
                if (Board[i][0] == 1){
                    return "1";
                } else {
                    return "2";
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            if (Board[0][i] == Board[1][i] && Board[0][i] == Board[2][i] && Board[0][i] != 0) {
                if (Board[0][i] == 1){
                    return "1";
                } else {
                    return "2";
                }
            }
        }
        if (Board[0][0] == Board[1][1] && Board[0][0] == Board[2][2] && Board[2][2] != 0) {
            if (Board[0][0] == 1){
                return "1";
            } else {
                return "2";
            }
        }
        if (Board[2][0] == Board[1][1] && Board[2][0] == Board[0][2] && Board[0][2] != 0) {
            if (Board[2][0] == 1){
                return "1";
            } else {
                return "2";
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (Board[i][j] == 0) {
                    return "going";
                }
            }
        }
        return "平局";
    }

}
