package domain;

import java.awt.Point;
import javafx.scene.paint.Color;

/**
 * Tetris class manages the matrix or the "game board". 
 * Fixes falling pieces into the matrix, checks if certain positions are filled,
 * and takes care of full rows.
 * @author malpel
 */
public class Tetris {

    private Color[][] matrix;
    private int width;
    private int height;
    private Color background;

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

    public int getWidth() {
        return width;
    }


    public int getHeight() {
        return height;
    }

    public Color getBackground() {
        return background;
    }

    public Color[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(Color[][] matrix) {
        this.matrix = matrix;
    }

    /**
     * Fixes the falling tetromino into the matrix. Possible full rows are checked. 
     * @param faller The current falling tetromino.
     * @see #checkFullRows()
     * @return The amount of full rows; used for scoring.
     */
    public int updateMatrix(Tetromino faller) {
        for (Point p : faller.getTetromino()) {
            matrix[faller.getOrigin().y + p.y][faller.getOrigin().x + p.x] = faller.getColor();
        }
        int fullRows = checkFullRows();
        faller = null;
        return fullRows;
    }

    /**
     * Finds out if the something blocks the falling tetromino. 
     * Can be blocked by the edges of the board or by the fixed matrix.
     * @param faller The current falling tetromino.
     * @param newRow Potential new row.
     * @param newCol Potential new column.
     * @return Boolean blocked
     */
    public boolean blocked(Tetromino faller, int newRow, int newCol) {
        for (Point p : faller.getTetromino()) {
            if (p.y + newRow >= height || p.x + newCol >= width || p.x + newCol < 0) {
                return true;
            }
            if (matrix[p.y + newRow][p.x + newCol] != background) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes the row by writing over it and moving all rows below it up by one. 
     * @param row The row to be removed.
     */
    public void removeRow(int row) {
        for (int y = row - 1; y > 0; y--) {
            for (int x = 0; x < width; x++) {
                matrix[y + 1][x] = matrix[y][x];
            }
        }
    }

    /**
     * Finds out existing full rows. Calls them to be removed on detection.
     * 
     * @see #removeRow(int)
     * 
     * @return The amount of full rows.
     */
    public int checkFullRows() {
        boolean fullRow = true;
        int fullRows = 0;

        for (int y = height - 1; y > 0; y--) {
            for (int x = 0; x < width; x++) {
                if (matrix[y][x] == background) {
                    fullRow = false;
                }
            }
            if (fullRow) {
                removeRow(y);
                y += 1;
                fullRows++;

            } else {
                fullRow = true;
            }

        }
        return fullRows;
    }
}
