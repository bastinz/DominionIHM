package fr.umontpellier.iut.dominionfx.basicgame.basiccards;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.StartTurnState;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.TreasurePhase;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class AstrolabeTest extends BaseTestClass {

    @Override
    public void start(Stage stage) {
        game = new Game(new String[]{"PlayerTest1", "PlayerTest2"},
                new String[]{"Astrolabe"});
        super.start(stage);
    }

    @Override
    public void setPlayersHands() {
        addToFirstPlayersHand("Astrolabe");
    }

    @Test
    public void incrementsBuysOnCurrentTurn() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Astrolabe");
        assertEquals(2, currentPlayer.getNumberOfBuys());
        assertInstanceOf(TreasurePhase.class, currentPlayer.getCurrentState());
//        pause(2);
    }

    @Test
    public void incrementsBuysOnNextTurn() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Astrolabe");
        clickOnSkip();
        clickOnSkip();
        assertEquals(2, currentPlayer.getNumberOfBuys());
        assertInstanceOf(StartTurnState.class, currentPlayer.getCurrentState());
//        pause(2);
    }
}
