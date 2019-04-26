package domain;

import java.awt.Point;
import java.util.Random;
import javafx.scene.paint.Color;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TetrisTest {

    Tetris tetris;
    Tetromino[] tetrominos;
    Tetromino tetromino;

    public TetrisTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        tetris = new Tetris(22, 10, Color.WHITE);
        tetrominos = new Tetromino[7];

        Point[][] tetrominoI = {
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3)},
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3)}
        };

        Point[][] tetrominoT = {
            {new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1)},
            {new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2)},
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2)},
            {new Point(1, 0), new Point(1, 1), new Point(2, 1), new Point(1, 2)}
        };

        Point[][] tetrominoL = {
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 2)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 2)},
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 0)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 0)}
        };

        Point[][] tetrominoJ = {
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 0)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 2)},
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 2)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 0)}
        };

        Point[][] tetrominoS = {
            {new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1)},
            {new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2)},
            {new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1)},
            {new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2)}
        };

        Point[][] tetrominoZ = {
            {new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1)},
            {new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2)},
            {new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1)},
            {new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2)}
        };

        Point[][] tetrominoO = {
            {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)},
            {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)},
            {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)},
            {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)}
        };

        tetrominos[0] = new Tetromino(tetrominoI, Color.CYAN);
        tetrominos[1] = new Tetromino(tetrominoT, Color.MAGENTA);
        tetrominos[2] = new Tetromino(tetrominoL, Color.ORANGE);
        tetrominos[3] = new Tetromino(tetrominoJ, Color.BLUE);
        tetrominos[4] = new Tetromino(tetrominoS, Color.GREEN);
        tetrominos[5] = new Tetromino(tetrominoZ, Color.RED);
        tetrominos[6] = new Tetromino(tetrominoO, Color.YELLOW);

        Random r = new Random();
        int t = r.nextInt(7);
        int rotation = r.nextInt(4);
        Tetromino faller = tetrominos[t];
        faller.setOrigin(4, 0);
        faller.setTetromino(faller.getRotations()[rotation]);
        faller.setRotation(rotation);
        tetromino = faller;

    }

    @After
    public void tearDown() {
    }

    @Test
    public void tetrominoCannotGoOverEdgeLeft() {

        assertEquals(true, tetris.blocked(tetromino, tetromino.getOrigin().y, tetromino.getOrigin().x - 15));
    }

    @Test
    public void tetrominoCannotGoOverEdgeRight() {

        assertEquals(true, tetris.blocked(tetromino, tetromino.getOrigin().y, tetromino.getOrigin().x + 237));
    }

    @Test
    public void tetrominoCannotGoOverEdgeBottom() {

        assertEquals(true, tetris.blocked(tetromino, tetromino.getOrigin().y + 50, tetromino.getOrigin().x));
    }

    @Test
    public void tetrominoGetsBlockedByFixedMatrix() {

        for (int i = 0; i < tetris.getHeight(); i++) {
            for (int j = 0; j < tetris.getWidth(); j++) {
                tetris.getMatrix()[i][j] = Color.ALICEBLUE;
            }
        }

        assertEquals(true, tetris.blocked(tetromino, tetromino.getOrigin().y, tetromino.getOrigin().x));
    }

    // can't test too full a matrix; removal algorithm is slow 
    @Test
    public void removeRowsEmptiesMatrix() {
        for (int i = tetris.getHeight() - 3; i < tetris.getHeight(); i++) {
            for (int j = 0; j < tetris.getWidth(); j++) {
                tetris.getMatrix()[i][j] = Color.ALICEBLUE;
            }
        }

        tetris.checkFullRows();
        boolean empty = true;

        for (int i = 0; i < tetris.getHeight(); i++) {
            for (int j = 0; j < tetris.getWidth(); j++) {
                if (tetris.getMatrix()[i][j] != Color.WHITE) {
                    empty = false;
                }
            }
        }

        assertEquals(true, empty);

    }

    @Test
    public void removeRowsRemovesCorrectRows() {
        for (int i = tetris.getHeight() - 5; i < tetris.getHeight() - 2; i++) {
            for (int j = 0; j < tetris.getWidth(); j++) {
                tetris.getMatrix()[i][j] = Color.ALICEBLUE;
            }
        }

        for (int a = 1; a < tetris.getWidth(); a++) {
            if (a % 2 == 0) {
                tetris.getMatrix()[tetris.getHeight() - 3][a] = tetris.getBackground();
            }
        }

        tetris.checkFullRows();

        boolean empty = true;

        for (int i = 0; i < tetris.getWidth(); i++) {
            if (tetris.getMatrix()[tetris.getHeight() - 3][i] != tetris.getBackground()) {
                empty = false;
            }
        }

        assertEquals(false, empty);

    }

    @Test
    public void checkFullRowsCountsRowsCorrectly() {
        for (int i = tetris.getHeight() - 5; i < tetris.getHeight() - 2; i++) {
            for (int j = 0; j < tetris.getWidth(); j++) {
                tetris.getMatrix()[i][j] = Color.ALICEBLUE;
            }
        }

        int fullRows = tetris.checkFullRows();

        assertEquals(3, fullRows);
    }

}
