package fr.umontpellier.iut.dominionfx.basicgame.basiccards;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.TreasurePhase;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class CutpurseTest extends BaseTestClass {

    @Override
    public void start(Stage stage) {
        game = new Game(new String[]{"PlayerTest1", "PlayerTest2"},
                new String[]{"Cutpurse"});
        super.start(stage);
    }

    @Override
    public void setPlayersHands() {
        addToFirstPlayersHand("Cutpurse");
    }

    @Test
    public void incrementsMoneyOnCurrentTurn() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Cutpurse");
        assertEquals(2, currentPlayer.getMoney());
        assertInstanceOf(TreasurePhase.class, currentPlayer.getCurrentState());
//        pause(2);
    }

    @Test
    public void otherPlayersDiscardCooper() {
        Player otherPlayer = game.getLastPlayer();
        long initialNbOfCoopersInOtherPlayerHand = otherPlayer.getHand().stream().filter(c -> c.getName().equals("Copper")).count();
        long initialNbOfCardsInOtherPlayerDiscard = otherPlayer.getDiscard().size();
        clickOnCardInHand("Cutpurse");
        long currentNbOfCoopersInOtherPlayerHand = otherPlayer.getHand().stream().filter(c -> c.getName().equals("Copper")).count();
        long currentNbOfCardsInOtherPlayerDiscard = otherPlayer.getDiscard().size();
        assertEquals(initialNbOfCoopersInOtherPlayerHand - 1, currentNbOfCoopersInOtherPlayerHand);
        assertEquals(initialNbOfCardsInOtherPlayerDiscard + 1, currentNbOfCardsInOtherPlayerDiscard);
//        pause(2);
    }
}
