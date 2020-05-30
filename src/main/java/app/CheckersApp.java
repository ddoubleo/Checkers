package app;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class CheckersApp extends Application {
    public static final int TILE_SIZE = 80;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;


    private Group tileGroup = new Group();
    public static Group pieceGroup = new Group();


    private Parent createBoard() {
        Pane root = new Pane();
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.getChildren().addAll(tileGroup, pieceGroup);
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = new Tile((x + y) % 2 == 1, x, y);
                Board.board[x][y] = tile;
                tile.setStroke(Color.BLACK);

                tileGroup.getChildren().add(tile);

                Piece piece = null;
                if (y <= 2 && (x + y) % 2 != 0) {
                    piece = new Piece(PieceType.Black, x, y);
                }
                if (y >= 5 && (x + y) % 2 != 0) {
                    piece = new Piece(PieceType.White, x, y);
                }
                if (piece != null) {
                    tile.setPiece(piece);
                    pieceGroup.getChildren().addAll(piece);
                }
            }
        }
        return root;
    }


    @Override
    public void start(Stage primaryStage) throws Exception { //ADD TOP MENU
        Image bgMenuImage = new Image("mainBG.png");
        ImageView bgMenu = new ImageView(bgMenuImage);

        VBox mainMenu = new VBox(7);

        Button start = new Button("Start game");
        Button exit = new Button("Exit game");

        mainMenu.setAlignment(Pos.CENTER);
        mainMenu.getChildren().addAll(start, exit);
        StackPane menuPane = new StackPane();
        menuPane.getChildren().addAll(bgMenu, mainMenu);
        Scene primaryScene = new Scene(menuPane);
        primaryStage.setScene(primaryScene);

        primaryStage.setWidth(800);
        primaryStage.setHeight(800);
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(300);
        primaryStage.setMaxWidth(800);
        primaryStage.setMaxHeight(800);
        primaryStage.setTitle("Checkers");
        primaryStage.show();

        exit.setOnAction(e -> {
            Button source = (Button) e.getSource();
            primaryStage.hide();
        });


        start.setOnAction(e -> {
            Button source = (Button) e.getSource();
            primaryStage.hide();

            Stage board = new Stage();
            Scene scene = new Scene(createBoard());
            board.setScene(scene);
            board.setTitle("ChEcKeRs");
            board.setResizable(false);
            board.show();
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
