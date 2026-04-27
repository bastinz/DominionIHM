package fr.umontpellier.iut.dominionfx.cards;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.StartTurnState;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.TreasurePhase;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.*;

public class PirateTest extends BaseTestClass {

    @Override
    public void start(Stage stage) {
        game = new Game(new String[]{"PlayerTest1", "PlayerTest2"},
                new String[]{"Pirate"/*, "Astrolabe"*/});
        super.start(stage);
    }

    @Override
    public void setPlayersHands() {
        addToFirstPlayersHand("Pirate");
    }

    @Test
    public void getsATreasureCosting6OneNextTurn() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Pirate");
        clickOnSkip();
        clickOnSkip();
        assertFalse(currentPlayer.getHand().stream().map(Card::getName).toList().contains("Gold"));
        clickOnSupplyPile("Gold");
        assertTrue(currentPlayer.getHand().stream().map(Card::getName).toList().contains("Gold"));
        assertInstanceOf(StartTurnState.class, currentPlayer.getCurrentState());
//        pause(2);
    }

    @Disabled
    @Test
    public void doesNotGetATreasureCostingMoreThan6() {
        // none in this game
//        pause(2);
    }

    @Test
    void reactsToOtherPlayersGainedTreasureAndRemainsOnSamePlayer() {
        Player pirateOwner = game.getPlayers().get(0);
        Player treasureGainer = game.getPlayers().get(1);
        addToPlayerSHand(treasureGainer, "Gold");
//        addCardForNextTurn("Pirate");
        clickOnSkip();
        addToPlayerSHand(pirateOwner, "Pirate");
        Platform.runLater(() -> treasureGainer.incrementBuys(1));// pour rester sur ce joueur
        WaitForAsyncUtils.waitForFxEvents();
        clickOnTreasures();
        clickOnSupplyPile("Gold"); // achat qui va declencher Reaction
        clickOnTemporaryCard("Pirate"); // reponse reaction
        assertTrue(pirateOwner.getInPlay().stream().map(Card::getName).toList().contains("Pirate"));
        assertInstanceOf(TreasurePhase.class, game.currentPlayer().getCurrentState());
//        pause(2);
    }

    @Test
    void reactsToOtherPlayersGainedTreasureAndMovesToNextPlayer() {
        addToSecondPlayersHand("Gold");
        clickOnSkip();
        addToFirstPlayersHand("Pirate");
        clickOnTreasures();
        clickOnSupplyPile("Gold"); // achat qui va declencher Reaction
        clickOnTemporaryCard("Pirate"); // reponse reaction
        assertInstanceOf(StartTurnState.class, game.currentPlayer().getCurrentState());
//        pause(2);
    }

    @Disabled
    @Test
    void reactsWhenMoreThanTwoPlayers() {
    }

    @Disabled
    @Test
    void reactsWhenMoreThanTwoReactingCards() {
    }
}