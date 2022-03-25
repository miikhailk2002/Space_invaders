package main.java.de.tum.in.ase.eist.View;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.java.de.tum.in.ase.eist.Controller.AudioPlayer;
import main.java.de.tum.in.ase.eist.Controller.KeyboardControls;
import main.java.de.tum.in.ase.eist.Dimension2D;
import main.java.de.tum.in.ase.eist.Model.AlienSpaceship;
import javafx.animation.AnimationTimer;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import main.java.de.tum.in.ase.eist.Controller.GameBoard;
import main.java.de.tum.in.ase.eist.Model.Observer;
import main.java.de.tum.in.ase.eist.Model.PlayerAction;
import main.java.de.tum.in.ase.eist.Resources;

public class GameBoardUI implements Observer<PlayerAction> {
    private GameBoard gameBoard;
    private Dimension2D gameBoardDimension;
    private final String BACKGROUND = "jeremy-thomas-E0AHdsENmDg-unsplash.jpg";

    private Scene gameScene;
    private Scene menuScene; // (ignore for now) implement menu and show it on start instead of game

    private GraphicsContext gc;

    private AnimationTimer drawTimer;

    public GameBoardUI() {
        this.gameBoardDimension = new Dimension2D(500, 500);
        this.gameBoard = new GameBoard(this.gameBoardDimension, 12, 3);
        this.gameBoard.setAudioPlayer(new AudioPlayer());
        this.gameBoard.setKeyboardControls(new KeyboardControls(this.gameBoard));
        this.gameBoard.addObserver(this);

        //  (ignore for now) make canvas resizeable
        Canvas canvas = new Canvas();
        canvas.setWidth(this.gameBoardDimension.getWidth());
        canvas.setHeight(this.gameBoardDimension.getHeight());
        this.gc = canvas.getGraphicsContext2D();

        Group group = new Group();
        group.getChildren().add(canvas);

        StackPane stackPane = new StackPane();
        stackPane.setBackground(createBackground());
        stackPane.getChildren().add(group);

        this.gameScene = new Scene(stackPane, this.gameBoardDimension.getWidth(), this.gameBoardDimension.getHeight());
        this.gameScene.setOnKeyPressed(e -> this.gameBoard.getKeyboardControls().keyPressed(e));
        this.gameScene.setOnKeyReleased(e -> this.gameBoard.getKeyboardControls().keyReleased(e));
    }

    public void start(Stage stage) {
        stage.setScene(this.gameScene);
        stage.setTitle("TUM Space Invaders");
        stage.setResizable(false);
        stage.show();

        // use AnimationTimer instead of a loop so that draw() can be invoked on every frame
        this.drawTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                draw(); // draw to screen
            }
        };

        gameBoard.startGame(); // start game logic
        drawTimer.start(); // start drawing to screen
    }

    public void stop() {
        gameBoard.stopGame();
        this.drawTimer.stop();
    }

    private Background createBackground() {
        return new Background(new BackgroundImage(Resources.getImage(BACKGROUND),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                new BackgroundPosition(Side.LEFT, 0, true, Side.BOTTOM, 0, true),
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true)));
    }

    public void draw() {
        // clear the playing field of all drawn shapes
        this.gc.clearRect(0, 0, this.gameBoardDimension.getWidth(), this.gameBoardDimension.getHeight());

        // aliens
        gameBoard.getAliensList().stream().filter(alienSpaceship -> !alienSpaceship.isHit())
                .forEach(alien -> gc.drawImage(alien.getIcon(), alien.getPosition().getX(), alien.getPosition().getY()));

        // draw the player
        gc.drawImage(this.gameBoard.getPlayer().getSpaceship().getIcon(),
                this.gameBoard.getPlayerLocation().getX(),
                this.gameBoard.getPlayerLocation().getY());

        // draw bullets
        this.gameBoard.getPlayerBullets().forEach(bullet -> {
            gc.setStroke(bullet.getColor());
            gc.setFill(bullet.getColor());
            gc.fillRect(
                    bullet.getPosition().getX(),
                    bullet.getPosition().getY(),
                    bullet.getWidth(),
                    bullet.getHeight());
        });
        this.gameBoard.getAlienBullets().stream()
                .filter(bullet -> !bullet.isDestroyed())
                .forEach(bullet -> {
                    gc.setStroke(bullet.getColor());
                    gc.setFill(bullet.getColor());
                    gc.fillRect(
                            bullet.getPosition().getX(),
                            bullet.getPosition().getY(),
                            bullet.getWidth(),
                            bullet.getHeight());
                });

        // black backdrop
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, gameBoardDimension.getWidth(), 30);

        // score
        gc.setStroke(Color.WHITE);
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Comic Sans MS",20));
        gc.fillText("score: " + this.gameBoard.getScore(), 10, 20);

        // lives
        for (int i = 0; i < gameBoard.getPlayer().getLives(); i++) {
            gc.drawImage(
                    Resources.getImage("heart.png"),
                    this.gameBoardDimension.getWidth() - 30 * i - 30,
                    5);
        }

        if (gameBoard.isGameOver()) {
            // black backdrop
            gc.setStroke(Color.BLACK);
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 235, gameBoardDimension.getWidth(), 80);

            // game over text
            gc.setStroke(Color.WHITE);
            gc.setFill(Color.WHITE);
            gc.setFont(new Font(50));
            gc.fillText("GAME OVER" , 100, 300);
        }
    }

    public void setup() {
    }

    @Override
    public void update(PlayerAction action) {
    }
}
