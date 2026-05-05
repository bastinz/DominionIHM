package fr.umontpellier.iut.dominionfx.cards;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.StartTurnState;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions.NativeVillageState;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NativeVillageTest extends BaseTestClass {

    @Override
    public void start(Stage stage) {
        game = new Game(new String[]{"PlayerTest1", "PlayerTest2"},
                new String[]{"Native Village", "Lighthouse"});
        super.start(stage);
    }

    @Override
    public void setPlayersHands() {
        addToFirstPlayersHand("Native Village");
    }

    @Test
    public void skipNotAllowedWhenChoosing() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Native Village");
        clickOnSkip();
        assertInstanceOf(NativeVillageState.class, currentPlayer.getCurrentState());
        assertEquals(currentPlayer, game.currentPlayer());
    }

    @Test
    public void addsCardsToNativeVillageMat() {
        Player currentPlayer = game.currentPlayer();
        int initialDrawSize = currentPlayer.getDraw().size();
        int initialNativeVillageMatSize = currentPlayer.getNativeVillageMat().size();
        clickOnCardInHand("Native Village");
        clickOnAddToNativeVillageMat();
        assertInstanceOf(StartTurnState.class, currentPlayer.getCurrentState());
        assertEquals(2, currentPlayer.getNumberOfActions());
        assertEquals(initialDrawSize - 1, currentPlayer.getDraw().size());
        assertEquals(initialNativeVillageMatSize + 1, currentPlayer.getNativeVillageMat().size());
//        pause(2);
    }

    @Test
    public void takesCardsFromNativeVillageMat() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Native Village");
        clickOnAddToNativeVillageMat();
        assertEquals(1, currentPlayer.getNativeVillageMat().size());
        clickOnSkip();
        clickOnSkip();
        addToFirstPlayersHand("Native Village");
        clickOnCardInHand("Native Village");
        int initialHandSize = currentPlayer.getHand().size();
        int initialNativeVillageMatSize = currentPlayer.getNativeVillageMat().size();
        clickOnTakeFromNativeVillageMat();
        assertEquals(initialHandSize + initialNativeVillageMatSize, currentPlayer.getHand().size());
        assertTrue(currentPlayer.getNativeVillageMat().isEmpty());
        clickOnSkip();clickOnSkip();
//        pause(2);
    }
}