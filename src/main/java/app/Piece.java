package app;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static app.Board.board;
import static app.Board.coordinateToBoard;
import static app.CheckersApp.*;
import static app.Game.MOVE;


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
                result = new MoveResult(MoveType.NONE);
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
                    if ((this.getType().name() == "Black" && newY == 7) || (this.getType().name() == "White" && newY == 0)) {
                        Piece piece = new King(this.getType(), newX, newY);
                        board[newX][newY].setPiece(piece);
                        pieceGroup.getChildren().remove(this);
                        pieceGroup.getChildren().add(piece);
                    }
                    Game.changeTurns();

                    break;
                case JUMP:
                    this.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(this);

                    if ((this.getType().name() == "Black" && newY == 7) || (this.getType().name() == "White" && newY == 0)) {
                        Piece piece = new King(this.getType(), newX, newY);
                        board[newX][newY].setPiece(piece);
                        pieceGroup.getChildren().remove(this);
                        pieceGroup.getChildren().add(piece);
                    }

                    Piece killedPiece = result.getPiece();
                    board[coordinateToBoard(killedPiece.getOldX())][coordinateToBoard(killedPiece.getOldY())].setPiece(null);
                    CheckersApp.pieceGroup.getChildren().remove(killedPiece);
                    System.out.println(MOVE);
                    if (canJump(board[newX][newY].getPiece()).isEmpty()) Game.changeTurns();
                    System.out.println(MOVE);
                    break;
            }
        });
    }

    public MoveResult tryToMove(Piece piece, int newX, int newY) {
        if (board[newX][newY].hasPiece() || (newX + newY) % 2 == 0) {
            return new MoveResult(MoveType.NONE);
        }
        int x0 = coordinateToBoard(piece.getOldX());
        int y0 = coordinateToBoard(piece.getOldY());


        if (canJump(piece).isEmpty() && Math.abs(newX - x0) == 1 && newY - y0 == piece.getType().moveDirection && MOVE == piece.getType().name()) {

            //Game.changeTurns();
            return new MoveResult(MoveType.MOVE);
        }
        if (!canJump(piece).isEmpty()) {
            int x1 = x0 + (newX - x0) / 2;
            int y1 = y0 + (newY - y0) / 2;

            if (MOVE == piece.getType().name() && board[x1][y1].hasPiece()
                    && board[x1][y1].getPiece().getType() != piece.getType() && MOVE == piece.getType().name()) {
                //System.out.println(canJump(piece).toString());

                return new MoveResult(MoveType.JUMP, board[x1][y1].getPiece());

            }
        }
        return new MoveResult(MoveType.NONE);
    }

    public void move(int x, int y) {
        oldX = x * TILE_SIZE;
        oldY = y * TILE_SIZE;
        relocate(oldX, oldY);
    }


    public void abortMove() {
        relocate(oldX, oldY);
    }

    /*public static MoveResult tryToMoveStatic(Piece piece, int newX, int newY) {
        if (board[newX][newY].hasPiece() || (newX + newY) % 2 == 0) {
            return new MoveResult(MoveType.NONE);
        }
        int x0 = coordinateToBoard(piece.getOldX());
        int y0 = coordinateToBoard(piece.getOldY());


        if (/*!canJump(piece) && Math.abs(newX - x0) == 1 && newY - y0 == piece.getType().moveDirection /*&& MOVE == piece.getType().name()) {
            Game.changeTurns();
            return new MoveResult(MoveType.MOVE);
        }
        if (Math.abs(newX - x0) == 2) {
            int x1 = x0 + (newX - x0) / 2;
            int y1 = y0 + (newY - y0) / 2;

            if (MOVE == piece.getType().name() && board[x1][y1].hasPiece()
                    && board[x1][y1].getPiece().getType() != piece.getType()) {
                Game.changeTurns();
                return new MoveResult(MoveType.JUMP, board[x1][y1].getPiece());

            }
        }
        return new MoveResult(MoveType.NONE);
    }
*/

    public List canJump(Piece piece) {  //ПЕРЕДЕЛАТЬ
        List result = new ArrayList<Integer>();
        int[] directions = new int[]{-1, 1, -1, -1, 1, 1, 1, -1};
        /*-1,1,-1,-1,1,1,1,-1*/
        int x = coordinateToBoard(piece.getOldX());
        int y = coordinateToBoard(piece.getOldY());
        for (int j = 1; j < 8; j += 2) {
            if (x + 2 * directions[j - 1] > -1 && x + 2 * directions[j - 1] < 8
                    && y + 2 * directions[j] > -1 && y + 2 * directions[j] < 8) {
                System.out.println("Checkpoint 1");
                int newX = directions[j - 1];
                int newY = directions[j];
                if (board[x + newX][y + newY].hasPiece()) {
                    System.out.println("Checkpoint 2");
                    if (board[x + newX][y + newY].getPiece().getType() != piece.getType()
                            && !board[x + 2 * newX][y + 2 * newY].hasPiece()) {
                        System.out.println("Checkpoint 3");
                        result.add(x + 2 * newX);
                        result.add(y + 2 * newY);

                    }
                }
            }
        }

        return result;
        /*try {
            if (x + 1 != 8 && y + 1 != 8 && board[x + 1][y + 1].hasPiece() && board[x + 1][y + 1].getPiece().getType() != piece.getType()) {
                Game.changeTurns();
                return true;
            }
            if (x + 1 != 8 && y - 1 != -1 && board[x + 1][y - 1].hasPiece() && board[x + 1][y - 1].getPiece().getType() != piece.getType()) {
                Game.changeTurns();
                return true;
            }
            if (x - 1 != -1 && x + 1 != 8 && board[x - 1][y + 1].hasPiece() && board[x - 1][y + 1].getPiece().getType() != piece.getType()) {
                Game.changeTurns();
                return true;
            }
            if (x - 1 != -1 && y - 1 != -1 && board[x - 1][y - 1].hasPiece() && board[x - 1][y - 1].getPiece().getType() != piece.getType()) {
                Game.changeTurns();
                return true;
            }
        } catch (IndexOutOfBoundsException ignored) {
        }*/
    }

    /*public static boolean canMove(Piece piece) {
        int moveDir = piece.getType().moveDirection;
        int x = coordinateToBoard(piece.getOldX());
        int y = coordinateToBoard(piece.getOldY());
        if (y + moveDir < 7 && y + moveDir > -1) {
            return !board[x - 1][y + moveDir].hasPiece() || !board[x + 1][y + moveDir].hasPiece();
        }
        return false;
    }*/

}