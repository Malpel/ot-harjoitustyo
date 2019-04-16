package ui;

import domain.TetrisService;
import java.awt.Point;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
        tetris = new TetrisService(Color.BEIGE);
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
        VBox reuna = new VBox();
        Label scoreLabel = new Label();
        IntegerProperty score = new SimpleIntegerProperty(0);
        scoreLabel.textProperty().bind(Bindings.createStringBinding(() -> ("Score: " + tetris.getScore()), score));
        reuna.getChildren().addAll(new Label("Tetris"), scoreLabel, new Label("Time: 22.22"));
        reuna.setAlignment(Pos.CENTER);
        reuna.setPadding(new Insets(15, 12, 15, 12));
        reuna.setSpacing(10);
        reuna.setStyle("-fx-background-color: #336699");
        bp.setLeft(reuna);

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
                scoreLabel.textProperty().bind(Bindings.createStringBinding(() -> ("Score: " + tetris.getScore()), score));
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
