package fr.umontpellier.iut.dominionfx.cards;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.TreasurePhase;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class EmbargoTest extends BaseTestClass {

    @Override
    public void start(Stage stage) {
        game = new Game(new String[]{"PlayerTest1", "PlayerTest2"},
                new String[]{"Embargo", "Lighthouse", "Pirate", "Sailor"});
        super.start(stage);
    }

    @Override
    public void setPlayersHands() {
        addToFirstPlayersHand("Embargo");
    }

    @Test
    public void addsEmbargoOnSupply() {
        clickOnCardInHand("Embargo");
        clickOnSupplyPile("Lighthouse");
        int numberOfEmbargoTokens = game.getSupplyPiles().stream()
                .filter(pile -> "Lighthouse".equals(pile.getName()))
                .findFirst()
                .get().getNbEmbargoTokens();
        assertInstanceOf(TreasurePhase.class, game.currentPlayer().getCurrentState());
        assertEquals(1, numberOfEmbargoTokens);
//        pause(2);
    }

    @Test
    public void addsTwoEmbargosOnSameSupplyPile() {
        clickOnCardInHand("Embargo");
        clickOnSupplyPile("Lighthouse");
        clickOnSkip();
        clickOnSkip();

        Player currentPlayer = game.currentPlayer();
        getFromSupply(currentPlayer, "Embargo");
        WaitForAsyncUtils.waitForFxEvents();

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
        clickOnCardInHand("Embargo");
        clickOnSupplyPile("Lighthouse");
        clickOnSkip();
        clickOnSkip();

        Player currentPlayer = game.currentPlayer();
        getFromSupply(currentPlayer, "Embargo");
        WaitForAsyncUtils.waitForFxEvents();

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

}
