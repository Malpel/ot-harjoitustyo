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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class TetrisUi extends Application {

    int width;
    int height;
    int scale;
    int time;
    TetrisService tetris;
    boolean closing;

    @Override
    public void init() {
        tetris = new TetrisService(Color.rgb(43, 42, 42));
        width = tetris.getCanvasWidth();
        height = tetris.getCanvasHeight();
        scale = tetris.getScale();
        tetris.newTetromino();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Canvas canvas = new Canvas(width, height);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        BorderPane gameBoard = new BorderPane();
        gameBoard.setCenter(canvas);
        VBox leftBorder = new VBox();
        Label scoreLabel = new Label();
        Label scoreLabel2 = new Label();
        IntegerProperty score = new SimpleIntegerProperty(0);

        scoreLabel.textProperty().bind(Bindings.createStringBinding(() -> ("Score: " + tetris.getScore()), score));
        scoreLabel2.textProperty().bind(Bindings.createStringBinding(() -> (String.valueOf(tetris.getScore())), score));

        leftBorder.getChildren().addAll(new Label("Tetris"), scoreLabel, new Label("Time: 22.22"));
        leftBorder.setAlignment(Pos.CENTER);
        leftBorder.setPadding(new Insets(15, 12, 15, 12));
        leftBorder.setSpacing(10);
        leftBorder.setStyle("-fx-background-color: #FFFFFF");

        gameBoard.setLeft(leftBorder);

        Scene gameLoop = new Scene(gameBoard);

        BorderPane scorePane = new BorderPane();
        scorePane.setPrefSize(width, height);

        Popup popup = new Popup();
        VBox scoreBox = new VBox();
        scoreBox.setSpacing(10);
        
        Button submitScore = new Button("Submit");
        submitScore.setOnAction((event) -> {
            // TODO submit through tetriservice to database
            primaryStage.setScene(gameLoop);
        });
        
        scoreBox.getChildren().addAll(new Label("nickname: "), new TextField(), scoreLabel2, submitScore);
        scoreBox.setStyle("-fx-background-color: #FFFFFF");
        scoreBox.setPadding(new Insets(15, 15, 15, 15));
        popup.getContent().addAll(scoreBox);

        Button startGame = new Button("Start game");
        startGame.setOnAction((event) -> {

            primaryStage.setScene(gameLoop);
            new AnimationTimer() {

                long previous = 0;

                @Override

                public void handle(long now) {

                    if (now - previous < 100000000) {

                        return;

                    } else if (tetris.isGameOver()) {
                        closing = true;
                        stop();
                        scoreLabel2.textProperty().bind(Bindings.createStringBinding(() -> ("Your score: " + tetris.getScore()), score));

                        popup.show(primaryStage);
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
                    while (!tetris.isGameOver() && !closing) {

                        try {
                            Thread.sleep(1000);
                            tetris.updateTetris();
                        } catch (InterruptedException e) {
                        }
                    }
                }
            }.start();
        });

        Scene scoreboard = new Scene(scorePane);

        Button highscores = new Button("Highscores");
        highscores.setOnAction((event) -> {
            primaryStage.setScene(scoreboard);
        });

        VBox startSet = new VBox();
        startSet.getChildren().addAll(startGame, highscores);

        Scene start = new Scene(startSet);

        Button backToStart = new Button("Back");
        backToStart.setOnAction((event) -> {
            primaryStage.setScene(start);
        });

        scorePane.setBottom(backToStart);

        gameLoop.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.DOWN) {
                tetris.updateTetris();
            }
        });

        gameLoop.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.LEFT) {
                tetris.moveTetromino(-1);
            }
        });

        gameLoop.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.RIGHT) {
                tetris.moveTetromino(1);
            }
        });

        gameLoop.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.SPACE) {
                tetris.rotation();
            }
        });

        primaryStage.setScene(start);

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
        closing = true;

    }

    public static void main(String[] args) {
        launch(TetrisUi.class
        );
    }
}
