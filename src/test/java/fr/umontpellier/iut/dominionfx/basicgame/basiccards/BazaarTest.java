package fr.umontpellier.iut.dominionfx.basicgame.basiccards;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.StartTurnState;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class BazaarTest extends BaseTestClass {

    @Override
    public void start(Stage stage) {
        game = new Game(new String[]{"PlayerTest1", "PlayerTest2"},
                new String[]{"Bazaar"});
        super.start(stage);
    }

    @Override
    public void setPlayersHands() {
        addToFirstPlayersHand("Bazaar");
    }

    @Test
    public void incrementsMoneyActionsAndHandOnCurrentTurn() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Bazaar");
        assertEquals(1, currentPlayer.getMoney());
        assertEquals(2, currentPlayer.getNumberOfActions());
        assertEquals(4, currentPlayer.getDraw().size());
        assertEquals(6, currentPlayer.getHand().size());
        assertInstanceOf(StartTurnState.class, currentPlayer.getCurrentState());
//        pause(2);
    }

}
