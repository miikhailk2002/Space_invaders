package main.java.de.tum.in.ase.eist.Model;

import main.java.de.tum.in.ase.eist.Dimension2D;
import main.java.de.tum.in.ase.eist.Point2D;

public class PlayerSpaceship extends Spaceship implements Observer<PlayerAction> {
    private boolean movingLeft;
    private boolean movingRight;
    private boolean shooting;

    public PlayerSpaceship(Point2D startingPos, Dimension2D gameBoardDimension) {
        super(startingPos, gameBoardDimension);
        this.setIconLocation("player.png");
        this.speed = 6;
        this.shooting = false;
    }

    @Override
    public void move() {
        if (this.movingLeft && this.position.getX() > 0) {
            this.position.setX(this.position.getX() - this.speed);
        } else if (this.movingRight && this.position.getX() < gameBoardDimension.getWidth() - this.width) {
            this.position.setX(this.position.getX() + this.speed);
        }
    }

    @Override
    public void destroy(BulletCollision collision) {

    }

    @Override
    public void update(PlayerAction action) {
        switch (action) {
            case MOVE_LEFT:
                this.movingLeft = true;
                break;
            case STOP_LEFT:
                this.movingLeft = false;
                break;
            case MOVE_RIGHT:
                this.movingRight = true;
                break;
            case STOP_RIGHT:
                this.movingRight = false;
                break;
            case START_SHOOT:
                this.shooting = true;
                break;
            case STOP_SHOOT:
                this.shooting = false;
                break;
        }
    }

    public boolean isShooting() {
        return shooting;
    }
}
