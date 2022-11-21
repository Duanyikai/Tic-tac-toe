package application;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class Client {

    volatile String receivedMessage = null;

    Socket socket = null;

    public void setReceivedMessage(String receivedMessage) {
        this.receivedMessage = receivedMessage;
    }

    public String getReceivedMessage() {
        return receivedMessage;
    }

    public void send(String sendMessage) {
        try {
            System.out.println("切换至发送状态");
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            printWriter.println(sendMessage);
        } catch (SocketException e) {
            System.out.println("服务器异常关闭");
            // 退出
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receive() {
        System.out.println("切换至监听状态");
        receivedMessage = null;
        //监听部分
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (receivedMessage == null) {
                String serverInfo = bufferedReader.readLine();
                if (serverInfo != null) {
                    receivedMessage = serverInfo;
//                    if (!serverInfo.equals("Player1,请等待另一名玩家...")) {
//                        System.out.println("服务器的消息为：" + receivedMessage);
//                        return receivedMessage;
//                    }
                    System.out.println("服务器的消息为：" + receivedMessage);
                    return receivedMessage;
                }
            }

        } catch (SocketException e) {
            System.out.println("服务器异常关闭");
            // 退出
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }





    public void start() {
        try {
            socket = new Socket("localhost", 5000);
            System.out.println("已连接至服务器");
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    while (true) {
//                        receive();
//                    }
//                }
//            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
//        finally {
//            // 关闭连接
//            try {
//                if (null != socket) {
//                    socket.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
    }

}
