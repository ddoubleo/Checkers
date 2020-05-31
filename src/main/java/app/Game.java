package app;

import static app.Board.board;


public class Game {
    public static String MOVE = "White";
    public static boolean isRunning = true;


    public static void changeTurns() {
        if (MOVE.equals("White")) MOVE = "Black";
        else MOVE = "White";
    }

}
