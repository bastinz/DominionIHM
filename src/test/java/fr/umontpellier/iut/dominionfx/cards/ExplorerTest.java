package fr.umontpellier.iut.dominionfx.cards;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.SupplyPile;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.ExplorerState;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.StartTurn;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.TreasurePhase;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class ExplorerTest extends BaseTestClass {

    @Override
    public void start(Stage stage) {
        game = new Game(new String[]{"PlayerTest1", "PlayerTest2"},
                new String[]{"Explorer", "Lighthouse", "Sailor"});
        super.start(stage);
    }

    @Override
    public void setPlayersHands() {
        addToFirstPlayersHand("Explorer");
        addToFirstPlayersHand("Province");
    }

    @Test
    public void explorerWithProvinceAddsGold() {
        miseEnPlaceDebut();
        Player currentPlayer = game.currentPlayer();
        long initialNumberOfGold = currentPlayer.getHand().stream()
                .filter(card -> "Gold".equals(card.getName()))
                .count();
        assertInstanceOf(ExplorerState.class, currentPlayer.getCurrentState());
        clickOnCardInHand("Province");
        long currentNumberOfGold = currentPlayer.getHand().stream()
                .filter(card -> "Gold".equals(card.getName()))
                .count();
        assertInstanceOf(TreasurePhase.class, currentPlayer.getCurrentState());
        assertEquals(initialNumberOfGold + 1, currentNumberOfGold);
    }

    @Test
    public void explorerWithoutProvinceAddsSilver() {
        miseEnPlaceDebut();
        Player currentPlayer = game.currentPlayer();
        long initialNumberOfSilver = currentPlayer.getHand().stream()
                .filter(card -> "Silver".equals(card.getName()))
                .count();
        clickOnSkip();
        long currentNumberOfSilver = currentPlayer.getHand().stream()
                .filter(card -> "Silver".equals(card.getName()))
                .count();
        assertInstanceOf(TreasurePhase.class, currentPlayer.getCurrentState());
        assertEquals(initialNumberOfSilver + 1, currentNumberOfSilver);
    }

    @Test
    public void explorerWithoutProvinceAndEmptySilverPile() {
        miseEnPlaceDebut();
        Platform.runLater(() -> {
            Optional<SupplyPile> silverPile = game.getSupplyPiles().stream()
                    .filter(p -> p.getName().equals("Silver"))
                    .findFirst();
            silverPile.ifPresent(p -> p.clear());        });
        WaitForAsyncUtils.waitForFxEvents();

        Player currentPlayer = game.currentPlayer();
        long initialNumberOfSilver = currentPlayer.getHand().stream()
                .filter(card -> "Silver".equals(card.getName()))
                .count();
        clickOnSkip();
        long currentNumberOfSilver = currentPlayer.getHand().stream()
                .filter(card -> "Silver".equals(card.getName()))
                .count();
        assertEquals(initialNumberOfSilver, currentNumberOfSilver);
        assertInstanceOf(TreasurePhase.class, currentPlayer.getCurrentState());
    }
/*
    @Test
    public void addsTwoEmbargosOnSameSupplyPile() {
        miseEnPlaceDebut();
        clickOnCardInHand("Embargo");
        clickOnSupplyPile("Lighthouse");
        skipUntilHandHasEnoughTreasures(2);
        clickOnTreasures();
        clickOnSupplyPile("Embargo");
        skipUntilHandHasCard("Embargo");
        clickOnCardInHand("Embargo");
        clickOnSupplyPile("Lighthouse");
        int numberOfEmbargoTokens = game.getSupplyPiles().stream()
                .filter(pile -> "Lighthouse".equals(pile.getName()))
                .findFirst()
                .get().getNbEmbargoTokens();
        assertEquals(2, numberOfEmbargoTokens);
//        pause(2);
    }

    @Test
    public void addsTwoEmbargosOnDifferentSupplyPiles() {
        miseEnPlaceDebut();
        clickOnCardInHand("Embargo");
        clickOnSupplyPile("Lighthouse");
        skipUntilHandHasEnoughTreasures(2);
        clickOnTreasures();
        clickOnSupplyPile("Embargo");
        skipUntilHandHasCard("Embargo");
        clickOnCardInHand("Embargo");
        clickOnSupplyPile("Sailor");
        int numberOfEmbargoTokensLighthouse = game.getSupplyPiles().stream()
                .filter(pile -> "Lighthouse".equals(pile.getName()))
                .findFirst()
                .get().getNbEmbargoTokens();
        assertEquals(1, numberOfEmbargoTokensLighthouse);
        int numberOfEmbargoTokensSailor = game.getSupplyPiles().stream()
                .filter(pile -> "Sailor".equals(pile.getName()))
                .findFirst()
                .get().getNbEmbargoTokens();
        assertEquals(1, numberOfEmbargoTokensSailor);
//        pause(2);
    }*/

    private void miseEnPlaceDebut() {
        clickOnCardInHand("Explorer");
//        pause(2);
    }

}
