package domain;


import javafx.scene.paint.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Tetromino {

    Point[][] rotations;
    Point[] tetromino;
    Point origin;
    Color color;
    int rotation;

    public Tetromino(Point[][] t, Color color) {
        origin = new Point(4, 0);
        rotations = t;
        tetromino = new Point[4];
        this.color = color;
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

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }
    

    public void move(int i) {
        this.origin.x += i;
    }

    public void dropDown() {
        this.origin.y += 1;
    }

}
