package fr.umontpellier.iut.dominionfx.cards;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.SupplyPile;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.TreasurePhase;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions.ExplorerState;
import javafx.application.Platform;
import javafx.beans.binding.ListExpression;
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
    }

    @Test
    public void explorerWithProvinceAddsGold() {
        clickOnCardInHand("Explorer");
        Player currentPlayer = game.currentPlayer();
        getFromSupply(currentPlayer, "Province");
        WaitForAsyncUtils.waitForFxEvents();

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
        clickOnCardInHand("Explorer");
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
        clickOnCardInHand("Explorer");
        Platform.runLater(() -> {
            Optional<SupplyPile> silverPile = game.getSupplyPiles().stream()
                    .filter(p -> p.getName().equals("Silver"))
                    .findFirst();
            silverPile.ifPresent(ListExpression::clear);        });
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

}