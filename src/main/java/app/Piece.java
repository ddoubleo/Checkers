package app;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import static app.CheckersApp.TILE_SIZE;

public class Piece extends StackPane {
    private final PieceType type;


    private double oldX, oldY;
    private double mouseX, mouseY;

    public PieceType getType() {
        return type;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public Piece(PieceType type, int x, int y) {
        this.type = type;
        move(x, y);
        Ellipse bg = new Ellipse(TILE_SIZE * 0.3, TILE_SIZE * 0.25);
        if (type == PieceType.Black) {
            bg.setFill(Color.BLACK);
        } else {
            bg.setFill(Color.WHITE);
            bg.setStroke(Color.BLACK);
            bg.setStrokeWidth(TILE_SIZE * 0.015);
        }
        bg.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3 * 2) / 2);
        bg.setTranslateY(((TILE_SIZE - TILE_SIZE * 0.25 * 2) / 2));
        getChildren().addAll(bg);

        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();

        });

        setOnMouseDragged(e -> {
            relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
        });

    }

    public void move(int x, int y) {
        oldX = x * TILE_SIZE;
        oldY = y * TILE_SIZE;
        relocate(oldX, oldY);
    }


    public void abortMove() {
        relocate(oldX, oldY);
    }
}