package main.java.de.tum.in.ase.eist;

import javafx.scene.image.Image;

import java.net.URL;

public class Resources {
    public static Image getImage(String file) {
        URL imageURL = App.class.getClassLoader().getResource(file);
        if (imageURL == null) {
            throw new IllegalArgumentException("Could not load the following file: \n" + file);
        }
        return new Image(imageURL.toExternalForm());
    }
}
