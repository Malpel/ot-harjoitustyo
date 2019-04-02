package domain;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Tetris {

    int[][] matrix;
    int width;
    int height;
    Tetromino faller;

    public Tetris(int width, int height) {
        matrix = new int[height][width];
        this.width = width;
        this.height = height;

    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public Tetromino getFaller() {
        return faller;
    }

    public void setFaller(Tetromino faller) {
        this.faller = faller;
    }

    public void updateMatrix() {
        int rowSize = faller.getTetromino().length;
        int colSize = faller.getTetromino()[0].length;
        int[][] tetra = faller.getTetromino();
        boolean allOk = false;
        //rework this
        for (int y = 0; y < rowSize; y++) {
            for (int x = 0; x < colSize; x++) {
                if (tetra[y][x] != 0) {
                    if (!blocked(y + (faller.getOrigin().y + 1), (x + faller.getOrigin().x))) {

                        allOk = true;
                    } else {
                        allOk = false;

                    }

                }

            }

        }
        
        if (allOk) {
            faller.dropDown();
        } else {
            for (int y = 0; y < rowSize; y++) {
                for (int x = 0; x < colSize; x++) {
                    matrix[y + faller.getOrigin().y][x + faller.getOrigin().x] = 1;

                }

            }

        }

    }

    //rework this
    public boolean blocked(int newRow, int newCol) {

        if (newRow >= matrix.length) {
            return true;
        } else if (matrix[newRow][newCol] != 0) {
            return true;
        }

        return false;
    }

}
