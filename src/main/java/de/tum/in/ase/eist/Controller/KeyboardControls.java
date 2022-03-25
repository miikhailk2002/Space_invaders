package main.java.de.tum.in.ase.eist.Controller;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.java.de.tum.in.ase.eist.Model.PlayerAction;

public class KeyboardControls {

    private GameBoard gameBoard;

    public KeyboardControls(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void keyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.A || e.getCode() == KeyCode.LEFT) {
            this.gameBoard.notifyObservers(PlayerAction.MOVE_LEFT);
        } else if (e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT) {
            this.gameBoard.notifyObservers(PlayerAction.MOVE_RIGHT);
        } else if (e.getCode() == KeyCode.W || e.getCode() == KeyCode.UP) {
            this.gameBoard.notifyObservers(PlayerAction.START_SHOOT);
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getCode() == KeyCode.A || e.getCode() == KeyCode.LEFT) {
            this.gameBoard.notifyObservers(PlayerAction.STOP_LEFT);
        } else if (e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT) {
            this.gameBoard.notifyObservers(PlayerAction.STOP_RIGHT);
        } else if (e.getCode() == KeyCode.W || e.getCode() == KeyCode.UP) {
            this.gameBoard.notifyObservers(PlayerAction.STOP_SHOOT);
        }
    }
}
