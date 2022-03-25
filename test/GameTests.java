import main.java.de.tum.in.ase.eist.Controller.AudioPlayer;
import main.java.de.tum.in.ase.eist.Controller.GameBoard;
import main.java.de.tum.in.ase.eist.Controller.Player;
import main.java.de.tum.in.ase.eist.Dimension2D;
import main.java.de.tum.in.ase.eist.Model.AlienSpaceship;
import main.java.de.tum.in.ase.eist.Model.PlayerSpaceship;
import main.java.de.tum.in.ase.eist.Point2D;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.net.MalformedURLException;
import java.net.URL;

import static main.java.de.tum.in.ase.eist.Model.PlayerAction.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameTests {


    @Test
    public void testAlienCreation() { // check if correct amount of aliens initialized in constructor
        GameBoard gameBoard = new GameBoard(
                new Dimension2D(20, 20),
                12, 3);
        Assertions.assertEquals(12 * 3, gameBoard.getAliensArray().length * gameBoard.getAliensArray()[0].length);
    }

    @Test
    public void testAlienCreationWithNoAliens() { // check if correct amount of aliens initialized in constructor

        Assertions.assertThrows(IllegalArgumentException.class, () -> new GameBoard(
                new Dimension2D(0, 20),
                0, 1));
    }

    @Test
    public void testGetSize() {
        Dimension2D size = new Dimension2D(50, 50);
        GameBoard gameBoard = new GameBoard(size, 12, 3);
        Assertions.assertEquals(size, gameBoard.getSize());
    }

    //  replace this test, because it depends too much on the environment the program is run
    @Test
    public void testValidFileConversion() throws MalformedURLException {//check URL conversion with valid URL
        AudioPlayer audioPlayer = new AudioPlayer();
        URL url = new URL("eist21t04e01-do07kartoffelpuffer/target/classes/backround1.wav");
        audioPlayer.setFileURL("backround1.wav");
        audioPlayer.convertNameToUrl(audioPlayer.getFileURL());
        Assertions.assertEquals(url.toExternalForm(), audioPlayer.getFileURL());
    }

    @Test

    public void testInvalidFile() {
        AudioPlayer audioPlayer = new AudioPlayer();
        Assertions.assertThrows(IllegalArgumentException.class, () -> audioPlayer.convertNameToUrl("url.toExternalForm()"));
    }


    @Test
    public void testplayerLosesLives() {
        GameBoard gameBoard = new GameBoard(new Dimension2D(20, 20), 12, 3);
        Player player = gameBoard.getPlayer();
        int expected = player.getLives() - 1;
        player.loseLife();
        int observed = player.getLives();
        assertEquals(expected, observed);
    }

    //TODO uncomment the next test
    /*
    @Test
    public void testCollidingWithplayer() {
        PlayerSpaceship playerSpaceship = new PlayerSpaceship(new Point2D(10.0, 10.0), new Dimension2D(50.0, 50.0));
        AlienSpaceship alienSpaceship = new AlienSpaceship(new Point2D(15.5, 5.5), new Dimension2D(50.0, 50.0));
        boolean observed = alienSpaceship.isCollidingWithPlayer(playerSpaceship);
        assertTrue(observed);
    }

     */

    @Test
    public void testResetPosition() {
        Point2D startPosition = new Point2D(15.5, 17.5);
        PlayerSpaceship playerSpaceship = new PlayerSpaceship(startPosition, new Dimension2D(50.0, 50.0));
        playerSpaceship.move();
        playerSpaceship.resetPosition();
        Point2D newPosition = playerSpaceship.getPosition();
        assertEquals(startPosition, newPosition);
    }

    @Test
    public void testAlienSpaceshipMovement() {
        AlienSpaceship alienSpaceship = new AlienSpaceship(new Point2D(29.0, 25.0), new Dimension2D(50.0, 50.0));
        alienSpaceship.move();
        Point2D expected = new Point2D(32.0, 25.0);
        Point2D observed = alienSpaceship.getPosition();
        assertEquals(expected, observed);
        alienSpaceship.move();
        expected = new Point2D(32.0, 45.0);
        observed = alienSpaceship.getPosition();
        assertEquals(expected, observed);
        alienSpaceship.move();
        expected = new Point2D(29.0, 45.0);
        observed = alienSpaceship.getPosition();
        assertEquals(expected, observed);
    }

    @Test
    public void testPlayerSpaceshipMovement() {
        PlayerSpaceship playerSpaceship = new PlayerSpaceship(new Point2D(30.0, 10.0), new Dimension2D(50.0, 50.0));
        playerSpaceship.update(MOVE_LEFT);
        playerSpaceship.move();
        Point2D expected = new Point2D(24.0, 10.0);
        Point2D observed = playerSpaceship.getPosition();
        assertEquals(expected, observed);
        playerSpaceship.update(STOP_LEFT);
        playerSpaceship.update(MOVE_RIGHT);
        playerSpaceship.move();
        expected = new Point2D(30.0, 10.0);
        observed = playerSpaceship.getPosition();
        assertEquals(expected, observed);
        playerSpaceship.move();
        //position darf sich hier nicht ändern
        observed = playerSpaceship.getPosition();
        assertEquals(expected, observed);
    }
}





