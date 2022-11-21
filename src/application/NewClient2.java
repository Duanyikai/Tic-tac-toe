//package application;
//
//import java.io.*;
//import java.net.Socket;
//import java.net.SocketException;
//import java.util.Map;
//
//public class NewClient2 {
//
//    static volatile String receivedMessage = null;
//    static volatile String sendMessage = "hello";
//
//    public void setReceivedMessage(String message) {
//        receivedMessage = message;
//    }
//
//    public void setSendMessage(String message) {
//        sendMessage = message;
//    }
//
//
//    public static void main(String[] args) {
//        Socket socket = null;
//        try {
//            socket = new Socket("localhost", 5000);
//            System.out.println("已连接至服务器");
//
//            new Thread(new GUI()).start();
//
//            int state = 1;
//            while(true) {
//                if (state == 0) {
//                    System.out.println("切换至监听" + "状态");
//                    //监听部分
//                    try {
//                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                        while (true) {
//                            String serverInfo = bufferedReader.readLine();
//                            if (serverInfo != null) {
//                                receivedMessage = serverInfo;
//                                //测试
//                                System.out.println("服务器的消息为：" + receivedMessage);
//                                break;
//                            }
//                        }
//                        state = 1;
//                        receivedMessage = null;
//                    } catch (SocketException e) {
//                        System.out.println("服务器异常关闭");
//                        // 退出
//                        System.exit(0);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    System.out.println("切换至发送状态");
//                    //发送部分
//                    try {
//                        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
//                        while(true) {
//                            if (sendMessage != null){
//                                printWriter.println(sendMessage);
//                                System.out.println(sendMessage);
//                                break;
//                            }
//                        }
//                        state = 0;
//                        sendMessage = null;
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
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
//
//    }
//
//    static class GUI implements Runnable {
//
//        @Override
//        public void run() {
//            Main main = new Main();
//            main.run();
//        }
//    }
//
//}
