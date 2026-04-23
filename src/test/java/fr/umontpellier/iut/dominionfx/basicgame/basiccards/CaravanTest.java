package fr.umontpellier.iut.dominionfx.basicgame.basiccards;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.StartTurnState;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class CaravanTest extends BaseTestClass {

    @Override
    public void start(Stage stage) {
        game = new Game(new String[]{"PlayerTest1", "PlayerTest2"},
                new String[]{"Caravan"});
        super.start(stage);
    }

    @Override
    public void setPlayersHands() {
        addToFirstPlayersHand("Caravan");
    }

    @Test
    public void incrementsActionAndHandOnCurrentTurn() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Caravan");
        assertEquals(1, currentPlayer.getNumberOfActions());
        assertEquals(4, currentPlayer.getDraw().size());
        assertEquals(6, currentPlayer.getHand().size());
        assertInstanceOf(StartTurnState.class, currentPlayer.getCurrentState());
//        pause(2);
    }

    @Test
    public void incrementsHandOnNextTurn() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Caravan");
        clickOnSkip();
        clickOnSkip();
        assertEquals(6, currentPlayer.getHand().size());
        assertInstanceOf(StartTurnState.class, currentPlayer.getCurrentState());
//        pause(2);
    }
}
