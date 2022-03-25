package main.java.de.tum.in.ase.eist.Model;

import main.java.de.tum.in.ase.eist.Dimension2D;
import main.java.de.tum.in.ase.eist.Point2D;

public class AlienSpaceship extends Spaceship implements Observer<Boolean> {
    private boolean movingLeft;
    private boolean movingRight;

    public AlienSpaceship(Point2D position, Dimension2D gameBoardDimension) {
        super(position, gameBoardDimension);
        this.setIconLocation("alien.png");
        this.movingRight = true;
        this.movingLeft = false;

        this.speed = 2;
    }

    @Override
    public void move() {
        if (this.movingRight) {
            if (position.getX() < gameBoardDimension.getWidth() - this.width) {
                this.position.setX(this.position.getX() + this.speed);
            } else {
                this.movingRight = false;
                this.movingLeft = true;
                this.position.setY(this.position.getY() + this.height);
            }
        } else if (this.movingLeft) {
            if (this.position.getX() > 0) {
                this.position.setX(this.position.getX() - this.speed);
            } else {
                this.movingRight = true;
                this.movingLeft = false;
                this.position.setY(this.position.getY() + this.height);
            }
        } else {
            this.position.setX(this.position.getX() + this.speed);
        }
    }

    @Override
    public void resetPosition() {
        super.resetPosition();
        this.movingRight = true;
        this.movingLeft = false;
    }

    @Override
    public void destroy(BulletCollision collision) {
        if (collision.detectCollision()) {
            this.setHit(true);
        }
    }

    @Override
    public void update(Boolean newState) {

    }

    public void increaseSpeed() {
        this.speed += 1;
    }
}
