package ui;

import domain.TetrisService;
import java.awt.Point;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TetrisUi extends Application {

    int width;
    int height;
    int scale;
    int time;
    TetrisService tetris;
    int speed;
    ScheduledExecutorService ex;
    boolean closing;

    @Override
    public void init() {
        tetris = new TetrisService();
        width = tetris.getCanvasWidth();
        height = tetris.getCanvasHeight();
        scale = tetris.getScale();
        tetris.newTetromino();
        speed = 1000;
        time = 0;
        ex = Executors.newScheduledThreadPool(1);
        closing = false;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        BorderPane gameBoard = new BorderPane();

        gameBoard.setCenter(canvas);

        VBox leftBorder = new VBox();
        HBox test = new HBox();

        Text scoreText = new Text();
        Text scoreTextFinal = new Text();

        Text timeText = new Text();
        timeText.setText("Time : " + time);

        Text levelText = new Text();
        levelText.setText("Level: " + tetris.getLevel());

        leftBorder.getChildren().addAll(levelText, scoreText, timeText);
        leftBorder.setAlignment(Pos.CENTER);
        leftBorder.setPadding(new Insets(15, 15, 15, 15));
        leftBorder.setSpacing(10);
        leftBorder.setStyle("-fx-background-color: #FFFFFF");
        
        gameBoard.setLeft(leftBorder);

        // gameplay scene
        Scene gameLoop = new Scene(gameBoard);
        Button startGame = new Button("Start game");

        // highscore screen
        BorderPane scorePane = new BorderPane();
        scorePane.setPrefSize(350, 300);

        Scene scoreboard = new Scene(scorePane);

        Button highScores = new Button("High Scores");
        highScores.setOnAction((event) -> {
            VBox scoreList = new VBox();
            List<String> list = new ArrayList<>();
            try {
                list = tetris.getHighScores();
            } catch (SQLException ex) {
                Logger.getLogger(TetrisUi.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (String s : list) {
                scoreList.getChildren().add(new Label(s));
            }
            scoreList.setAlignment(Pos.CENTER);
            scorePane.setCenter(scoreList);

            primaryStage.setScene(scoreboard);

        });

        // the start screen 
        BorderPane startPane = new BorderPane();
        VBox startSet = new VBox();
        startSet.setPadding(new Insets(15, 15, 15, 15));
        startSet.setSpacing(10);
        startSet.getChildren().addAll(startGame, highScores);
        startSet.setAlignment(Pos.CENTER);
        startPane.setCenter(startSet);
        startPane.setPrefSize(350, 200);

        Scene start = new Scene(startPane);

        Button backToStart = new Button("Back");
        backToStart.setOnAction((event) -> {
            primaryStage.setScene(start);
        });

        scorePane.setBottom(backToStart);

        // submit score to database
        TextField nameField = new TextField();
        Button submitScore = new Button("Submit");
        submitScore.setOnAction((event) -> {
            try {
                tetris.saveScore(nameField.getText());
                primaryStage.setScene(start);
            } catch (SQLException ex) {
                Logger.getLogger(TetrisUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        BorderPane submitPane = new BorderPane();
        VBox submitBox = new VBox();
        submitBox.setSpacing(10);
        submitBox.setPadding(new Insets(15, 15, 15, 15));
        submitBox.getChildren().addAll(new Label("Name: "), nameField, scoreTextFinal, submitScore);
        submitBox.setPrefSize(350, 200);
        submitPane.setCenter(submitBox);

        Scene saveScore = new Scene(submitPane);

        // game over popup; save score or return to start screen
        Popup popup = new Popup();
        VBox popupBox = new VBox();
        popupBox.setSpacing(10);
        popupBox.setStyle("-fx-background-color: #FFFFFF");
        popupBox.setPadding(new Insets(15, 15, 15, 15));
        popupBox.setPrefSize(350, 200);
        popupBox.setAlignment(Pos.CENTER);

        Button toSubmitScreen = new Button("Yes");
        toSubmitScreen.setOnAction((even) -> {
            popup.hide();
            primaryStage.setScene(saveScore);
        });
        Button toStartScreen = new Button("No");
        toStartScreen.setOnAction((event) -> {
            popup.hide();
            primaryStage.setScene(start);
        });

        popupBox.getChildren().addAll(new Text("Game Over!"), new Text("Save score?"), toSubmitScreen, toStartScreen);
        popup.getContent().addAll(popupBox);

        // keybindings for controls
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
                tetris.rotate();
            }
        });

        // threads for updating time and making the tetromio fall
        Runnable tetrisUpdate = () -> {
            tetris.updateTetris();
        };

        // the game loop; separate into its own method?
        startGame.setOnAction((event) -> {

            tetris.reset();

            ex = Executors.newScheduledThreadPool(1);
            Runnable timerUpdate = () -> {
                time++;
                timeText.setText("Time: " + String.valueOf(time));

            };

            ex.scheduleWithFixedDelay(timerUpdate, 1, 1, TimeUnit.SECONDS);
            //ex.scheduleWithFixedDelay(tetrisUpdate, 1000, tetris.getDifficulty(), TimeUnit.MILLISECONDS);

            primaryStage.setScene(gameLoop);

            new AnimationTimer() {

                long previous = 0;

                @Override

                public void handle(long now) {

                    if (tetris.isGameOver()) {
                        ex.shutdown();
                        stop();
                        // get the final score
                        scoreTextFinal.setText("Your score: " + tetris.getScore());
                        popup.show(primaryStage);
                    }

                    gc.setFill(Color.WHITE);
                    gc.fillRect(0, 0, width, height);
                    drawMatrix(gc);
                    drawFaller(gc);
                    scoreText.setText("Score: " + tetris.getScore());
                    levelText.setText("Level: " + tetris.getLevel());
                    this.previous = now;

                }

            }.start();

            new Thread() {
                @Override
                public void run() {
                    while (!tetris.isGameOver() && !closing) {
                        try {
                            Thread.sleep(tetris.getDifficulty());
                            tetris.updateTetris();
                        } catch (InterruptedException e) {
                        }
                    }
                }
            }.start();

        });

        primaryStage.setScene(start);
        primaryStage.setTitle("Tetris");
        primaryStage.show();
    }

    /**
     * Draws the falling piece.
     *
     * @param gc GraphicsContext
     */
    public void drawFaller(GraphicsContext gc) {
        gc.setFill(tetris.getFaller().getColor());
        for (Point p : tetris.getFaller().getTetromino()) {
            gc.fillRect(((p.x + tetris.getFaller().getOrigin().x) * scale), ((p.y + tetris.getFaller().getOrigin().y) * scale), scale, scale);

        }
    }

    /**
     * Draws the fixed matrix.
     *
     * @param gc GraphicsContext
     */
    public void drawMatrix(GraphicsContext gc) {
        for (int y = 0; y < tetris.getMatrixHeight(); y++) {
            for (int x = 0; x < tetris.getMatrixWidth(); x++) {
                gc.setFill(tetris.getMatrix()[y][x]);
                gc.fillRect(x * scale, y * scale, scale, scale);
            }
        }
    }

    /**
     * Stop changes the boolean value closing to true. Needed when game is
     * closed before game over to stop the game state updating thread.
     */
    @Override
    public void stop() {
        // needed to stop the threads
        ex.shutdown();
        closing = true;
    }

    public static void main(String[] args) {
        launch(TetrisUi.class
        );
    }
}
