package domain;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Tetris {

    int[][] matrix;
    int width;
    int height;

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

    public void updateMatrix(Tetromino faller) {
        for (Point p : faller.tetromino) {
            matrix[faller.getOrigin().y + p.y][faller.getOrigin().x + p.x] = 1; // tetromino.color
        }
        faller = null;
    }

    public boolean blocked(Tetromino faller, int newRow, int newCol) {

        for (Point p : faller.tetromino) {
            if (matrix[p.y + newRow][p.x + newCol] != 0) {
                return true;
            }
        }

        return false;
    }

    public void removeRows() {

    }

}
