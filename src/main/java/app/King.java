package app;


import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import static app.Board.board;
import static app.Board.coordinateToBoard;
import static app.CheckersApp.TILE_SIZE;

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
    public MoveResult tryToMove(Piece piece, int newX, int newY) {
        if (board[newX][newY].hasPiece() || (newX + newY) % 2 == 0) {
            return new MoveResult(MoveType.NONE);
        }
        int x0 = coordinateToBoard(piece.getOldX());
        int y0 = coordinateToBoard(piece.getOldY());


        if (Math.abs(newX - x0) == Math.abs(newY - y0)) {
            if (!pieceInLine(Integer.signum(newX - x0), Integer.signum(newY - y0), x0, y0, newX, newY))
                return new MoveResult(MoveType.MOVE);
            int x1 = newX - (newX - x0) / Math.abs(newX - x0);
            int y1 = newY - (newY - y0) / Math.abs(newY - y0);
            if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != this.getType()) {
                return new MoveResult(MoveType.JUMP, board[x1][y1].getPiece());
            }

        }

        return new MoveResult(MoveType.NONE);

    }

    private boolean pieceInLine(int xDirection, int yDirection, int x0, int y0, int newX, int newY) {
        for (int i = x0 + xDirection, j = y0 + yDirection; i != newX && j != newY; i += xDirection, j += yDirection) {
            if (board[i][j].hasPiece()) return true;
        }
        return false;
    }

}


