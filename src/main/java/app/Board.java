package app;

import static app.CheckersApp.HEIGHT;
import static app.CheckersApp.WIDTH;
import static app.CheckersApp.TILE_SIZE;

public class Board {
    public static final Tile[][] board = new Tile[WIDTH][HEIGHT];
    public static int coordinateToBoard(double pixel) {
        return (int) ((pixel + TILE_SIZE * 0.5) / TILE_SIZE);
    }
}
