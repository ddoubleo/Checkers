package app;


import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import java.util.ArrayList;
import java.util.List;

import static app.Board.board;
import static app.Board.coordinateToBoard;
import static app.CheckersApp.TILE_SIZE;
import static app.Game.MOVE;

public class King extends Piece {  //Наследуется от Piece и переопределяет условия дивжения/взятия
    public King(PieceType type, int x, int y) {
        super(type, x, y);
        Ellipse RED = new Ellipse(TILE_SIZE * 0.1, TILE_SIZE * 0.07);
        RED.setFill(Color.RED);
        RED.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3 * 2) / 2);
        RED.setTranslateY(((TILE_SIZE - TILE_SIZE * 0.25 * 2) / 2));
        getChildren().addAll(RED);
    }

    @Override
    public MoveResult tryToMove(Piece king, int newX, int newY) {
        if (board[newX][newY].hasPiece() || (newX + newY) % 2 == 0) {
            return new MoveResult(MoveType.NONE);
        }
        int x0 = coordinateToBoard(king.getOldX());
        int y0 = coordinateToBoard(king.getOldY());


        if (Math.abs(newX - x0) == Math.abs(newY - y0)) {
            if (!jumpsAvailable(MOVE) && !pieceInLine(Integer.signum(newX - x0),
                    Integer.signum(newY - y0), x0, y0, newX, newY)) {
                Game.changeTurns();
                return new MoveResult(MoveType.MOVE);
            }
            int x1 = newX - Integer.signum(newX - x0);
            int y1 = newY - Integer.signum(newY - y0);

            if (jumpsAvailable(MOVE) && MOVE == king.getType().name() &&
                    board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != this.getType()) {
                Game.changeTurns();
                return new MoveResult(MoveType.JUMP, board[x1][y1].getPiece());
            }

        }

        return new MoveResult(MoveType.NONE);

    }


    @Override
    public List canJump(Piece king) {  //ПЕРЕДЕЛАТЬ
        List result = new ArrayList<Integer>();
        int[] directions = new int[]{-1, 1, -1, -1, 1, 1, 1, -1};
        /*-1,1,-1,-1,1,1,1,-1*/

        int x = coordinateToBoard(king.getOldX());
        int y = coordinateToBoard(king.getOldY());
        System.out.println((x));
        System.out.println((y));
        for (int j = 1; j < 8; j += 2) {

            for (int d1 = x, d2 = y; d1 < 8 && d2 < 8 && d1 > -1 && d2 > 0; d1 += directions[j - 1], d2 += directions[j]) {

                if (d1 + 2 * directions[j - 1] > -1 && d1 + 2 * directions[j - 1] < 8
                        && d2 + 2 * directions[j] > -1 && d2 + 2 * directions[j] < 8) {
                    //System.out.println("Checkpoint 1");
                    int newX = directions[j - 1];
                    int newY = directions[j];
                    if (board[d1 + newX][d2 + newY].hasPiece()) {
                        //System.out.println("Checkpoint 2");
                        if (board[d1 + newX][d2 + newY].getPiece().getType() != king.getType()
                                && !board[d1 + 2 * newX][d2 + 2 * newY].hasPiece()) {
                            result.add(d1 + 2 * newX);
                            result.add(d2 + 2 * newY);
                            System.out.println(result);

                        }

                    }


                }
            }


        }
        return result;
    }

    private boolean pieceInLine(int xDirection, int yDirection, int x0, int y0, int newX, int newY) {
        for (int i = x0 + xDirection, j = y0 + yDirection; i != newX && j != newY; i += xDirection, j += yDirection) {
            if (board[i][j].hasPiece()) return true;
        }
        return false;
    }


}



