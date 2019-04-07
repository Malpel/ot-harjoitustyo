package domain;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Tetromino {

    Point[][] rotations;
    Point[] tetromino;
    Point origin;
    //color
    //rotation?

    public Tetromino(Point[][] t) {
        origin = new Point(4, 0);
        rotations = t;
        tetromino = new Point[4];
    }

    public Point[][] getRotations() {
        return rotations;
    }

    public void setRotations(Point[][] tetromino) {
        this.rotations = tetromino;
    }

    public Point[] getTetromino() {
        return tetromino;
    }

    public void setTetromino(Point[] tetromino) {
        this.tetromino = tetromino;
    }

    public Point getOrigin() {
        return this.origin;
    }

    public void rotate() {
        
    }

    public void move(int i) {
        this.origin.x += i;
    }

    public void dropDown() {
        this.origin.y += 1;
    }

}
