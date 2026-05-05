package fr.umontpellier.iut.dominionfx.cards;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.SupplyPile;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions.SmugglersState;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class SmugglersTest extends BaseTestClass {

    @Override
    public void start(Stage stage) {
        game = new Game(new String[]{"PlayerTest1", "PlayerTest2"},
                new String[]{"Smugglers", "Lighthouse", "Sailor"});
        super.start(stage);
    }

    @Override
    public void setPlayersHands() {
        addToFirstPlayersHand("Gold");
    }

    @Test
    public void skipNotAllowedWhenChoosingPile() {
        SupplyPile sailorPile = game.getSupplyPiles().stream()
                .filter(pile -> "Sailor".equals(pile.getName()))
                .findFirst()
                .orElseThrow();
        clickOnTreasures();
        clickOnSupplyPile("Lighthouse");
        Player currentPlayer = game.currentPlayer();
        getFromSupplyToHand(currentPlayer, "Smugglers");
        clickOnCardInHand("Smugglers");
        pause(2);
        clickOnSkip();
        assertInstanceOf(SmugglersState.class, currentPlayer.getCurrentState());
        assertEquals(currentPlayer, game.currentPlayer());
    }

    @Test
    public void getsACardWhenPreviousGained() {
        SupplyPile lightHousePile = game.getSupplyPiles().stream()
                .filter(pile -> "Lighthouse".equals(pile.getName()))
                .findFirst()
                .orElseThrow();
        clickOnTreasures();
        clickOnSupplyPile("Lighthouse");
        Player currentPlayer = game.currentPlayer();
        getFromSupplyToHand(currentPlayer, "Smugglers");
        assertEquals(0, currentPlayer.getDiscard().size());
        clickOnCardInHand("Smugglers");
        clickOnSupplyPile("Lighthouse");
        assertEquals(1, currentPlayer.getDiscard().size());
        assertEquals(8, lightHousePile.size());
//        pause(2);
    }

    @Test
    public void doesNotGetWrongCardWhenPreviousGained() {
        SupplyPile sailorPile = game.getSupplyPiles().stream()
                .filter(pile -> "Sailor".equals(pile.getName()))
                .findFirst()
                .orElseThrow();
        clickOnTreasures();
        clickOnSupplyPile("Lighthouse");
        Player currentPlayer = game.currentPlayer();
        getFromSupplyToHand(currentPlayer, "Smugglers");
        assertEquals(0, currentPlayer.getDiscard().size());
        clickOnCardInHand("Smugglers");
        clickOnSupplyPile("Sailor");
        assertEquals(0, currentPlayer.getDiscard().size());
        assertEquals(10, sailorPile.size());
//        pause(2);
    }

    @Test
    public void doesNotGetACardWhenPreviousDidNotGain() {
        SupplyPile lightHousePile = game.getSupplyPiles().stream()
                .filter(pile -> "Lighthouse".equals(pile.getName()))
                .findFirst()
                .orElseThrow();
        Player currentPlayer = game.currentPlayer();
        getFromSupplyToHand(currentPlayer, "Smugglers");
        assertEquals(0, currentPlayer.getDiscard().size());
        clickOnCardInHand("Smugglers");
        assertEquals(0, currentPlayer.getDiscard().size());
        clickOnSupplyPile("Lighthouse");
        clickOnSupplyPile("Lighthouse");
        assertEquals(0, currentPlayer.getDiscard().size());
        assertEquals(10, lightHousePile.size());
//        pause(2);
    }

    @Test
    public void doesNotGetWhenPreviousGainedCardCostsMoreThan6() {
        SupplyPile provincePile = game.getSupplyPiles().stream()
                .filter(pile -> "Province".equals(pile.getName()))
                .findFirst()
                .orElseThrow();
        Player currentPlayer = game.currentPlayer();
        getFromSupplyToHand(currentPlayer, "Gold");
        clickOnTreasures();
        clickOnSupplyPile("Province");
        currentPlayer = game.currentPlayer();
        getFromSupplyToHand(currentPlayer, "Smugglers");
        clickOnCardInHand("Smugglers");
        clickOnSupplyPile("Province");
        assertEquals(0, currentPlayer.getDiscard().size());
        assertEquals(7, provincePile.size());
//        pause(2);
    }
}