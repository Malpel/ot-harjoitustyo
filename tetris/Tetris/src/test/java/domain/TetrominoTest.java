
package domain;

import java.awt.Point;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class TetrominoTest {
    
    Tetromino tetromino;
    
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
        
        int[][] L = {
            {1, 0},
            {1, 0},
            {1, 1}};
        
        tetromino = new Tetromino(L);


    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void tetrominoFallsDown() {;
        tetromino.dropDown();
        assertEquals(new Point(4, 1), tetromino.getOrigin());
       
    }


}
