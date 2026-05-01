package fr.umontpellier.iut.dominionfx.cards;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.StartTurnState;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.TreasurePhase;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.*;

public class SailorTest extends BaseTestClass {

    @Override
    public void start(Stage stage) {
        game = new Game(new String[]{"PlayerTest1", "PlayerTest2"},
                new String[]{"Merchant Ship", "Sailor", "Haven"});
        super.start(stage);
    }

    @Override
    public void setPlayersHands() {
        addToFirstPlayersHand("Sailor");
        addToFirstPlayersHand("Gold");
        addToFirstPlayersHand("Gold");
    }

    @Test
    public void gainsDuration() {
        Player currentPlayer = game.currentPlayer();
        Platform.runLater(() -> currentPlayer.incrementBuys(1));
        WaitForAsyncUtils.waitForFxEvents();

        clickOnCardInHand("Sailor");
        clickOnTreasures();
        clickOnSupplyPile("Merchant Ship");
        clickOnYes();
        assertInstanceOf(TreasurePhase.class, game.currentPlayer().getCurrentState());
//        pause(2);
    }

    @Test
    public void trashesCurseOnNextTurn() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Sailor");
        addCardForNextDraw("Curse");
        clickOnSkip();
        clickOnSkip();
        assertTrue(listContainsCard(currentPlayer.getCardsInHand(), "Curse"));
        clickOnCardInHand("Curse");
        assertFalse(listContainsCard(currentPlayer.getCardsInHand(), "Curse"));
        assertInstanceOf(StartTurnState.class, currentPlayer.getCurrentState());
        assertEquals(2, currentPlayer.getMoney());
//        pause(2);
    }

    @Test
    public void choosesNotToTrashOnNextTurn() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Sailor");
        clickOnSkip();
        clickOnSkip();
        int currentNumberOfCardsInHand = currentPlayer.getHand().size();
        clickOnSkip();
        assertEquals(currentNumberOfCardsInHand, currentPlayer.getHand().size());
        assertInstanceOf(StartTurnState.class, currentPlayer.getCurrentState());
//        pause(2);
    }
 }