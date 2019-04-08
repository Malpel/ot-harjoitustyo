package domain;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;

public class Tetris {

    Color[][] matrix;
    int width;
    int height;
    Color background;

    public Tetris(int width, int height, Color background) {
        matrix = new Color[height][width];
        this.width = width;
        this.height = height;
        this.background = background;
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                matrix[y][x] = this.background;
            }
        }

    }

    public Color[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(Color[][] matrix) {
        this.matrix = matrix;
    }

    public void updateMatrix(Tetromino faller) {
        for (Point p : faller.tetromino) {
            matrix[faller.getOrigin().y + p.y][faller.getOrigin().x + p.x] = faller.getColor(); // tetromino.color
        }
        checkFullRows();
        faller = null;
    }

    public boolean blocked(Tetromino faller, int newRow, int newCol) {

        for (Point p : faller.tetromino) {
            if (p.y + newRow >= height || p.x + newCol >= width || p.x + newCol < 0) {
                return true;
            }
            if (matrix[p.y + newRow][p.x + newCol] != background) {
                return true;
            }

        }

        return false;
    }

    public void removeRow(int row) {
        for (int y = row - 1; y > 0; y--) {
            for (int x = 0; x < width; x++) {
                matrix[y + 1][x] = matrix[y][x];
            }
        }
    }

    public void checkFullRows() {
        boolean fullRow = true;
        for (int y = height - 1; y > 0; y--) {
            for (int x = 0; x < width; x++) {
                if (matrix[y][x] == background) {
                    fullRow = false;
                }
            }
            if (fullRow) {
                removeRow(y);
                y += 1;

            } else {
                fullRow = true;
            }

        }
    }

}
