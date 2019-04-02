
package domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TetrisTest {
    Tetris tetris;
    
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
        tetris = new Tetris(10, 22);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void matrixDimensionAreCorrect() {
        
    }

}
