package fr.umontpellier.iut.dominionfx.cards;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

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
    public void trashesAndDiscards() {
        Player currentPlayer = game.currentPlayer();
        int initialNumberOfTrashedCards = game.getNumberOfTrashedCards();
        String cardToTrash = currentPlayer.getDraw().get(2).getName();
        String cardToDiscard = currentPlayer.getDraw().get(0).getName();
        pause(2);
        clickOnCardInHand("Lookout");
        pause(2);
        clickOnTemporaryCard(cardToTrash);
        pause(2);
        clickOnTemporaryCard(cardToDiscard);
        pause(2);
/*        assertEquals(3, currentPlayer.getDraw().size());
        assertEquals(10, currentPlayer.getAllOwnedCards().size()); // +1 Lookout ajoutée pour le test
        assertEquals(initialNumberOfTrashedCards + 1, game.getNumberOfTrashedCards());*/
//        pause(2);
    }
 }
