package domain;

import dao.ScoreDao;
import javafx.scene.paint.Color;
import java.awt.Point;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

/**
 * The main backend workhorse for the program. Handles the coordination between
 * the TetrisUi and Tetris, Tetromino, and ScoreDao classes.
 *
 * @see Tetris
 * @see Tetromino
 * @see ScoreDao
 */
public class TetrisService {

    private Tetris tetris;
    private Tetromino[] tetrominos;
    private Tetromino faller;
    private final int matrixWidth = 10;
    private final int matrixHeight = 22;
    private final int canvasWidth = 270; //350
    private final int cavasHeight = 594;
    private final int scale = cavasHeight / matrixHeight; // scale is used to determine correct sizes for drawing
    private int score;
    private boolean gameOver;
    private ScoreDao hsDao;
    private final String databaseUrl = "jdbc:sqlite:tetris.db";
    private final Color background = Color.rgb(43, 42, 42);
    private int level;
    private int difficulty;
    private int lineClears;

    public TetrisService() {

        tetrominos = new Tetromino[7];

        Point[][] tetrominoI = {
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3)},
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3)}
        };

        Point[][] tetrominoT = {
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2)},
            {new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2)},
            {new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1)},
            {new Point(1, 0), new Point(1, 1), new Point(2, 1), new Point(1, 2)},
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
        tetrominos[1] = new Tetromino(tetrominoT, Color.DEEPPINK);
        tetrominos[2] = new Tetromino(tetrominoL, Color.ORANGE);
        tetrominos[3] = new Tetromino(tetrominoJ, Color.ROYALBLUE);
        tetrominos[4] = new Tetromino(tetrominoS, Color.FORESTGREEN);
        tetrominos[5] = new Tetromino(tetrominoZ, Color.RED);
        tetrominos[6] = new Tetromino(tetrominoO, Color.GOLD);

        reset();

    }

    // 
    /**
     * Updates the game state. Either makes the tetromino fall or fixes it to
     * the matrix, updates the score if needed, and checks if game is over.
     *
     * @see Tetromino#dropDown()
     * @see Tetris#blocked(domain.Tetromino, int, int)
     * @see Tetris#updateMatrix(domain.Tetromino)
     * @see #updateScore(int)
     * @see #newTetromino()
     */
    public void updateTetris() {

        if (!tetris.blocked(faller, faller.getOrigin().y + 1, faller.getOrigin().x)) {
            faller.dropDown();
        } else {
            int fullRows = tetris.updateMatrix(faller);
            updateScore(fullRows);
            newTetromino();
            if (tetris.blocked(faller, faller.getOrigin().y, faller.getOrigin().x)) {
                gameOver = true;
            }
        }
    }

    /**
     * Updates the score, calls method for level increase if needed. Original
     * Nintendo-like scoring..
     *
     * @param i The amount of full rows.
     * @see #increaseLevel(int)
     */
    public void updateScore(int i) {
        switch (i) {
            case 1:
                score += 40 * (level + 1);
                break;
            case 2:
                score += 100 * (level + 1);
                ;
                break;
            case 3:
                score += 300 * (level + 1);
                break;
            case 4:
                score += 1200 * (level + 1);
                break;
        }
        lineClears += i;

        increaseLevel(i);

    }

    /**
     * Increases the level and difficulty values.
     *
     * @param i the amount of cleared lines
     */
    public void increaseLevel(int i) {
        if (i != 0 && lineClears % 10 == 0 && lineClears > 0) {
            level++;
            if (level < 10) {
                difficulty -= 80;
            } else if (level > 10 && level < 13) {
                difficulty = 80;
            } else if (level >= 13 && level < 16) {
                difficulty = 70;
            } else if (level >= 16 && level < 18) {
                difficulty = 50;
            } else if (level >= 18 && level < 29) {
                difficulty = 30;
            } else if (level >= 29) {
                difficulty = 20;
            }
        }
    }

    /**
     * Checks that the move is legitimate.
     *
     * @param i Either -1 or 1 in practice, move one to left or right.
     * @see Tetromino#move(int)
     * @see Tetris#blocked(domain.Tetromino, int, int)
     */
    public void moveTetromino(int i) {
        if (!tetris.blocked(faller, faller.getOrigin().y, faller.getOrigin().x + i)) {
            faller.move(i);
        }
    }

    /**
     * Rotates the tetromino. Checks that it can be rotated (isn't blocked).
     * Currently no wallkicks allowed.
     *
     * @see Tetris#blocked(domain.Tetromino, int, int)
     */
    public void rotate() {
        Point[][] orientations = faller.getOrientations();
        int orientation = faller.getOrientation();

        if (orientation + 1 > 3) {
            orientation = 0;
        } else {
            orientation += 1;
        }

        Tetromino newRotation = new Tetromino(orientations, faller.getColor());
        newRotation.setTetromino(orientations[orientation]);

        if (!tetris.blocked(newRotation, faller.getOrigin().y, faller.getOrigin().x)) {
            faller.setTetromino(orientations[orientation]);
            faller.setOrientation(orientation);
        }
    }

    /**
     * Spawns a new, pseudo-randomly chosen tetromino.
     *
     */
    public void newTetromino() {
        Random r = new Random();
        int t = r.nextInt(7);
        int orientation = r.nextInt(4);
        Tetromino faller = tetrominos[t];

        // origin needs to be reset, otherwise the current system will spawn a new tetromino 
        // in the last spot of the same shape
        faller.setOrigin(4, 0);

        faller.setTetromino(faller.getOrientations()[orientation]);
        faller.setOrientation(orientation);
        this.faller = faller;

    }

    /**
     * Saves score into the database.
     *
     * @param name Username
     * @throws SQLException SQLException
     */
    public void saveScore(String name) throws SQLException {
        hsDao.create(name, score);
    }

    /**
     * Retrieves scores from database and returns a sublist.
     *
     * @return Top ten high scores or the whole list if its size is less than
     * 10.
     * @throws SQLException SQLException
     */
    public List<String> getHighScores() throws SQLException {
        List<String> allScores = hsDao.findAll();

        if (allScores.size() < 10) {
            return allScores;
        }

        return allScores.subList(0, 9);
    }

    /**
     * Resets the game state.
     *
     */
    public void reset() {
        newTetromino();
        score = 0;
        gameOver = false;
        hsDao = new ScoreDao(databaseUrl);
        tetris = new Tetris(matrixWidth, matrixHeight, background);
        level = 0;
        lineClears = 0;
        difficulty = 800;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Tetris getTetris() {
        return tetris;
    }

    public int getMatrixWidth() {
        return matrixWidth;
    }

    public int getMatrixHeight() {
        return matrixHeight;
    }

    public int getCanvasWidth() {
        return canvasWidth;
    }

    public int getCanvasHeight() {
        return cavasHeight;
    }

    public int getScale() {
        return scale;
    }

    public Color[][] getMatrix() {
        return tetris.getMatrix();
    }

    public Tetromino getFaller() {
        return faller;
    }

    public void setFaller(Tetromino faller) {
        this.faller = faller;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getLevel() {
        return level;
    }
}
