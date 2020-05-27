package app;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



public class CheckersApp extends Application {
    public static final int TILE_SIZE = 80;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    private Group tileGroup = new Group();
    public static Group peiceGroup = new Group();

    //private Tile[][] board = new Tile[WIDTH][HEIGHT];

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.getChildren().addAll(tileGroup, peiceGroup);
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = new Tile((x + y) % 2 == 1, x, y);
                Board.board[x][y] = tile;
                tile.setStroke(Color.BLACK);

                tileGroup.getChildren().add(tile);

                Piece piece = null;
                if (y <= 2 && (x + y) % 2 != 0) {
                    piece = new King(PieceType.Black,x,y);
                }
                if (y >= 5 && (x + y) % 2 != 0) {
                    piece = new King(PieceType.White,x,y);
                }
                if (piece != null) {
                    tile.setPiece(piece);
                    peiceGroup.getChildren().addAll(piece);
                }
            }
        }

        return root;
    }

    private Piece makePiece(PieceType type, int x, int y) {
        Piece piece = new Piece(type, x, y);

        /*piece.setOnMouseReleased(e -> {
            int newX = coordinateToBoard(piece.getLayoutX());
            int newY = coordinateToBoard(piece.getLayoutY());
            MoveResult result;
            if (newX < 0 || newY < 0 || newX >= WIDTH || newY >= HEIGHT) {
                result = new MoveResult(MovementType.NONE);
            } else {
                result = tryToMove(piece, newX, newY);
            }

            int x0 = coordinateToBoard(piece.getOldX());
            int y0 = coordinateToBoard(piece.getOldY());

            switch (result.getType()) {
                case NONE:
                    piece.abortMove();
                    break;
                case MOVE:
                    Board.board[x0][y0].setPiece(null);
                    Board.board[newX][newY].setPiece(piece);
                    piece.move(newX, newY);
                    break;
                case JUMP:
                    piece.move(newX, newY);
                    Board.board[x0][y0].setPiece(null);
                    Board.board[newX][newY].setPiece(piece);

                    Piece killedPiece = result.getPiece();
                    Board.board[coordinateToBoard(killedPiece.getOldX())][coordinateToBoard(killedPiece.getOldY())].setPiece(null);
                    peiceGroup.getChildren().remove(killedPiece);
                    break;
            }
        });
*/
        return piece;
    }

    /*private MoveResult tryToMove(Piece piece, int newX, int newY) {
        if (board[newX][newY].hasPiece() || (newX + newY) % 2 == 0) {
            return new MoveResult(MovementType.NONE);
        }
        int x0 = coordinateToBoard(piece.getOldX());
        int y0 = coordinateToBoard(piece.getOldY());

        System.out.println(x0);
        System.out.println(y0);
        System.out.println(newX);
        System.out.println(newY);

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
    } */

    private int coordinateToBoard(double pixel) {
        return (int) ((pixel + TILE_SIZE * 0.5) / TILE_SIZE);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        primaryStage.setScene(scene);
        primaryStage.setTitle("ChEcKeRs");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
