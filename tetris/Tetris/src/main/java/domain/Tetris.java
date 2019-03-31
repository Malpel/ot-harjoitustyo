package domain;

import java.util.ArrayList;
import java.util.List;

public class Tetris {

    Tetromino[] tetrominos;
    int[][] matrix;
    int width;
    int height;

    public Tetris(int width, int height) {
        tetrominos = new Tetromino[7];
        matrix = new int[height][width];
        this.width = width;
        this.height = height;

        int[][] I = {
            {0, 0, 0, 0},
            {1, 1, 1, 1},
            {0, 0, 0, 0},
            {0, 0, 0, 0}};

        int[][] T = {
            {0, 1, 0},
            {1, 1, 1},
            {0, 0, 0}};

        int[][] L = {
            {0, 1, 0},
            {0, 1, 0},
            {0, 1, 1}};

        int[][] J = {
            {0, 1, 0},
            {0, 1, 0},
            {1, 1, 0}};

        int[][] S = {
            {0, 1, 1},
            {1, 1, 0},
            {0, 0, 0}
        };

        int[][] Z = {
            {1, 1, 0},
            {0, 1, 1},
            {0, 0, 0}
        };

        int[][] O = {
            {1, 1},
            {1, 1}
        };

        tetrominos[0] = new Tetromino(I);
        tetrominos[1] = new Tetromino(T);
        tetrominos[2] = new Tetromino(L);
        tetrominos[3] = new Tetromino(J);
        tetrominos[4] = new Tetromino(S);
        tetrominos[5] = new Tetromino(Z);
        tetrominos[6] = new Tetromino(O);
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public Tetromino[] getTetrominos() {
        return tetrominos;
    }

    public void setTetrominos(Tetromino[] tetrominos) {
        this.tetrominos = tetrominos;
    }

    public void newTetromino(Tetromino t) {

        int rowSize = t.tetromino.length;

        for (int y = 0; y < rowSize; y++) {
            for (int x = 0; x < rowSize; x++) {
                if (t.tetromino[y][x] == 1) {
                    matrix[y][x + 3] = 1;

                }

            }

        }

    }

    public void updateMatrix() {
        
        

    }

}
