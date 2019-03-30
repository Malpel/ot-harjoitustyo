package ui;

import domain.Tetris;
import domain.Tetromino;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TetrisUi extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        int width = 350; //35
        int height = 770; //35

        Tetris testo = new Tetris(10, 22);

        Canvas canvas = new Canvas(width, height);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        BorderPane bp = new BorderPane();
        bp.setCenter(canvas);

        gc.setFill(Color.BLACK);

        Tetromino t = testo.getTetrominos()[5];
        testo.newBrick(t);
        int[][] m = testo.getMatrix();

        for (int y = 0; y < 22; y++) {
            for (int x = 0; x < 10; x++) {
                if (m[y][x] == 1) {
                   gc.fillRect((x*35), (y*35), 35, 35);
                }
                System.out.print(m[y][x] + " ");

            }
            System.out.println("");
        }

        Scene s = new Scene(bp);

        primaryStage.setScene(s);
        primaryStage.setTitle("Tetris");
        primaryStage.show();
    }

    @Override
    public void stop() {
        System.out.println("Sulkeutuu");
    }

    public static void main(String[] args) {
        launch(TetrisUi.class);
    }

}
