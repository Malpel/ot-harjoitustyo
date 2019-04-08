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
    

    public TetrisService(Color background) {
        tetris = new Tetris(matrixWidth, matrixHeight, background);
        tetrominos = new Tetromino[7];

        Point[][] I = {
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3)},
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3)}
        };

        Point[][] T = {
            {new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1)},
            {new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2)},
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2)},
            {new Point(1, 0), new Point(1, 1), new Point(2, 1), new Point(1, 2)}
        };

        Point[][] L = {
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 2)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 2)},
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 0)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 0)}
        };

        Point[][] J = {
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 0)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 2)},
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 2)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 0)}
        };

        Point[][] S = {
            {new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1)},
            {new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2)},
            {new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1)},
            {new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2)}
        };

        Point[][] Z = {
            {new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1)},
            {new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2)},
            {new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1)},
            {new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2)}
        };

        Point[][] O = {
            {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)},
            {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)},
            {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)},
            {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)}
        };

        tetrominos[0] = new Tetromino(I, Color.CYAN);
        tetrominos[1] = new Tetromino(T, Color.MAGENTA);
        tetrominos[2] = new Tetromino(L, Color.ORANGE);
        tetrominos[3] = new Tetromino(J, Color.BLUE);
        tetrominos[4] = new Tetromino(S, Color.GREEN);
        tetrominos[5] = new Tetromino(Z, Color.RED);
        tetrominos[6] = new Tetromino(O, Color.YELLOW);

    }

    // doubles as both making the tetromino fall and updating the fixed matrix pieces
    public void updateTetris() {

        if (!tetris.blocked(faller, faller.getOrigin().y + 1, faller.getOrigin().x)) {
            faller.dropDown();
        } else {
            tetris.updateMatrix(faller);
            newTetromino();
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
        return this.faller;
    }

}
