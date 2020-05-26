package app;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {
    private Piece piece;

    public boolean hasPiece() {
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Tile(boolean color, int x, int y) { //color = 1 -> dark  color = 0 -> light
        setWidth(CheckersApp.TILE_SIZE);
        setHeight(CheckersApp.TILE_SIZE);
        relocate(x * CheckersApp.TILE_SIZE, y * CheckersApp.TILE_SIZE);
        if (color) {
            setFill(Color.valueOf("#7B7D7D"));
        } else {
            setFill(Color.valueOf("#F7F9F9"));
        }
    }
}
