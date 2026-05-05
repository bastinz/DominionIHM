package fr.umontpellier.iut.dominionfx.cards;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.StartTurnState;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.plain.PearlDiverState;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PearlDiverTest extends BaseTestClass {

    @Override
    public void start(Stage stage) {
        game = new Game(new String[]{"PlayerTest1", "PlayerTest2"},
                new String[]{"Pearl Diver", "Province"});
        super.start(stage);
    }

    @Override
    public void setPlayersHands() {
        addToFirstPlayersHand("Pearl Diver");
    }

    @Test
    public void skipNotAllowedWhenChoosing() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Pearl Diver");
        clickOnSkip();
        assertInstanceOf(PearlDiverState.class, currentPlayer.getCurrentState());
        assertEquals(currentPlayer, game.currentPlayer());
    }

    @Test
    public void answerYesToAcceptBottomCard() {
        Player firstPlayer = game.currentPlayer();
        Card cardToAccept =  firstPlayer.getDraw().getFirst();
        clickOnCardInHand("Pearl Diver");
        clickOnYes();
        assertEquals(firstPlayer, game.currentPlayer());
        assertInstanceOf(StartTurnState.class, game.currentPlayer().getCurrentState());
        assertEquals(cardToAccept, firstPlayer.getDraw().getLast());
//        pause(2);
    }

    @Test
    public void answerNoToAcceptBottomCard() {
        Player firstPlayer = game.currentPlayer();
        Card cardToAccept =  firstPlayer.getDraw().getFirst();
        clickOnCardInHand("Pearl Diver");
        clickOnNo();
        assertEquals(firstPlayer, game.currentPlayer());
        assertInstanceOf(StartTurnState.class, game.currentPlayer().getCurrentState());
        assertNotEquals(cardToAccept, firstPlayer.getDraw().getLast());
//        pause(2);
    }
 }
