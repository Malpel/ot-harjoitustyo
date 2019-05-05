package domain;

import java.awt.Point;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TetrisServiceTest {

    TetrisService ts;

    public TetrisServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

        ts = new TetrisService();
        ts.newTetromino();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void newTetrominoSetsFaller() {
        assertThat(ts.getFaller(), is(not(equalTo(null))));
    }

    @Test
    public void updateTetrisDropsTetromino() {
        ts.updateTetris();
        assertEquals(new Point(4, 1), ts.getFaller().getOrigin());
    }

    @Test
    public void rotationUpdatesTetrominoPoints() {
        Point[] beforeRotation = ts.getFaller().getTetromino();
        ts.rotate();
        assertThat(ts.getFaller().getOrientation(), is(not(beforeRotation)));
    }

    @Test
    public void rotationUpdatesTetrominoOrientation() {
        int beforeRotation = ts.getFaller().getOrientation();
        ts.rotate();
        assertThat(ts.getFaller().getOrientation(), is(not(beforeRotation)));
    }

    @Test
    public void moveTetrominoMovesTetrominoLeft() {
        ts.moveTetromino(-2);
        assertEquals(new Point(2, 0), ts.getFaller().getOrigin());
    }

    @Test
    public void moveTetrominoMovesTetrominoRight() {
        ts.moveTetromino(2);
        assertEquals(new Point(6, 0), ts.getFaller().getOrigin());
    }

    @Test
    public void scoreUpdatesCorrectly() {
        ts.updateScore(1);
        ts.updateScore(1);
        ts.updateScore(1);
        ts.updateScore(4);
        ts.updateScore(2);
        ts.updateScore(3);
        assertEquals(1720, ts.getScore());
    }

    @Test
    public void increaseLevelUpdatesSubstitutionCorrectly() {
        ts.updateScore(1);
        ts.updateScore(4);
        ts.updateScore(2);
        ts.updateScore(3);
        ts.increaseLevel(1);
        assertEquals(640, ts.getDifficulty());

    }

    @Test
    public void increaseLevelUpdatesSettingCorrectly() {
        ts.updateScore(1);
        ts.updateScore(4);
        ts.updateScore(2);
        ts.updateScore(3);
        for (int i = 0; i < 29; i++) {
            ts.increaseLevel(1);
        }
        System.out.println(ts.getLevel());

        assertEquals(20, ts.getDifficulty());
    }

    @Test
    public void updateTetrisEndsGameWhenNeeded() {
        ts.updateTetris();
        for (int h = 0; h < 22; h++) {
            for (int i = 0; i < ts.getMatrixHeight(); i++) {
                ts.updateTetris();
            }
        }
        assertTrue(ts.isGameOver());
    }

}
