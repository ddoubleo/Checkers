package app;


import static app.Board.board;
import static app.Board.coordinateToBoard;

public class MoveResult {

    private MovementType type;

    public MovementType getType() {
        return type;
    }

    private Piece piece;

    public Piece getPiece() {
        return piece;
    }

    public MoveResult(MovementType type) { //to avoid setting null from outside
        this(type, null);
    }

    public MoveResult(MovementType type, Piece piece) {
        this.type = type;
        this.piece = piece;
    }
    /*public static MoveResult tryToMove(Piece piece, int newX, int newY) {
        if (board[newX][newY].hasPiece() || (newX + newY) % 2 == 0) {
            return new MoveResult(MovementType.NONE);
        }
        int x0 = coordinateToBoard(piece.getOldX());
        int y0 = coordinateToBoard(piece.getOldY());


        if (Math.abs(newX - x0) == 1 && newY - y0 == piece.getType().moveDirection) {
            return new MoveResult(MovementType.MOVE);
        }
        if (Math.abs(newX - x0) == 2) {
            System.out.println(newY-y0);
            int x1 = x0 + (newX - x0) / 2;
            int y1 = y0 + (newY - y0) / 2;

            if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != piece.getType()) {
                return new MoveResult(MovementType.JUMP, board[x1][y1].getPiece());

            }
        }
        return new MoveResult(MovementType.NONE);
    }
*/

}
