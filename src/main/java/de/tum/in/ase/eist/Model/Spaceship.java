package main.java.de.tum.in.ase.eist.Model;

import main.java.de.tum.in.ase.eist.Dimension2D;
import main.java.de.tum.in.ase.eist.Point2D;

import javafx.scene.image.Image;
import main.java.de.tum.in.ase.eist.Resources;

public abstract class Spaceship implements HitBox {
    private boolean isHit;
    private String iconLocation;
    protected Point2D position;
    protected Point2D startingPosition;
    protected int speed;
    protected Dimension2D gameBoardDimension;

    protected int width;
    protected int height;

    public Spaceship(Point2D startingPosition, Dimension2D gameBoardDimension) {
        this.position = new Point2D(startingPosition.getX(), startingPosition.getY());
        this.startingPosition = startingPosition;
        this.gameBoardDimension = gameBoardDimension;
        this.width = 20;
        this.height = 20;
    }

    public abstract void move();

    protected void setIconLocation(String iconLocation) {
        if (iconLocation == null) {
            throw new NullPointerException("The chassis image of a car cannot be null.");
        }
        this.iconLocation = iconLocation;
    }

    public Point2D getPosition() {
        return position;
    }

    public void resetPosition() {
        this.position.setX(this.startingPosition.getX());
        this.position.setY(this.startingPosition.getY());
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        this.isHit = hit;
    }

    public Image getIcon() {
        return Resources.getImage(iconLocation);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getTop() {
        return position.getY();
    }

    public double getRight() {
        return position.getX() + this.width;
    }

    public double getBottom() {
        return position.getY() + this.height;
    }

    public double getLeft() {
        return position.getX();
    }

    public abstract void destroy(BulletCollision collision);
}
