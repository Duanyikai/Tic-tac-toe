package application.controller;

import application.Client;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller1 implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void open() {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getClassLoader().getResource("mainUI.fxml"));
            Pane root = fxmlLoader.load();

//            Client client1 = new Client();
//            client1.start();
//            Controller controller = fxmlLoader.getController();
//            controller.setClient(client1);
//
//            client1.send("hello");
//            if (client1.receive().equals("Player1,请等待另一名玩家...")) {
//                controller.setTURN(true);
//            } else {
//                client1.send("hello");
//            }

//            Platform.runLater(new Runnable() {
//                @Override
//                public void run() {
//                    while (true) {
//                        String receive = client1.receive();
//                        int rx = (int)receive.charAt(0) - (int)'0';
//                        int ry = (int)receive.charAt(2) - (int)'0';
//                        controller.refreshBoard(rx,ry);
//                        controller.TURN = !controller.TURN;
//                    }
//                }
//            });

            Stage primaryStage = new Stage();
            primaryStage.setTitle("Tic Tac Toe");
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
