package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

public class HighscoreDaoTest {
    
    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    String url;
    HighscoreDao dao;

    public HighscoreDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

        url = "jdbc:sqlite:" + temp.getRoot().getAbsolutePath() + "/temp";
        dao = new HighscoreDao(url);

    }

    @After
    public void tearDown() {
        temp.delete();
    }

    @Test
    public void newHighscoresAreAdded() throws SQLException {
        try {
            dao.create("Cao Cao", 7777);
            dao.create("Tester", 9876);
            dao.create("Lann", 5674);
        } catch (SQLException ex) {
            Logger.getLogger(HighscoreDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        assertEquals(3, dao.findAll().size());
    }

    @Test
    public void listIsInCorrectOrder() {
        List<String> list = new ArrayList<>();

        try {
            dao.create("Cao Cao", 7777);
            dao.create("Tester", 9876);
            dao.create("Lann", 5674);
            list = dao.findAll();
        } catch (SQLException ex) {
            Logger.getLogger(HighscoreDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (String s : list) {
            System.out.println(s);
        }

        assertTrue(list.get(0).equals("Tester 9876"));
        assertTrue(list.get(1).equals("Cao Cao 7777"));
        assertTrue(list.get(2).equals("Lann 5674"));

    }

    @Test
    public void databaseCreationWorks() {
        List<String> list = new ArrayList<>();

        try {
            list = dao.findAll();
        } catch (SQLException ex) {
            Logger.getLogger(HighscoreDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        assertFalse(list.equals(null));
    }
}
