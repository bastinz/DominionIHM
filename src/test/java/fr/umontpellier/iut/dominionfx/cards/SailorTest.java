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

public class SailorTest extends BaseTestClass {

    @Override
    public void start(Stage stage) {
        game = new Game(new String[]{"PlayerTest1", "PlayerTest2"},
                new String[]{"Lighthouse", "Sailor"});
        super.start(stage);
    }

    @Override
    public void setPlayersHands() {
        addToFirstPlayersHand("Sailor");
    }

    @Test
    public void trashesCurseOnNextTurn() {
        clickOnCardInHand("Sailor");
        Player currentPlayer = game.currentPlayer();
        addCardForNextTurn("Curse");
        clickOnSkip();
        clickOnSkip();
        assertTrue(listContainsCard(currentPlayer.getCardsInHand(), "Curse"));
        clickOnCardInHand("Curse");
        assertFalse(listContainsCard(currentPlayer.getCardsInHand(), "Curse"));
        assertInstanceOf(StartTurnState.class, currentPlayer.getCurrentState());
//        pause(2);
    }

    @Test
    public void choosesNotToTrashOnNextTurn() {
        clickOnCardInHand("Sailor");
        Player currentPlayer = game.currentPlayer();
        clickOnSkip();
        clickOnSkip();
        int currentNumberOfCardsInHand = currentPlayer.getHand().size();
        clickOnSkip();
        assertEquals(currentNumberOfCardsInHand, currentPlayer.getHand().size());
        assertInstanceOf(StartTurnState.class, currentPlayer.getCurrentState());
//        pause(2);
    }
 }