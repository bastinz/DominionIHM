package fr.umontpellier.iut.dominionfx.cards;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    public void answerYesToCoins() {
        Player firstPlayer = game.currentPlayer();
        firstPlayer.pirateShipCounterProperty().setValue(5);
        int initialMoney = firstPlayer.getMoney();
        clickOnCardInHand("Pirate Ship");
        clickOnYes();
        assertEquals(initialMoney + firstPlayer.pirateShipCounterProperty().getValue(), firstPlayer.getMoney());
        pause(2);
    }

    @Test
    public void answerNoToCoins() {
        Player firstPlayer = game.currentPlayer();
        Player secondPlayer = game.getPlayers().get(1);
        int initialTotalNumberOfCards =  secondPlayer.getAllOwnedCards().size();
        int initialNumberOfCardsInDiscard =  secondPlayer.getDiscard().size();
        int initialMoney = firstPlayer.getMoney();
        clickOnCardInHand("Pirate Ship");
        clickOnNo();
        clickOnTemporaryCard("Copper");
        assertEquals(initialMoney, firstPlayer.getMoney());
        assertEquals(initialTotalNumberOfCards - 1, secondPlayer.getAllOwnedCards().size());
        assertEquals(initialNumberOfCardsInDiscard + 1, secondPlayer.getDiscard().size());
//        pause(2);
    }
 }
