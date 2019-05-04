package domain;

import javafx.scene.paint.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Tetromino class takes care of the coordinates and the current orientation/shape of the falling piece.
 * 
 */
public class Tetromino {

    private Point[][] orientations;
    private Point[] tetromino;
    private Point origin;
    private Color color;
    private int orientation;

    public Tetromino(Point[][] t, Color color) {
        origin = new Point(4, 0);
        orientations = t;
        tetromino = new Point[4];
        this.color = color;
    }

    public Point[][] getOrientations() {
        return orientations;
    }

    public void setOrientations(Point[][] tetromino) {
        this.orientations = tetromino;
    }

    public Point[] getTetromino() {
        return tetromino;
    }

    public void setTetromino(Point[] tetromino) {
        this.tetromino = tetromino;
    }

    /**
     * Origin is used to calculate everything movement related.
     * 
     * @return Coordinates.
     */
    public Point getOrigin() {
        return this.origin;
    }

    public void setOrigin(int x, int y) {
        origin.x = x;
        origin.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    /**
     * Changes the horizontal position; negative values move the piece left,
     * positive values to the right.
     *
     *
     * @param i the amount of movement (-1 or 1 in practice)
     */
    public void move(int i) {
        this.origin.x += i;
    }

    /**
     * Increments vertical position by one; makes the piece fall.
     */
    public void dropDown() {
        this.origin.y += 1;
    }

}
