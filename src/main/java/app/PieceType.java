package app;

public enum PieceType {
    Black(1), White(-1);

    final int moveDirection;

    PieceType(int moveDirection) {
        this.moveDirection = moveDirection;
    }

}
