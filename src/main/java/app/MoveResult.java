package app;

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

}
