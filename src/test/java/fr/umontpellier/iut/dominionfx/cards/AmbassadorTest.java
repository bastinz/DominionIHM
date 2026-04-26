package fr.umontpellier.iut.dominionfx.cards;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.TreasurePhase;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions.OnGoingActionState;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class AmbassadorTest extends BaseTestClass {

    @Override
    public void start(Stage stage) {
        game = new Game(new String[]{"PlayerTest1", "PlayerTest2"},
                new String[]{"Ambassador", "Lighthouse"});
        super.start(stage);
    }

    @Override
    public void setPlayersHands() {
       addToFirstPlayersHand("Ambassador");
    }

    @Test
    public void returnsOneCardToSupply() {
        Player currentPlayer = game.currentPlayer();
        getFromSupply(currentPlayer, "Lighthouse");
        getFromSupply(currentPlayer, "Lighthouse");
        WaitForAsyncUtils.waitForFxEvents();

        int initialNbOfCardsInHand = currentPlayer.getHand().size();
        clickOnCardInHand("Ambassador"); // -1 card
        clickOnCardInHand("Lighthouse");
        clickOnCardInHand("Lighthouse"); // -1 card
        clickOnSkip();
        assertInstanceOf(TreasurePhase.class, currentPlayer.getCurrentState());
        assertEquals(initialNbOfCardsInHand - 2, currentPlayer.getHand().size());
//        pause(2);
    }

    @Test
    public void cannotReturnAnyOtherCardToSupply() {
        Player currentPlayer = game.currentPlayer();
        getFromSupply(currentPlayer, "Lighthouse");
        getFromSupply(currentPlayer, "Ambassador");
        WaitForAsyncUtils.waitForFxEvents();

        int initialNbOfCardsInHand = currentPlayer.getHand().size();
        clickOnCardInHand("Ambassador"); // -1 card
        clickOnCardInHand("Lighthouse");
        clickOnCardInHand("Ambassador"); // nothing should happen
        clickOnSkip();
        assertEquals(initialNbOfCardsInHand - 1, currentPlayer.getHand().size());
        assertInstanceOf(OnGoingActionState.class, currentPlayer.getCurrentState());
//        pause(2);
    }

    @Test
    public void returnsTwoCardsToSupply() {
        Player currentPlayer = game.currentPlayer();
        getFromSupply(currentPlayer, "Lighthouse");
        getFromSupply(currentPlayer, "Lighthouse");
        WaitForAsyncUtils.waitForFxEvents();

        int initialNbOfCardsInHand = currentPlayer.getHand().size();
        clickOnCardInHand("Ambassador"); // -1 card
        clickOnCardInHand("Lighthouse");
        clickOnCardInHand("Lighthouse"); // -1 card
        clickOnCardInHand("Lighthouse"); // -1 card
        assertEquals(initialNbOfCardsInHand - 3, currentPlayer.getHand().size());
        assertInstanceOf(TreasurePhase.class, currentPlayer.getCurrentState());
//        pause(2);
    }

    @Test
    public void otherPlayersGetACopyOfRevealedCard() {
        Player currentPlayer = game.currentPlayer();
        getFromSupply(currentPlayer, "Lighthouse");
        WaitForAsyncUtils.waitForFxEvents();

        clickOnCardInHand("Ambassador");
        clickOnCardInHand("Lighthouse");
        clickOnCardInHand("Lighthouse");
        clickOnSkip();
        assertEquals(1, game.getPlayers().get(1).getDiscard().size());
        assertInstanceOf(TreasurePhase.class, currentPlayer.getCurrentState());
//        pause(2);
    }
}
