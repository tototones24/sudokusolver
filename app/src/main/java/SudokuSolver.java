/**
 * Created by jonathan on 9/28/17.
 */

public class SudokuSolver {
    private char[][] board = new char[9][9];
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    //public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    //public static final String ANSI_PURPLE = "\u001B[35m";
    //public static final String ANSI_CYAN = "\u001B[36m";
    //public static final String ANSI_WHITE = "\u001B[37m";
    public boolean[] flip = new boolean[81];


    public static void main(String[] args) {

    }

    public void setupBoard() {
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++) {
                board[i][j] = '.';
            }
        }

        for (int i = 0; i < 81; i++) {
            if (board[i/9][i%9] == '.'){
                flip[i] = true;
            }
        }
    }

    public void printBoard(String color) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (flip[i*9 + j]){
                    System.out.print(color + board[i][j] + ANSI_RESET);
                } else {
                    System.out.print(board[i][j]);
                }
                System.out.print(" ");
                if ((j+1) % 3 == 0 && j != 8) {
                    System.out.print(" | ");
                }
            }
            System.out.println();
            if ((i+1) % 3 == 0 && i != 8) {
                System.out.println("------------------------");
            }
        }
    }

    public boolean solveSudoku() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.'){
                    for (char c = '1'; c <= '9'; c++) {
                        if (isValid(i, j, c)){
                            board[i][j] = c;

                            if (solveSudoku()){
                                return true;
                            } else {
                                board[i][j] = '.';
                            }
                        }
                    }

                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(int row, int col, char c){
        for (int i = 0; i < 9; i++) {
            if (board[i][col] != '.' && board[i][col] == c)
                return false;
            if (board[row][i] != '.' && board[row][i] == c)
                return false;
            if (board[3*(row/3) + (i/3)][3*(col/3) + (i%3)] != '.' && board[3*(row/3) + (i/3)][3*(col/3) + (i%3)] == c)
                return false;
        }
        return true;
    }
}
