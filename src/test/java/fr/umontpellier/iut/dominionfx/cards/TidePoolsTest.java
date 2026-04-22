package fr.umontpellier.iut.dominionfx.cards;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.StartTurnState;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class TidePoolsTest extends BaseTestClass {

    @Override
    public void start(Stage stage) {
        game = new Game(new String[]{"PlayerTest1", "PlayerTest2"},
                new String[]{"Tide Pools", "Warehouse", "Treasury", "Bazaar", "Blockade"});
        super.start(stage);
    }

    @Override
    public void setPlayersHands() {
        addToFirstPlayersHand("Tide Pools");
        addToFirstPlayersHand("Blockade");
    }

    @Test
    public void discardTwoCardsOnNextTurn() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Tide Pools");
        clickOnSkip();
        clickOnSkip();
        clickOnFirstCardInHand();
        assertEquals(4, currentPlayer.getCardsInHand().size());
        clickOnFirstCardInHand();
        assertEquals(3, currentPlayer.getCardsInHand().size());
        assertInstanceOf(StartTurnState.class, currentPlayer.getCurrentState());
//        pause(2);
    }

    @Test
    public void discardOneCardOnNextTurn() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Tide Pools");
        clickOnSkip();
        clickOnSkip();
        clickOnFirstCardInHand();
        assertEquals(4, currentPlayer.getCardsInHand().size());
        clickOnSkip();
        assertEquals(4, currentPlayer.getCardsInHand().size());
        assertInstanceOf(StartTurnState.class, currentPlayer.getCurrentState());
//        pause(2);
    }

    @Test
    public void twoDurationsAndOneCardOnNextTurn() {
        Player currentPlayer = game.currentPlayer();
        int initialNumberOfCardsInHand = currentPlayer.getCardsInHand().size();
        clickOnCardInHand("Tide Pools"); // -1 carte Tide Pools + 3 cards = +2
        clickOnCardInHand("Blockade"); // -1 carte Blockade
        clickOnSupplyPile("Warehouse");
        assertEquals(initialNumberOfCardsInHand + 1, currentPlayer.getCardsInHand().size());
        clickOnSkip();
        clickOnSkip();
        assertEquals(5, currentPlayer.getCardsInHand().size());
        clickOnFirstCardInHand();
        assertEquals(4, currentPlayer.getCardsInHand().size());
        clickOnFirstCardInHand(); // -1 discard +1 carte Warehouse
        assertEquals(3, currentPlayer.getCardsInHand().size());
//        pause(2);
    }

}
