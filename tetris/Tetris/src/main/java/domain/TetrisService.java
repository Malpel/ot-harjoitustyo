package domain;

public class TetrisService {

    Tetris tetris;
    Tetromino[] tetrominos;
    final int matrixWidth = 10;
    final int matrixHeight = 22;
    final int canvasWidth = 350; //35 * matrixWidth
    final int canvasHeight = 770; //35 * matrixHeight
    final int scale = canvasHeight / matrixHeight;

    public TetrisService() {
        tetris = new Tetris(matrixWidth, matrixHeight);
        tetrominos = new Tetromino[7];

        int[][] I = {
            {1, 1, 1, 1}
        };

        int[][] T = {
            {0, 1, 0},
            {1, 1, 1}
        };

        int[][] L = {
            {1, 0},
            {1, 0},
            {1, 1}};

        int[][] J = {
            {0, 1},
            {0, 1},
            {1, 1}};

        int[][] S = {
            {0, 1, 1},
            {1, 1, 0}
        };

        int[][] Z = {
            {1, 1, 0},
            {0, 1, 1}
        };

        int[][] O = {
            {1, 1},
            {1, 1}
        };

        tetrominos[0] = new Tetromino(I);
        tetrominos[1] = new Tetromino(T);
        tetrominos[2] = new Tetromino(L);
        tetrominos[3] = new Tetromino(J);
        tetrominos[4] = new Tetromino(S);
        tetrominos[5] = new Tetromino(Z);
        tetrominos[6] = new Tetromino(O);

    }

    public void updateTetris() {
        tetris.updateMatrix();
    }

    // change to random
    public void newTetromino() {
        tetris.setFaller(tetrominos[0]);
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
        return canvasHeight;
    }

    public int getScale() {
        return scale;
    }
    
    public int[][] getMatrix() {
        return tetris.getMatrix();
    }
    
    public Tetromino getFaller() {
        return tetris.getFaller();
    }

}
