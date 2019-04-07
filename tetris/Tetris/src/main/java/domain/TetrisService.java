package domain;

import java.awt.Point;
import java.util.Random;

public class TetrisService {

    Tetris tetris;
    Tetromino[] tetrominos;
    Tetromino faller;
    final int matrixWidth = 10;
    final int matrixHeight = 22;
    final int canvasWidth = 350; //35 * matrixWidth
    final int canvasHeight = 770; //35 * matrixHeight
    final int scale = canvasHeight / matrixHeight;

    public TetrisService() {
        tetris = new Tetris(matrixWidth, matrixHeight);
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

        tetrominos[0] = new Tetromino(I);
        tetrominos[1] = new Tetromino(T);
        tetrominos[2] = new Tetromino(L);
        tetrominos[3] = new Tetromino(J);
        tetrominos[4] = new Tetromino(S);
        tetrominos[5] = new Tetromino(Z);
        tetrominos[6] = new Tetromino(O);

    }

    public void updateTetris() {
        if (!tetris.blocked(faller, faller.getOrigin().y + 1, faller.getOrigin().x)) {
            faller.dropDown();
        } else {
            tetris.updateMatrix(this.faller);
        }
    }

    public void moveTetromino(int i) {
        if (!tetris.blocked(faller, faller.getOrigin().y, faller.getOrigin().x + i)) {
            faller.move(i);
        }
    }

    public void rotation() {
        Point[][] rotations = faller.getRotations();

        for (int i = 0; i < rotations.length; i++) {
            if (rotations[i] == faller.getTetromino()) {
                if (i + 1 >= rotations.length) {
                    Tetromino newRotation = faller;
                    newRotation.setTetromino(rotations[0]);
                    if (!tetris.blocked(newRotation, newRotation.getOrigin().y, newRotation.getOrigin().x)) {
                        faller.setTetromino(rotations[0]);
                    }
                } else {
                    Tetromino newRotation = faller;
                    newRotation.setTetromino(rotations[i]);
                    if (!tetris.blocked(newRotation, newRotation.getOrigin().y, newRotation.getOrigin().x)) {
                        faller.setTetromino(rotations[i]);
                    }
                }

            }
        }
    }

    // change to random
    public void newTetromino() {
        Random r = new Random();
        int t = r.nextInt(7);
        int rotation = r.nextInt(4);
        Tetromino faller = tetrominos[t];
        faller.setTetromino(faller.getRotations()[rotation]);
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

    public int[][] getMatrix() {
        return tetris.getMatrix();
    }

    public Tetromino getFaller() {
        return this.faller;
    }

}
