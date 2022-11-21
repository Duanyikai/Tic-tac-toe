package application;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server {
    static List<Socket> socketList = new ArrayList<>();
    static List<GameData> GameList = new ArrayList<>();

    static List<ClientHandler> clientHandlerList = new ArrayList<>();

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(5000);
            System.out.println("等待客户端的连接");
            int a = 0;

            while (true) {
                Socket socket = serverSocket.accept();
                socketList.add(socket);
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
                clientHandler.setGameID(a);
                clientHandlerList.add(clientHandler);

                if (socketList.size() % 2 != 0) {
                    clientHandler.setSendMessage("Player1,请等待另一名玩家...");
                } else {
                    clientHandler.setSendMessage("Player2,已连接。请等待对方落子...");
                    GameData gd = new GameData(a, clientHandlerList.get(0), clientHandlerList.get(1));
                    GameList.add(gd);
                    clientHandlerList.get(0).setGameData(gd);
                    clientHandlerList.get(1).setGameData(gd);

                    socketList.clear();
                    clientHandlerList.clear();
                    a++;
                }
            }
        } catch (SocketException e) {
            System.out.println(2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (null != serverSocket) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class ClientHandler implements Runnable{
        volatile String receivedMessage = null;
        volatile String sendMessage = null;

        int gameID;

        volatile int turn = 0;

        Socket socket;

        GameData gameData;

        //构造方法
        public ClientHandler(Socket socket) {
            this.socket = socket;

        }

        public ClientHandler(int gameID, Socket socket) {
            this.gameID = gameID;
            this.socket = socket;
        }

        public int getGameID() {
            return gameID;
        }

        public void setGameID(int gameID) {
            this.gameID = gameID;
        }

        public String getReceivedMessage() {
            return receivedMessage;
        }

        public void setReceivedMessage(String receivedMessage) {
            this.receivedMessage = receivedMessage;
        }

        public String getSendMessage() {
            return sendMessage;
        }

        public GameData getGameData() {
            return gameData;
        }

        public void setGameData(GameData gameData) {
            this.gameData = gameData;
        }

        public void setSendMessage(String sendMessage) {
            this.sendMessage = sendMessage;
        }

        public String receive() {
            System.out.println("切换至监听状态");
            //监听部分
            try {
                // 读取客户端发来的数据
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println("等待读取客户端：" + socket.getLocalAddress().toString().substring(1) + ":" + socket.getPort() + " 的消息");
                while (receivedMessage == null) {
                    String clientInfo = bufferedReader.readLine();
                    if (clientInfo != null) {
                        if (!clientInfo.equals("hello")) {
                            GameList.get(gameID).setMessage(clientInfo);
                            if (turn == 0) {
                                System.out.println(0);
                                gameData.oneToTwo();
                                turn = 1;
                            } else {
                                System.out.println(1);
                                gameData.twoToOne();
                                turn = 0;
                            }
                        }
                        receivedMessage = clientInfo;
                        System.out.println(socket.getLocalAddress().toString().substring(1) + ":" + socket.getPort() + "发送的消息是：" + receivedMessage);
                        break;
                    }
                }
                receivedMessage = null;
            } catch (SocketException e) {
                // 强制关闭连接后的处理
                System.out.println(socket.getLocalAddress().toString().substring(1) + ":" + socket.getPort() + "断开连接");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public void send() {
            try {
                System.out.println("切换至发送状态");
                PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
                while(true) {
                    if (sendMessage != null){
                        printWriter.println(sendMessage);
                        break;
                    }
                }
                if (!sendMessage.equals("Player1,请等待另一名玩家...") && !sendMessage.equals("Player2,已连接。请等待对方落子...")) {
                    if (turn == 0) {
                        turn = 1;
                    } else {
                        turn = 0;
                    }
                }
                System.out.println(sendMessage);
                sendMessage = null;

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void run() {
            int state = 0;
            while(true) {
                if (state == 0) {
                    receive();
                    state = 1;
                } else {
                    send();
                    state = 0;
                }
            }
        }
    }
}
