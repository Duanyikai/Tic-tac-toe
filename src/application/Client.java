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
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receive() {
        System.out.println("切换至监听状态");
        receivedMessage = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (receivedMessage == null) {
                String serverInfo = bufferedReader.readLine();
                if (serverInfo != null) {
                    receivedMessage = serverInfo;
                    System.out.println("服务器的消息为：" + receivedMessage);
                    return receivedMessage;
                }
            }

        } catch (SocketException e) {
            System.out.println("服务器异常关闭");
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
