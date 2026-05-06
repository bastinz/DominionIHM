package fr.umontpellier.iut.dominionfx.cards;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.plain.LookoutState;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class LookoutTest extends BaseTestClass {

    @Override
    public void start(Stage stage) {
        game = new Game(new String[]{"PlayerTest1", "PlayerTest2"},
                new String[]{"Lookout"});
        super.start(stage);
    }

    @Override
    public void setPlayersHands() {
        addToFirstPlayersHand("Lookout");
    }

    @Test
    public void skipNotAllowedAtStart() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Lookout");
        clickOnSkip();
        assertInstanceOf(LookoutState.class, currentPlayer.getCurrentState());
        assertEquals(currentPlayer, game.currentPlayer());
    }

    @Test
    public void skipNotAllowedAfterTrash() {
        Player currentPlayer = game.currentPlayer();
        String cardToTrash = currentPlayer.getDraw().get(2).getName();
        clickOnCardInHand("Lookout");
        clickOnTemporaryCard(cardToTrash);
        clickOnSkip();
        assertInstanceOf(LookoutState.class, currentPlayer.getCurrentState());
        assertEquals(currentPlayer, game.currentPlayer());
    }

    @Test
    public void trashesAndDiscards() {
        Player currentPlayer = game.currentPlayer();
        int initialNumberOfTrashedCards = game.getNumberOfTrashedCards();
        int initialNumberOfCardInDraw = currentPlayer.getDraw().size();
        String cardToTrash = currentPlayer.getDraw().get(2).getName();
        String cardToDiscard = currentPlayer.getDraw().get(0).getName();
        clickOnCardInHand("Lookout");
        clickOnTemporaryCard(cardToTrash);
        clickOnTemporaryCard(cardToDiscard);
        assertEquals(initialNumberOfCardInDraw - 2, currentPlayer.getDraw().size());
        assertEquals(10, currentPlayer.getAllOwnedCards().size()); // +1 Lookout ajoutée pour le test
        assertEquals(initialNumberOfTrashedCards + 1, game.getNumberOfTrashedCards());
//        pause(2);
    }
 }
