package fr.umontpellier.iut.dominionfx.cards;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.plain.PirateShipAttackState;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.plain.PirateShipChoiceState;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class PirateShipTest extends BaseTestClass {

    @Override
    public void start(Stage stage) {
        game = new Game(new String[]{"PlayerTest1", "PlayerTest2"},
                new String[]{"Pirate Ship", "Bazaar"});
        super.start(stage);
    }

    @Override
    public void setPlayersHands() {
        addToFirstPlayersHand("Pirate Ship");
    }

    @Test
    public void skipNotAllowedWhenChoosing() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Pirate Ship");
        clickOnSkip();
        assertInstanceOf(PirateShipChoiceState.class, currentPlayer.getCurrentState());
        assertEquals(currentPlayer, game.currentPlayer());
    }

    @Test
    public void skipNotAllowedWhenAttacking() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Pirate Ship");
        clickOnNo();
        clickOnSkip();
        assertInstanceOf(PirateShipAttackState.class, currentPlayer.getCurrentState());
        assertEquals(currentPlayer, game.currentPlayer());
    }

    @Test
    public void getsMoneyWhenYesToCoins() {
        Player firstPlayer = game.currentPlayer();
        firstPlayer.pirateShipCounterProperty().setValue(5);
        int initialMoney = firstPlayer.getMoney();
        clickOnCardInHand("Pirate Ship");
        clickOnYes();
        assertEquals(initialMoney + firstPlayer.pirateShipCounterProperty().getValue(), firstPlayer.getMoney());
//        pause(2);
    }

    @Test
    public void trashesTreasureWhenNoToCoins() {
        Player firstPlayer = game.currentPlayer();
        Player secondPlayer = game.getLastPlayer();
        addOnTopOfPlayersDraw(game.currentPlayer().getPlayers().get(1), "Silver");
        int initialTotalNumberOfCards =  secondPlayer.getAllOwnedCards().size();
        int initialNumberOfCardsInDiscard =  secondPlayer.getDiscard().size();
        int initialMoney = firstPlayer.getMoney();
        clickOnCardInHand("Pirate Ship");
        clickOnNo();
        clickOnTemporaryCard("Silver");
        assertEquals(initialMoney, firstPlayer.getMoney());
        assertEquals(initialTotalNumberOfCards - 1, secondPlayer.getAllOwnedCards().size());
        assertEquals(initialNumberOfCardsInDiscard + 1, secondPlayer.getDiscard().size());
//        pause(2);
    }
 }
