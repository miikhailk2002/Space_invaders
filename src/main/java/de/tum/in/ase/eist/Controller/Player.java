package main.java.de.tum.in.ase.eist.Controller;

import main.java.de.tum.in.ase.eist.Model.AlienSpaceship;
import main.java.de.tum.in.ase.eist.Model.PlayerSpaceship;

import java.util.AbstractList;

public class Player {
    private int lives;
    private PlayerSpaceship spaceship;

    public Player(PlayerSpaceship s) {
        spaceship = s;
    }

    public void notification() {
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void loseLife() {
        this.lives--;
    }

    public int getLives() {
        return lives;
    }

    public PlayerSpaceship getSpaceship() {
        return spaceship;
    }
}
