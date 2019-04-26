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

public class TetrominoTest {

    Tetromino[] tetrominos;
    Tetromino testSubject;
    Point origin;

    public TetrominoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

        tetrominos = new Tetromino[7];
        origin = new Point(4, 0);

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
        testSubject = faller;

    }

    @After
    public void tearDown() {
    }

    @Test
    public void tetrominoFallsDown() {;
        testSubject.dropDown();
        assertThat(testSubject.getOrigin(), is(equalTo(new Point(4, 1))));

    }

    @Test
    public void tetrominoMovesRight() {
        testSubject.move(1);
        assertThat(testSubject.getOrigin(), is(equalTo(new Point(5, 0))));
    }

    @Test
    public void tetrominoMovesLeft() {
        testSubject.move(-1);
        assertThat(testSubject.getOrigin(), is(equalTo(new Point(3, 0))));
    }

    @Test
    public void originIsReset() {
        testSubject.dropDown();
        testSubject.dropDown();
        testSubject.dropDown();
        testSubject.move(1);
        testSubject.move(1);
        testSubject.dropDown();
        testSubject.dropDown();
        testSubject.move(1);
        testSubject.move(1);
        testSubject.dropDown();

        testSubject.setOrigin(4, 0);

        assertEquals(testSubject.getOrigin(), new Point(4, 0));

    }
}
