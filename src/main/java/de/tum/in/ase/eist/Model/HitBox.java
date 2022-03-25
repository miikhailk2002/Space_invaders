package main.java.de.tum.in.ase.eist.Model;

public interface HitBox {

    public int getWidth();

    public int getHeight();

    public double getTop();

    public double getRight();

    public double getBottom();

    public double getLeft();

    public default boolean collidesWith(HitBox otherHitBox) {
        // check if the hitboxes intersect
        boolean dontIntersect = this.getLeft() > otherHitBox.getRight()
                || this.getRight() < otherHitBox.getLeft()
                || this.getTop() > otherHitBox.getBottom()
                || this.getBottom() < otherHitBox.getTop();

        return !dontIntersect;
    }
}
