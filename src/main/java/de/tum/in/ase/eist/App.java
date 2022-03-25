package main.java.de.tum.in.ase.eist;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.de.tum.in.ase.eist.View.GameBoardUI;

public class App extends Application {
    private GameBoardUI ui = new GameBoardUI();

    public static void main(String[] args) {
        System.out.println("Well then...");
        launch();
    }

    @Override
    public void start(Stage stage) {
        this.ui.setup();
        this.ui.start(stage);
    }

    @Override
    public void stop() throws Exception {
        this.ui.stop();
    }
}
