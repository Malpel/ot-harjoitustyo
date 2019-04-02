package domain;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Tetromino {

    int[][] tetromino;
    Point origin;
    boolean falling;

    public Tetromino(int[][] t) {
        origin = new Point(4, 0);
        tetromino = t;
        falling = true;
    }

    public int[][] getTetromino() {
        return tetromino;
    }

    public void setTetromino(int[][] tetromino) {
        this.tetromino = tetromino;
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public Point getOrigin() {
        return this.origin;
    }

    public void rotate() {

    }

    public void moveSideways() {

    }

    public void dropDown() {
        this.origin = new Point(origin.x, origin.y + 1);
    }

}
