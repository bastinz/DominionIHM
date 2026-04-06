package fr.umontpellier.iut.dominionJavaFX;

import fr.umontpellier.iut.dominionJavaFX.dominion.Game;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class GameService extends Service<Void> {
    private final Game game;

    public GameService(Game jeu) {
        this.game = jeu;
    }

    @Override
    protected Task<Void> createTask() {
        return game;
    }

}