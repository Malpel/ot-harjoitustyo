package ui;

import domain.TetrisService;
import java.awt.Point;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TetrisUi extends Application {

    int width;
    int height;
    int scale;
    int time;
    TetrisService tetris;

    @Override
    public void init() {
        tetris = new TetrisService(Color.WHITE);
        width = tetris.getCanvasWidth();
        height = tetris.getCanvasHeight();
        scale = tetris.getScale();
        tetris.newTetromino();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Canvas canvas = new Canvas(width, height);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        BorderPane bp = new BorderPane();
        bp.setCenter(canvas);

        new AnimationTimer() {

            long previous = 0;

            @Override

            public void handle(long now) {

                if (now - previous < 100000000) {

                    return;

                }

                gc.setFill(Color.WHITE);
                gc.fillRect(0, 0, width, height);
                drawMatrix(gc);
                drawFaller(gc);

                this.previous = now;

            }

        }.start();

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        tetris.updateTetris();
                    } catch (InterruptedException e) {
                    }
                }
            }
        }.start();

        Scene s = new Scene(bp);
        s.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.DOWN) {
                tetris.updateTetris();
            }
        });

        s.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.LEFT) {
                tetris.moveTetromino(-1);
            }
        });

        s.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.RIGHT) {
                tetris.moveTetromino(1);
            }
        });

        s.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.SPACE) {
                tetris.rotation();
            }
        });

        primaryStage.setScene(s);

        primaryStage.setTitle(
                "Tetris");
        primaryStage.show();
    }

    public void drawFaller(GraphicsContext gc) {
        gc.setFill(tetris.getFaller().getColor());
        for (Point p : tetris.getFaller().getTetromino()) {
            gc.fillRect(((p.x + tetris.getFaller().getOrigin().x) * scale), ((p.y + tetris.getFaller().getOrigin().y) * scale), scale, scale);

        }
    }

    public void drawMatrix(GraphicsContext gc) {
        for (int y = 0; y < tetris.getMatrixHeight(); y++) {
            for (int x = 0; x < tetris.getMatrixWidth(); x++) {
                gc.setFill(tetris.getMatrix()[y][x]);
                gc.fillRect(x * scale, y * scale, scale, scale);
            }
        }
    }

    @Override
    public void stop() {
        System.out.println("Sulkeutuu");
    }

    public static void main(String[] args) {
        launch(TetrisUi.class
        );
    }
}
