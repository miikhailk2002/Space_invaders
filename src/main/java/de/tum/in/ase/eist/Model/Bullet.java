package main.java.de.tum.in.ase.eist.Model;

import javafx.scene.paint.Color;
import main.java.de.tum.in.ase.eist.Point2D;

public class Bullet implements HitBox {
    private Color color;
    private int speed;

    private int width;
    private int height;

    private boolean destroyed;

    private Point2D position;
    private Direction direction;

    public Bullet(Point2D position, Direction direction, Color color) {
        this.position = new Point2D(position.getX(), position.getY());
        this.direction = direction;
        this.speed = 5;
        this.color = color;
        this.width = 2;
        this.height = 10;
        this.destroyed = false;
    }

    public void move() {
        if (this.direction == Direction.UP) {
            this.position.setY(this.position.getY() - this.speed);
        } else if (this.direction == Direction.DOWN) {
            this.position.setY(this.position.getY() + this.speed);
        }
    }

    public void destroy() {
        this.destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public Color getColor() {
        return color;
    }

    public Point2D getPosition() {
        return position;
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
}
