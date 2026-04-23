package fr.umontpellier.iut.dominionfx.cards;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.StartTurnState;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.TreasurePhase;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.durations.SeaWitchState;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.*;

public class SeaWitchTest extends BaseTestClass {

    @Override
    public void start(Stage stage) {
        game = new Game(new String[]{"PlayerTest1", "PlayerTest2"},
                new String[]{"Sea Witch", "Lighthouse", "Sailor"});
        super.start(stage);
    }

    @Override
    public void setPlayersHands() {
        addToFirstPlayersHand("Sea Witch");
    }

    @Test
    public void adds2CardsToHandOnCurrentTurn() {
        clickOnCardInHand("Sea Witch");
        Player currentPlayer = game.currentPlayer();
        assertEquals(7, currentPlayer.getHand().size()); // 6 -1(SeaWitch) +2(gained)
        assertInstanceOf(TreasurePhase.class, currentPlayer.getCurrentState());
//        pause(2);
    }

    @Test
    public void adds2CardsToHandOnNextTurn() {
        clickOnCardInHand("Sea Witch");
        clickOnSkip();
        clickOnSkip();
        Player currentPlayer = game.currentPlayer();
        assertEquals(7, currentPlayer.getHand().size()); // 5 +2(duration)
        getFromSupply(currentPlayer, "Lighthouse");
        getFromSupply(currentPlayer, "Sailor");
        WaitForAsyncUtils.waitForFxEvents();
        assertInstanceOf(SeaWitchState.class, currentPlayer.getCurrentState());
        clickOnCardInHand("Lighthouse");
        clickOnSkip();
        clickOnCardInHand("Sailor");
        assertInstanceOf(StartTurnState.class, currentPlayer.getCurrentState());
    }

    @Test
    public void cannotDiscardlessThan2() {
        clickOnCardInHand("Sea Witch");
        clickOnSkip();
        clickOnSkip();
        clickOnCardInHand("Copper");
        clickOnSkip();
        Player currentPlayer = game.currentPlayer();
        assertEquals(6, currentPlayer.getHand().size()); // 5 +2(duration) -1(discard)
        assertInstanceOf(SeaWitchState.class, currentPlayer.getCurrentState());
//        pause(2);
    }

    @Test
    public void otherPlayersgetCurse() {
        Player otherPlayer = game.getPlayers().get(1);
        assertEquals(0, otherPlayer.getDiscard().size());
        clickOnCardInHand("Sea Witch");
        assertTrue(otherPlayer.getDiscard().stream().map(Card::getName).toList().contains("Curse"));
//        pause(2);
    }
 }