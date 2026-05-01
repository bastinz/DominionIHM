package fr.umontpellier.iut.dominionfx.cards;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.StartTurnState;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions.WarehouseState;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class WarehouseTest extends BaseTestClass {

    @Override
    public void start(Stage stage) {
        game = new Game(new String[]{"PlayerTest1", "PlayerTest2"},
                new String[]{"Warehouse", "Lighthouse", "Sailor"});
        super.start(stage);
    }

    @Override
    public void setPlayersHands() {
        addToFirstPlayersHand("Warehouse");
    }

    @Test
    public void adds3CardsToHandAnd1Action() {
        clickOnCardInHand("Warehouse");
        clickOnCardInHand("Copper");
        clickOnCardInHand("Copper");
        clickOnCardInHand("Copper");

        Player currentPlayer = game.currentPlayer();
        assertEquals(5, currentPlayer.getHand().size()); // 6 -1(Warehouse) +3(gained) -3(discard)
        assertInstanceOf(StartTurnState.class, currentPlayer.getCurrentState());
//        pause(2);
    }

    @Test
    public void cannotDiscardLessThan3() {
        clickOnCardInHand("Warehouse");
        clickOnCardInHand("Copper");
        clickOnCardInHand("Copper");
        clickOnSkip();
        Player currentPlayer = game.currentPlayer();
        assertEquals(6, currentPlayer.getHand().size()); // 6 -1(Warehouse) +3(gained) -2(discard)
        assertInstanceOf(WarehouseState.class, currentPlayer.getCurrentState());
//        pause(2);
    }
 }