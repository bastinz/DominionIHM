package fr.umontpellier.iut.dominionfx.basicgame.basiccards;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.StartTurnState;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.TreasurePhase;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CorsairTest extends BaseTestClass {

    @Override
    public void start(Stage stage) {
        game = new Game(new String[]{"PlayerTest1", "PlayerTest2"},
                new String[]{"Corsair"});
        super.start(stage);
    }

    @Override
    public void setPlayersHands() {
        addToFirstPlayersHand("Corsair");
        addToSecondPlayersHand("Silver");
    }

    @Test
    public void incrementsMoneyOnCurrentTurn() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Corsair");
        assertEquals(2, currentPlayer.getMoney());
        assertInstanceOf(TreasurePhase.class, currentPlayer.getCurrentState());
//        pause(2);
    }

    @Test
    public void incrementsHandOnNextTurn() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Corsair");
        clickOnSkip();
        clickOnSkip();
        assertEquals(6, currentPlayer.getHand().size());
        assertEquals(4, currentPlayer.getDraw().size()); // parce que Corsair est resté InPlay
        assertInstanceOf(StartTurnState.class, currentPlayer.getCurrentState());
//        pause(2);
    }

    @Test
    public void otherPlayersTrashSilver() {
        clickOnCardInHand("Corsair");
        clickOnSkip();
        Player otherPlayer = game.currentPlayer();
        assertEquals(0, game.getNumberOfTrashedCards());
        assertEquals(6, otherPlayer.getHand().size()); // 5 + Silver
        Card silver = otherPlayer.getHand().stream().filter(c -> c.getName().equals("Silver")).findFirst().orElseThrow();
        clickOnCardInHand("Silver");
        assertEquals(1, game.getNumberOfTrashedCards());
        assertFalse(otherPlayer.getAllOwnedCards().contains(silver));
        assertEquals(5, game.currentPlayer().getHand().size());
//        pause(2);
    }
}
