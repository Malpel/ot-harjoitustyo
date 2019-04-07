package ui;

import domain.Tetris;
import domain.TetrisService;
import domain.Tetromino;
import java.awt.Point;
import java.util.List;
import java.util.TimerTask;
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
    int time;
    TetrisService tetris;

    @Override
    public void start(Stage primaryStage) throws Exception {

        tetris = new TetrisService();
        width = tetris.getCanvasWidth();
        height = tetris.getCanvasHeight();
        scale = tetris.getScale();

        Canvas canvas = new Canvas(width, height);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        BorderPane bp = new BorderPane();
        bp.setCenter(canvas);

        gc.setFill(Color.BLACK);

        tetris.newTetromino();

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        tetris.updateTetris();
                        draw(gc);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }.start();

        Scene s = new Scene(bp);

        primaryStage.setScene(s);

        primaryStage.setTitle(
                "Tetris");
        primaryStage.show();
    }

    public void draw(GraphicsContext gc) {

        for (Point p : tetris.getFaller().getTetromino()) {
            gc.fillRect(((p.x + tetris.getFaller().getOrigin().x) * scale), ((p.y + tetris.getFaller().getOrigin().y) * scale), scale, scale);
            
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

//    public class GameLoop extends TimerTask{
//
//        @Override
//        public void run() {
//            if (tetris.getFaller() == null) {
//                
//            } else {
//                tetris.updateTetris();
//            }
//        }
//    
//}
}
