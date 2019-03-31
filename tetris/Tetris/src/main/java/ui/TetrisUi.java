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

    //tetris service
    final int matrixWidth = 10;
    final int matrixHeight = 22;
    int width = 350; //35 * matrixWidth
    int height = 770; //35 * matrixHeight
    int scale = height / matrixHeight;

    @Override
    public void start(Stage primaryStage) throws Exception {

        //tetris service
        Tetris testo = new Tetris(matrixWidth, matrixHeight);

        Canvas canvas = new Canvas(width, height);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        BorderPane bp = new BorderPane();
        bp.setCenter(canvas);

        gc.setFill(Color.BLACK);

        Tetromino t = testo.getTetrominos()[1];
        testo.newTetromino(t);
        int[][] m = testo.getMatrix();

        for (int y = 0; y < matrixHeight; y++) {
            for (int x = 0; x < matrixWidth; x++) {
                if (m[y][x] == 1) {
                    gc.fillRect((x * scale), (y * scale), scale, scale);
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
