package app;


public class MoveResult {

    private MoveType type;

    public MoveType getType() {
        return type;
    }

    private Piece piece;

    public Piece getPiece() {
        return piece;
    }

    public MoveResult(MoveType type) { //to avoid setting null from outside
        this(type, null);
    }

    public MoveResult(MoveType type, Piece piece) {
        this.type = type;
        this.piece = piece;
    }


}
