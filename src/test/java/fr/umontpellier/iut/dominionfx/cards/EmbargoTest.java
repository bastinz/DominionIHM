package fr.umontpellier.iut.dominionfx.cards;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.TreasurePhase;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class EmbargoTest extends BaseTestClass {

    @Override
    public void start(Stage stage) {
        game = new Game(new String[]{"PlayerTest1", "PlayerTest2"},
                new String[]{"Embargo", "Lighthouse", "Pirate", "Sailor"});
        super.start(stage);
    }

    @Test
    public void addsEmbargoOnSupply() {
        miseEnPlaceDebut();
        clickOnCardInHand("Embargo");
        clickOnSupplyPile("Lighthouse");
        int numberOfEmbargoTokens = game.getSupplyPiles().stream()
                .filter(pile -> "Lighthouse".equals(pile.getName()))
                .findFirst()
                .get().getNbEmbargoTokens();
        assertEquals(1, numberOfEmbargoTokens);
        assertInstanceOf(TreasurePhase.class, game.currentPlayer().getCurrentState());
//        pause(2);
    }

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
        assertInstanceOf(TreasurePhase.class, game.currentPlayer().getCurrentState());
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
        assertInstanceOf(TreasurePhase.class, game.currentPlayer().getCurrentState());
//        pause(2);
    }

    private void miseEnPlaceDebut() {
        clickOnTreasures();
        clickOnSupplyPile("Embargo");
        clickOnSkip();
        clickOnSkip();
        skipUntilHandHasCard("Embargo");
//        pause(2);
    }

}
