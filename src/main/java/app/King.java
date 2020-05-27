package app;


import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import static app.Board.board;
import static app.Board.coordinateToBoard;
import static app.CheckersApp.*;

public class King extends Piece{
    public King(PieceType type,int x,int y){
        super(type,x,y);

    }

    @Override
    public MoveResult tryToMove(Piece piece, int newX, int newY) {
        if (board[newX][newY].hasPiece() || (newX + newY) % 2 == 0) {
            return new MoveResult(MovementType.NONE);
        }
        int x0 = coordinateToBoard(piece.getOldX());
        int y0 = coordinateToBoard(piece.getOldY());

        /*if (Math.abs(newX - x0) == 2) {
            System.out.println(newY-y0);
            int x1 = x0 + (newX - x0) / 2;
            int y1 = y0 + (newY - y0) / 2;

            if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != piece.getType()) {
                return new MoveResult(MovementType.JUMP, board[x1][y1].getPiece());

            }
        }*/

        /*if (Math.abs(newX-x0)==Math.abs(newY-y0)) {
            if
            return new MoveResult(MovementType.MOVE);
        }*/

        return new MoveResult(MovementType.NONE);

    }

}
