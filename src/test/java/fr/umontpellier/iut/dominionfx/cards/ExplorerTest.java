package fr.umontpellier.iut.dominionfx.cards;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    public void playExplorer() {
        miseEnPlaceDebut();
        long initialNumberOfGold = game.currentPlayer().getHand().stream()
                .filter(card -> "Gold".equals(card.getName()))
                .count();
        clickOnCardInHand("Province");
        long currentNumberOfGold = game.currentPlayer().getHand().stream()
                .filter(card -> "Gold".equals(card.getName()))
                .count();
        assertEquals(initialNumberOfGold + 1, currentNumberOfGold);
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
