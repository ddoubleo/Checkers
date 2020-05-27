package app;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import static app.Board.coordinateToBoard;
import static app.CheckersApp.TILE_SIZE;
import static app.CheckersApp.WIDTH;
import static app.CheckersApp.HEIGHT;
import static app.Board.board;


public class Piece extends StackPane {
    public final PieceType type; //?


    public PieceType getType() {
        return type;
    }

    public double oldX, oldY;
    public double mouseX, mouseY;

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
        setOnMouseReleased(e -> {
            int newX = coordinateToBoard(this.getLayoutX());
            int newY = coordinateToBoard(this.getLayoutY());
            MoveResult result;
            if (newX < 0 || newY < 0 || newX >= WIDTH || newY >= HEIGHT) {
                result = new MoveResult(MovementType.NONE);
            } else {
                result = tryToMove(this, newX, newY);
            }

            int x0 = coordinateToBoard(oldX);
            int y0 = coordinateToBoard(oldY);

            switch (result.getType()) {
                case NONE:
                    this.abortMove();
                    break;
                case MOVE:
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(this);
                    this.move(newX, newY);
                    break;
                case JUMP:
                    this.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(this);

                    Piece killedPiece = result.getPiece();
                    board[coordinateToBoard(killedPiece.getOldX())][coordinateToBoard(killedPiece.getOldY())].setPiece(null);
                    CheckersApp.peiceGroup.getChildren().remove(killedPiece);
                    break;
            }
        });
    }

    public MoveResult tryToMove(Piece piece, int newX, int newY) {
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

    public void move(int x, int y) {
        oldX = x * TILE_SIZE;
        oldY = y * TILE_SIZE;
        relocate(oldX, oldY);
    }


    public void abortMove(){
        relocate(oldX,oldY);
    }

}