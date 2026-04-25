package fr.umontpellier.iut.dominionfx.cards;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions.IslandState;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IslandTest extends BaseTestClass {

    @Override
    public void start(Stage stage) {
        game = new Game(new String[]{"PlayerTest1", "PlayerTest2"},
                new String[]{"Island", "Lighthouse"});
        super.start(stage);
    }

    @Override
    public void setPlayersHands() {
        addToFirstPlayersHand("Island");
    }

    @Test
    public void addsTwoCardsToIslandMat() {
        clickOnCardInHand("Island");
        Player currentPlayer = game.currentPlayer();
        assertInstanceOf(IslandState.class, currentPlayer.getCurrentState());
        clickOnCardInHand("Copper");
        assertTrue(currentPlayer.getIslandMat().stream().map(Card::getName).toList().contains("Island"));
        assertTrue(currentPlayer.getIslandMat().stream().map(Card::getName).toList().contains("Copper"));
//        pause(2);
    }

    @Test
    public void addsOneCardToIslandMat() {
        clickOnCardInHand("Island");
        Player currentPlayer = game.currentPlayer();
        assertInstanceOf(IslandState.class, currentPlayer.getCurrentState());
        clickOnSkip();
        assertEquals(1, currentPlayer.getIslandMat().size());
        assertTrue(currentPlayer.getIslandMat().stream().map(Card::getName).toList().contains("Island"));
//        pause(2);
    }
}