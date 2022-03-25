package main.java.de.tum.in.ase.eist.Controller;

import javafx.scene.paint.Color;
import main.java.de.tum.in.ase.eist.Model.*;
import main.java.de.tum.in.ase.eist.Point2D;
import main.java.de.tum.in.ase.eist.Dimension2D;

import java.util.*;
import java.util.stream.Collectors;

public class GameBoard extends Subject<PlayerAction> {

    private Dimension2D size;
    private Point2D playerLocation;

    private long score;

    private Player player;
    private List<Bullet> playerBullets;
    private List<Bullet> alienBullets;
    private AlienSpaceship[][] aliensArray;
    private List<AlienSpaceship> aliensList; // also keep a list with references to the array for more idiomatic handling of game logic

    private KeyboardControls keyboardControls;
    private AudioPlayer audioPlayer;

    private Timer gameTickTimer;

    private boolean gameOver;
    private final int TICKS_PER_SCORE_INCREASE = 100; // 1 point per second
    private int scoreTimer = 0;

    private final int TICKS_PER_BULLET_SHOT = 50;
    private int shootingCooldownCounter = 0;

    public GameBoard(Dimension2D size, int nrAlienRows, int nrAlienCols) {
        this.size = size;

        this.aliensArray = new AlienSpaceship[nrAlienRows][nrAlienCols];
        this.aliensList = new ArrayList<>();

        this.player = new Player(
                new PlayerSpaceship(
                        new Point2D(this.size.getWidth() / 2, this.size.getHeight() - 40),
                        this.size));
        this.player.setLives(3);
        this.addObserver(player.getSpaceship());

        this.playerBullets = new ArrayList<>();
        this.alienBullets = new ArrayList<>();

        createAliens();

        playerLocation = player.getSpaceship().getPosition();

        this.score = 0;
        this.gameOver = false;
    }

    private void createAliens() {
        if (aliensArray.length == 0 || aliensArray[0].length == 0) {
            throw new IllegalArgumentException("You can't create zero aliens.");
        }

        // initialize starting positions of aliens
        for (int x = 0; x < aliensArray.length; x++) {
            for (int y = 0; y < aliensArray[x].length; y++) {
                aliensArray[x][y] = new AlienSpaceship(
                        new Point2D(20 + x * 30, 40 + y * 40),
                        getSize());
                aliensList.add(aliensArray[x][y]);
            }
        }
    }

    public void startGame() {
        this.gameTickTimer = new Timer();
        int millisPerTick = 10; // = 100 ticks/s

        shootingCooldownCounter = TICKS_PER_BULLET_SHOT;
        scoreTimer = TICKS_PER_SCORE_INCREASE;

        this.gameTickTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() { // this method runs on every game tick
                        player.getSpaceship().move();

                        // move bullets
                        playerBullets.forEach(Bullet::move);
                        alienBullets.forEach(Bullet::move);

                        alienBullets.stream()
                                .filter(bullet -> !bullet.isDestroyed())
                                .forEach(bullet -> {
                                    if (bullet.collidesWith(player.getSpaceship())) {
                                        if (player.getLives() == 0) {
                                            System.out.println("game over");
                                            gameOver = true;
                                            gameTickTimer.cancel();
                                        }
                                        player.loseLife();
                                        bullet.destroy();
                                    }
                                });

                        List<AlienSpaceship> aliveAliens = aliensList.stream().filter(alienSpaceship -> !alienSpaceship.isHit()).collect(Collectors.toList());
                        if (aliveAliens.size() <= 0) { // all aliens have been destroyed
                            score += 20;
                            for (AlienSpaceship alienSpaceship : aliensList) {
                                alienSpaceship.setHit(false);
                                alienSpaceship.resetPosition();
                                alienSpaceship.increaseSpeed();
                            }
                        } else {
                            aliveAliens.forEach(alienSpaceship -> {
                                alienSpaceship.move();
                                if (Math.random() < 0.0002) {
                                    alienBullets.add(new Bullet(alienSpaceship.getPosition(), Direction.DOWN, Color.ORANGE));
                                }

                                for (Bullet bullet : playerBullets) {
                                    if (bullet.collidesWith(alienSpaceship)) {
                                        alienSpaceship.setHit(true);
                                        score += 1;
                                    }
                                }

                                if (alienSpaceship.collidesWith(player.getSpaceship())) {
                                    if (player.getLives() == 0) {
                                        System.out.println("game over");
                                        gameOver = true;
                                        gameTickTimer.cancel();
                                    }

                                    player.loseLife();
                                    aliensList.forEach(AlienSpaceship::resetPosition);
                                }
                            });
                        }

                        // create new Bullets if player is shooting and cooldown timer is over
                        if (shootingCooldownCounter <= 0 && player.getSpaceship().isShooting()) {
                            playerBullets.add(new Bullet(player.getSpaceship().getPosition(), Direction.UP, Color.WHITE));
                            shootingCooldownCounter = TICKS_PER_BULLET_SHOT; // reset cooldown timer
                        } else if (shootingCooldownCounter > 0) {
                            shootingCooldownCounter--;
                        }

                        updateScore();

                        // allow pausing and resuming the game
                        if (false) { // stop game logic
                            gameTickTimer.cancel();
                        }
                    }
                },
                0, // start straight away
                millisPerTick
        );

        playMusic();
    }

    public void stopGame() {
        this.gameTickTimer.cancel();
        stopMusic();
    }

    public void playMusic() {
        this.audioPlayer.playBackgroundMusic();
    }

    public void stopMusic() {
        this.audioPlayer.stopBackgroundMusic();
    }

    public void chooseDifficulty() {
    }

    public void updateScore() {
        if (scoreTimer <= 0) {
            score += 1;
            scoreTimer = TICKS_PER_SCORE_INCREASE;
        } else {
            scoreTimer--;
        }
    }

    public KeyboardControls getKeyboardControls() {
        return keyboardControls;
    }

    public Point2D getPlayerLocation() {
        return playerLocation;
    }

    public Player getPlayer() {
        return player;
    }

    public void setAudioPlayer(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
    }

    public void setKeyboardControls(KeyboardControls keyboardControls) {
        this.keyboardControls = keyboardControls;
    }

    public long getScore() {
        return score;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public List<Bullet> getPlayerBullets() {
        return playerBullets;
    }

    public List<Bullet> getAlienBullets() {
        return alienBullets;
    }

    public AlienSpaceship[][] getAliensArray() {
        return aliensArray;
    }

    public List<AlienSpaceship> getAliensList() {
        return aliensList;
    }

    public Dimension2D getSize() {
        return this.size;
    }
}
