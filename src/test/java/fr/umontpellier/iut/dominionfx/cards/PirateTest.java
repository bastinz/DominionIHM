package fr.umontpellier.iut.dominionfx.cards;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.StartTurnState;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

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

    @Test
    public void doesNotGetATreasureCostingMoreThan6() {
        // none in this game
//        pause(2);
    }

    @Disabled
    @Test
    void reactsToOtherPlayersGainedTreasure() {
        Player treasureGainer = game.getPlayers().get(1);
        Platform.runLater(() -> treasureGainer.incrementBuys(1));// pour rester sur ce joueur
        addToSecondPlayersHand("Gold");
        Player pirateOwner = game.currentPlayer();
        clickOnSkip();
        addToFirstPlayersHand("Pirate");
//        pause(2);
        clickOnTreasures();
//        System.out.println("SOOO on va acheter Gold");
        clickOnSupplyPile("Gold"); // achat et on passe à l'autre player
//        System.out.println("SOOO avant click pirate");
//        System.out.println("SOOO currentPlayer " + treasureGainer.getGame().currentPlayer().getName());
//        pause(2);
//        clickOnTemporaryCard("Pirate");
//        clickOnSupplyPile("Gold");
        assertTrue(pirateOwner.getInPlay().stream().map(Card::getName).toList().contains("Pirate"));
//        pause(5);
        // ligne 44 de pirate pour activer ou pas la réaction pour l'instant
    }
}