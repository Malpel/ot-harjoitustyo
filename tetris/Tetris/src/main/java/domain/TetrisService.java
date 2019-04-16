package domain;

import javafx.scene.paint.Color;
import java.awt.Point;
import java.util.Random;

public class TetrisService {

    Tetris tetris;
    Tetromino[] tetrominos;
    Tetromino faller;
    final int matrixWidth = 10;
    final int matrixHeight = 22;
    final int canvasWidth = 350; // 35 * matrixWidth
    final int canvasHeight = 770; // 35 * matrixHeight
    final int scale = canvasHeight / matrixHeight; // scale is used to determine correct sizes for drawing
    int score;
    boolean gameOver;

    public TetrisService(Color background) {
        tetris = new Tetris(matrixWidth, matrixHeight, background);
        tetrominos = new Tetromino[7];

        Point[][] tetrominoI = {
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3)},
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3)}
        };

        Point[][] tetrominoT = {
            {new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1)},
            {new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2)},
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2)},
            {new Point(1, 0), new Point(1, 1), new Point(2, 1), new Point(1, 2)}
        };

        Point[][] tetrominoL = {
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 2)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 2)},
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 0)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 0)}
        };

        Point[][] tetrominoJ = {
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 0)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 2)},
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 2)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 0)}
        };

        Point[][] tetrominoS = {
            {new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1)},
            {new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2)},
            {new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1)},
            {new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2)}
        };

        Point[][] tetrominoZ = {
            {new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1)},
            {new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2)},
            {new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1)},
            {new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2)}
        };

        Point[][] tetrominoO = {
            {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)},
            {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)},
            {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)},
            {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)}
        };

        tetrominos[0] = new Tetromino(tetrominoI, Color.CYAN);
        tetrominos[1] = new Tetromino(tetrominoT, Color.DEEPPINK);
        tetrominos[2] = new Tetromino(tetrominoL, Color.ORANGE);
        tetrominos[3] = new Tetromino(tetrominoJ, Color.ROYALBLUE);
        tetrominos[4] = new Tetromino(tetrominoS, Color.FORESTGREEN);
        tetrominos[5] = new Tetromino(tetrominoZ, Color.RED);
        tetrominos[6] = new Tetromino(tetrominoO, Color.GOLD);

        score = 0;
        gameOver = false;
    }

    // doubles as both making the tetromino fall and updating the fixed matrix pieces
    public void updateTetris() {

        if (!tetris.blocked(faller, faller.getOrigin().y + 1, faller.getOrigin().x)) {
            faller.dropDown();
        } else {
            int fullRows = tetris.updateMatrix(faller);
            updateScore(fullRows);
            newTetromino();
            if (tetris.blocked(faller, faller.getOrigin().y, faller.getOrigin().x)) {
                gameOver();
                System.out.println("Yeah");
            }

        }

    }

    public void updateScore(int i) {
        switch (i) {
            case 1:
                score += 40;
                break;
            case 2:
                score += 100;
                break;
            case 3:
                score += 300;
                break;
            case 4:
                score += 1200;
                break;
        }
    }

    public void moveTetromino(int i) {
        if (!tetris.blocked(faller, faller.getOrigin().y, faller.getOrigin().x + i)) {
            faller.move(i);
        }
    }

    public void rotation() {
        Point[][] rotations = faller.getRotations();
        int rotation = faller.getRotation();

        if (rotation + 1 > 3) {
            rotation = 0;
        } else {
            rotation += 1;
        }

        Tetromino newRotation = new Tetromino(faller.getRotations(), faller.getColor());
        newRotation.setTetromino(rotations[rotation]);

        if (!tetris.blocked(newRotation, faller.getOrigin().y, faller.getOrigin().x)) {
            faller.setTetromino(rotations[rotation]);
            faller.rotation = rotation;
        }

    }

    // origin needs to be reset, otherwise the current system will spawn a new tetromino 
    // in the last spot of the same shape
    public void newTetromino() {
        Random r = new Random();
        int t = r.nextInt(7);
        int rotation = r.nextInt(4);
        Tetromino faller = tetrominos[t];
        faller.setOrigin(4, 0);
        faller.setTetromino(faller.getRotations()[rotation]);
        faller.rotation = rotation;
        this.faller = faller;

    }

    // TODO game over
    public void gameOver() {
        gameOver = true;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Tetris getTetris() {
        return tetris;
    }

    public int getMatrixWidth() {
        return matrixWidth;
    }

    public int getMatrixHeight() {
        return matrixHeight;
    }

    public int getCanvasWidth() {
        return canvasWidth;
    }

    public int getCanvasHeight() {
        return canvasHeight;
    }

    public int getScale() {
        return scale;
    }

    public Color[][] getMatrix() {
        return tetris.getMatrix();
    }

    public Tetromino getFaller() {
        return faller;
    }

    public void setFaller(Tetromino faller) {
        this.faller = faller;
    }

    public Tetromino[] getTetrominos() {
        return tetrominos;
    }

    public void setTetrominos(Tetromino[] tetrominos) {
        this.tetrominos = tetrominos;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

}
