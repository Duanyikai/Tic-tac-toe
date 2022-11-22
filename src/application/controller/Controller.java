package application.controller;

import application.Client;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private static final int PLAY_1 = 1;
    private static final int PLAY_2 = 2;
    private static final int EMPTY = 0;
    private static final int BOUND = 90;
    private static final int OFFSET = 15;

    @FXML
    private Pane base_square;

    @FXML
    private Rectangle game_panel;

    public boolean TURN = false;

    public void setTURN(boolean TURN) {
        this.TURN = TURN;
    }

    private static final int[][] chessBoard = new int[3][3];
    private static final boolean[][] flag = new boolean[3][3];

    public Client client;
    public int isNewThread = 0;

    int x = -1, y = -1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client = new Client();
        client.start();


        client.send("hello");
        if (client.receive().equals("Player1,请等待另一名玩家...")) {
            TURN=true;
        } else {
            client.send("hello");
        }
        if (isNewThread == 0) {
            new Thread(() -> {
                while (true){
                    String receive = client.receive();
                    if (receive.equals("你赢了！") || receive.equals("你输了...") || receive.equals("平局...") || receive.equals("另一个玩家退出")) {
                        break;
                    }
                    int rx = (int) receive.charAt(0) - (int) '0';
                    int ry = (int) receive.charAt(2) - (int) '0';
                    refreshBoard(rx, ry);
                    TURN = !TURN;
                }
            }).start();
            isNewThread++;
        }
        game_panel.setOnMouseClicked(event -> {
            x = (int) (event.getX() / BOUND);
            y = (int) (event.getY() / BOUND);
            if (TURN) {
                refreshBoard(x, y);
                TURN = !TURN;
                System.out.println("玩家下在了[" + x + "," + y + "]");
                client.send(x + "," + y);
            }

        });


    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean refreshBoard(int x, int y) {
        if (chessBoard[x][y] == EMPTY) {
            chessBoard[x][y] = TURN ? PLAY_1 : PLAY_2;
            drawChess();
            return true;
        }
        return false;
    }

    private void drawChess() {

        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard[0].length; j++) {
                if (flag[i][j]) {
                    // This square has been drawing, ignore.
                    continue;
                }
                switch (chessBoard[i][j]) {
                    case PLAY_1:
                        drawCircle(i, j);
                        break;
                    case PLAY_2:
                        drawLine(i, j);
                        break;
                    case EMPTY:
                        // do nothing
                        break;
                    default:
                        System.err.println("Invalid value!");
                }
            }
        }
    }

    private void drawCircle(int i, int j) {
        Platform.runLater(() -> {
            Circle circle = new Circle();
            base_square.getChildren().add(circle);
            circle.setCenterX(i * BOUND + BOUND / 2.0 + OFFSET);
            circle.setCenterY(j * BOUND + BOUND / 2.0 + OFFSET);
            circle.setRadius(BOUND / 2.0 - OFFSET / 2.0);
            circle.setStroke(Color.RED);
            circle.setFill(Color.TRANSPARENT);
            flag[i][j] = true;
        });

    }

    private void drawLine(int i, int j) {
        Platform.runLater(()->{
            Line line_a = new Line();
            Line line_b = new Line();
            base_square.getChildren().add(line_a);
            base_square.getChildren().add(line_b);
            line_a.setStartX(i * BOUND + OFFSET * 1.5);
            line_a.setStartY(j * BOUND + OFFSET * 1.5);
            line_a.setEndX((i + 1) * BOUND + OFFSET * 0.5);
            line_a.setEndY((j + 1) * BOUND + OFFSET * 0.5);
            line_a.setStroke(Color.BLUE);

            line_b.setStartX((i + 1) * BOUND + OFFSET * 0.5);
            line_b.setStartY(j * BOUND + OFFSET * 1.5);
            line_b.setEndX(i * BOUND + OFFSET * 1.5);
            line_b.setEndY((j + 1) * BOUND + OFFSET * 0.5);
            line_b.setStroke(Color.BLUE);
            flag[i][j] = true;
        });
    }
}
