package fr.umontpellier.iut.dominionfx.cards;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.TreasurePhase;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions.GhostShipState;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class GhostShipTest extends BaseTestClass {

    @Override
    public void start(Stage stage) {
        game = new Game(new String[]{"PlayerTest1", "PlayerTest2"},
                new String[]{"Ghost Ship", "Lighthouse"});
        super.start(stage);
    }

    @Override
    public void setPlayersHands() {
        addToFirstPlayersHand("Ghost Ship");
    }

    @Test
    public void addsTwoCardsToHand() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Ghost Ship");
        assertEquals(7,currentPlayer.getHand().size());
        assertInstanceOf(GhostShipState.class, currentPlayer.getCurrentState());
//        pause(2);
    }

    @Test
    public void otherPlayersDiscardUpTo3Cards() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Ghost Ship");
        assertInstanceOf(GhostShipState.class, currentPlayer.getCurrentState());
        clickOnTemporaryCard("Copper");
        assertInstanceOf(GhostShipState.class, currentPlayer.getCurrentState());
        clickOnTemporaryCard("Copper");
        assertInstanceOf(TreasurePhase.class, currentPlayer.getCurrentState());
        assertEquals(3,game.getPlayers().get(1).getHand().size());
//        pause(2);
    }

    @Test
    public void otherPlayersCannotSkipWhileDiscarding() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Ghost Ship");
        assertInstanceOf(GhostShipState.class, currentPlayer.getCurrentState());
        clickOnTemporaryCard("Copper");
        assertInstanceOf(GhostShipState.class, currentPlayer.getCurrentState());
        clickOnSkip();
        assertInstanceOf(GhostShipState.class, currentPlayer.getCurrentState());
        assertEquals(4,game.getPlayers().get(1).getHand().size());
//        pause(2);
    }
}
