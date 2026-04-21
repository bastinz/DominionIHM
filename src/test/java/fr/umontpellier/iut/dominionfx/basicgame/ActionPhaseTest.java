package fr.umontpellier.iut.dominionfx.basicgame;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.StartTurnState;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.TreasurePhase;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.durations.TidePoolsState;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ActionPhaseTest extends BaseTestClass {

    @Override
    public void start(Stage stage) {
        game = new Game(new String[]{"PlayerTest1", "PlayerTest2"},
                        new String[]{"Bazaar", "Warehouse", "Fishing Village", "Sea Hag", "Blockade", "Tide Pools"});
        super.start(stage);
    }

    @Override
    public void setPlayersHands() {
        addToFirstPlayersHand("Sea Hag");
        addToFirstPlayersHand("Tide Pools");
        addToFirstPlayersHand("Sea Hag");
        addToFirstPlayersHand("Fishing Village");
        addToFirstPlayersHand("Blockade");
        addToFirstPlayersHand("Bazaar");
    }

    @Test
    public void moveToTreasureAfterAction() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Sea Hag");
        assertEquals(currentPlayer, game.currentPlayer());
        assertEquals(0, game.currentPlayer().getNumberOfActions());
        assertInstanceOf(TreasurePhase.class, currentPlayer.getCurrentState());
//        pause(2);
    }

    @Test
    public void remainInActionPhaseWhenMoreThanOneActionInPlay() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Bazaar");
        assertInstanceOf(StartTurnState.class, currentPlayer.getCurrentState());
        assertEquals(2, currentPlayer.getNumberOfActions());
        clickOnCardInHand("Sea Hag");
        assertEquals(1, currentPlayer.getNumberOfActions());
        assertInstanceOf(StartTurnState.class, currentPlayer.getCurrentState());
        clickOnCardInHand("Sea Hag");
        assertInstanceOf(TreasurePhase.class, currentPlayer.getCurrentState());
        assertEquals(currentPlayer, game.currentPlayer());
//        pause(2);
    }

    @Test
    public void durationRemainsInPlayTillNextTurn() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Fishing Village");
        clickOnSkip();
        assertTrue(listContainsCard(game.getPreviousTurnPlayer().getInPlay(), "Fishing Village"));
        clickOnSkip();
        assertTrue(listContainsCard(currentPlayer.getInPlay(), "Fishing Village"));// les cartes InPlay restent jusqu'au cleanUp
//        pause(2);
    }

    @Test
    public void durationTillNextTurnWhenMoreThanOnAction() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Fishing Village");
        clickOnCardInHand("Blockade");
        clickOnSupplyPile("Warehouse");
        clickOnSkip();
        assertTrue(listContainsCard(game.getPreviousTurnPlayer().getInPlay(), "Fishing Village"));
        assertTrue(listContainsCard(game.getPreviousTurnPlayer().getInPlay(), "Blockade"));
        clickOnSkip();
        assertTrue(listContainsCard(currentPlayer.getInPlay(), "Blockade"));
        assertTrue(listContainsCard(currentPlayer.getInPlay(), "Fishing Village"));
//        pause(2);
    }

    @Test
    public void handlesTwoDurationsSequentially() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Tide Pools");
        clickOnCardInHand("Blockade");
        clickOnSupplyPile("Warehouse");
        clickOnSkip();
        clickOnSkip();
        assertInstanceOf(TidePoolsState.class, currentPlayer.getCurrentState());
        clickOnFirstCardInHand();
        assertInstanceOf(TidePoolsState.class, currentPlayer.getCurrentState());
        clickOnFirstCardInHand();
        assertInstanceOf(StartTurnState.class, currentPlayer.getCurrentState());
//        pause(2);
    }
}
