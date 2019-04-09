package domain;

import java.awt.Point;
import javafx.scene.paint.Color;
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

        ts = new TetrisService(Color.WHITE);
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
        ts.rotation();
        assertThat(ts.getFaller().getRotation(), is(not(beforeRotation)));
    }
    
    @Test 
    public void rotationUpdatesTetrominoRotation() {
        int beforeRotation = ts.getFaller().rotation;
        ts.rotation();
        assertThat(ts.getFaller().getRotation(), is(not(beforeRotation)));
    }
    
    @Test
    public void moveTetrominoMovesTetrominoLeft() {
        ts.moveTetromino(-2);
        assertEquals(new Point(2,0), ts.getFaller().getOrigin());
    }
    
    @Test
    public void moveTetrominoMovesTetrominoRight() {
        ts.moveTetromino(2);
        assertEquals(new Point(6,0), ts.getFaller().getOrigin());
    }

}
