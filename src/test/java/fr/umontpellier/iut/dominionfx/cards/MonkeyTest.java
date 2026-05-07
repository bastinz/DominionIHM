package fr.umontpellier.iut.dominionfx.cards;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MonkeyTest extends BaseTestClass {

    @Override
    public void start(Stage stage) {
        game = new Game(new String[]{"PlayerTest1", "PlayerTest2"},
                new String[]{"Monkey", "Lighthouse", "Pirate"});
        super.start(stage);
    }

    @Override
    public void setPlayersHands() {
        addToFirstPlayersHand("Monkey");
    }

    @Test
    public void increasesNumberOfCardsWhenOtherPlayerGains() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Monkey");
        clickOnSkip();
        clickOnTreasures();
        assertEquals(5, currentPlayer.getHand().size());
        clickOnSupplyPile("Lighthouse");
        assertEquals(7, currentPlayer.getHand().size());
//        pause(2);
    }

    @Test
    public void doesNotIncreaseNumberOfCardsIfPlayerDoesNotGain() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Monkey");
        clickOnSkip();
        assertEquals(5, currentPlayer.getHand().size());
        clickOnSkip();
        assertEquals(6, currentPlayer.getHand().size());
//        pause(2);
    }

    @Test
    public void drawingPirateAfterTreasureGainedByOtherPlayer() {
        Player monkeyPlayerAndPirateDrawer = game.currentPlayer();
        clickOnCardInHand("Monkey");
        clickOnSkip();
        addOnTopOfPlayersDraw(monkeyPlayerAndPirateDrawer, "Pirate");
        clickOnTreasures();
        clickOnSupplyPile("Copper"); // il gagne un TREASURE, Monkey exécute onGain qui pioche Pirate, et cela déclenche la réaction de Pirate
        clickOnYes();
        assertTrue(listContainsCard(monkeyPlayerAndPirateDrawer.getInPlay(), "Monkey"));
        assertTrue(listContainsCard(monkeyPlayerAndPirateDrawer.getInPlay(), "Pirate"));
//        pause(2);
    }
}