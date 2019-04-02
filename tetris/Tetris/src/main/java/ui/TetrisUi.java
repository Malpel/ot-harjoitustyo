package ui;

import domain.Tetris;
import domain.TetrisService;
import domain.Tetromino;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TetrisUi extends Application {

    int width;
    int height;
    int scale;

    @Override
    public void start(Stage primaryStage) throws Exception {

        TetrisService tetris = new TetrisService();
        width = tetris.getCanvasWidth();
        height = tetris.getCanvasHeight();
        scale = tetris.getScale();

        Canvas canvas = new Canvas(width, height);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        BorderPane bp = new BorderPane();
        bp.setCenter(canvas);

        gc.setFill(Color.BLACK);

        tetris.newTetromino();

        new AnimationTimer() {
            long previous = 0;

            @Override
            public void handle(long now) {
                if (previous != 0) {
                    if (now > previous + 1_000_000_000) {
                        for (int y = 0; y < tetris.getMatrixHeight(); y++) {
                            for (int x = 0; x < tetris.getMatrixWidth(); x++) {
                                if (tetris.getMatrix()[y][x] == 1) {
                                    gc.fillRect((x * scale), (y * scale), scale, scale);
                                }
                                if (tetris.getFaller().getOrigin().getY() == y && tetris.getFaller().getOrigin().getX() == x) {
                                    gc.fillRect((x * scale), (y * scale), scale, scale);
                                }
                            }

                        }

                    }
                } else {
                    previous = now;
                }

                tetris.updateTetris();

            }
        }.start();
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
