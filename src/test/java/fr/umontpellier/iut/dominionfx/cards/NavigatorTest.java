package fr.umontpellier.iut.dominionfx.cards;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.TreasurePhase;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.plain.NavigatorState;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class NavigatorTest extends BaseTestClass {

    @Override
    public void start(Stage stage) {
        game = new Game(new String[]{"PlayerTest1", "PlayerTest2"},
                new String[]{"Navigator", "Province"});
        super.start(stage);
    }

    @Test
    public void skipNotAllowedWhenChoosing() {
        Player currentPlayer = game.currentPlayer();
        addToFirstPlayersHand("Navigator");
        clickOnCardInHand("Navigator");
        clickOnSkip();
        assertInstanceOf(NavigatorState.class, currentPlayer.getCurrentState());
        assertEquals(currentPlayer, game.currentPlayer());
    }

    @Test
    public void skipNotAllowedAfterFirstCardBack() {
        Player currentPlayer = game.currentPlayer();
        clickOnSkip();
        clickOnSkip();
        clickOnSkip();
        addToFirstPlayersHand("Navigator");
        clickOnSkip();
        clickOnCardInHand("Navigator");
        List<String> cardsNames = game.getTemporaryCardsNames();
        clickOnNo();
        clickOnTemporaryCard(cardsNames.get(0));
        clickOnSkip();
        assertInstanceOf(NavigatorState.class, currentPlayer.getCurrentState());
        assertEquals(currentPlayer, game.currentPlayer());
    }

    @Test
    public void answerYesToDiscard() {
        Player firstPlayer = game.currentPlayer();
        clickOnSkip();
        clickOnSkip();
        clickOnSkip();
        addToFirstPlayersHand("Navigator");
        clickOnSkip();
        int initialDrawSize = firstPlayer.getDraw().size();
        clickOnCardInHand("Navigator");
        assertEquals(initialDrawSize - 5, firstPlayer.getDraw().size());
        initialDrawSize = firstPlayer.getDraw().size();
        clickOnYes();
        assertEquals(initialDrawSize + 5, firstPlayer.getDraw().size());
//        pause(2);
    }

    @Test
    public void answerNoToDiscard() {
        Player firstPlayer = game.currentPlayer();
        clickOnSkip();
        clickOnSkip();
        clickOnSkip();
        addToFirstPlayersHand("Navigator");
        clickOnSkip();
        int initialDrawSize = firstPlayer.getDraw().size();
        clickOnCardInHand("Navigator");
        assertEquals(initialDrawSize - 5, firstPlayer.getDraw().size());
        initialDrawSize = firstPlayer.getDraw().size();
        List<String> cardsNames = game.getTemporaryCardsNames();
        clickOnNo();
        clickOnTemporaryCard(cardsNames.get(0));
        clickOnTemporaryCard(cardsNames.get(2));
        clickOnTemporaryCard(cardsNames.get(3));
        clickOnTemporaryCard(cardsNames.get(1));
        clickOnTemporaryCard(cardsNames.get(4));
        assertEquals(initialDrawSize + 5, firstPlayer.getDraw().size());
        assertInstanceOf(TreasurePhase.class, firstPlayer.getCurrentState());
//        pause(2);
    }
 }
