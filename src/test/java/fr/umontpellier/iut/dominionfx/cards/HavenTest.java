package fr.umontpellier.iut.dominionfx.cards;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.StartTurnState;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions.HavenState;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HavenTest extends BaseTestClass {

    @Override
    public void start(Stage stage) {
        game = new Game(new String[]{"PlayerTest1", "PlayerTest2"},
                new String[]{"Haven", "Lighthouse"});
        super.start(stage);
    }

    @Override
    public void setPlayersHands() {
        addToFirstPlayersHand("Haven");
        addToFirstPlayersHand("Silver");
    }

    @Test
    public void skipNotAllowed() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Haven");
        clickOnSkip();
        assertInstanceOf(HavenState.class, currentPlayer.getCurrentState());
        assertEquals(currentPlayer, game.currentPlayer());
    }

    @Test
    public void increasesNumberOfCardsAside() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Haven");
        clickOnCardInHand("Silver");
        assertEquals(1, currentPlayer.getNumberOfActions());
        assertEquals(6, currentPlayer.getHand().size()); // 7 au départ, -1 Haven, +1 gagné, -1 Silver
        assertEquals(1, currentPlayer.getCardsSetAside().size());
        assertInstanceOf(StartTurnState.class, currentPlayer.getCurrentState());
//        pause(2);
    }

    @Test
    public void getsSetAsideCardOnNextTurn() {
        Player firstPlayer = game.currentPlayer();
        Card silver = firstPlayer.getHand().stream().filter(c -> c.getName().equals("Silver")).findFirst().orElseThrow();
        clickOnCardInHand("Haven");
        clickOnCardInHand("Silver");
        assertTrue(firstPlayer.getCardsSetAside().contains(silver));
        clickOnSkip();
        clickOnSkip();
        assertTrue(firstPlayer.getHand().contains(silver));
        assertInstanceOf(StartTurnState.class, firstPlayer.getCurrentState());
//        pause(2);
    }
}
