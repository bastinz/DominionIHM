package fr.umontpellier.iut.dominionfx.basicgame;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.SupplyPile;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.ActionPhase;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.StartTurn;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.TreasurePhase;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ActionPhaseTest extends BaseTestClass {

    @Override
    public void start(Stage stage) {
        game = new Game(new String[]{"PlayerTest1", "PlayerTest2"},
                        new String[]{"Bazaar", "Warehouse", "Smugglers", "Sea Hag"});
        super.start(stage);
    }

    @Override
    public void setPlayersHands() {
        addToFirstPlayersHand("Sea Hag");
        addToFirstPlayersHand("Sea Hag");
        addToFirstPlayersHand("Bazaar");
    }

    @Test
    public void moveToTreasureAfterAction() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Sea Hag");
        assertEquals(currentPlayer, game.currentPlayer());
        assertEquals(0, game.currentPlayer().getNumberOfActions());
        assertInstanceOf(TreasurePhase.class, currentPlayer.getCurrentState());
//        pause(2);
    }

    @Test
    public void remainInActionPhaseWhenMoreThanOneActionInPlay() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Bazaar");
        assertInstanceOf(StartTurn.class, currentPlayer.getCurrentState());
        assertEquals(2, currentPlayer.getNumberOfActions());
        clickOnCardInHand("Sea Hag");
        assertEquals(1, currentPlayer.getNumberOfActions());
        assertInstanceOf(StartTurn.class, currentPlayer.getCurrentState());
        clickOnCardInHand("Sea Hag");
        assertInstanceOf(TreasurePhase.class, currentPlayer.getCurrentState());
        assertEquals(currentPlayer, game.currentPlayer());
//        pause(2);
    }

}
