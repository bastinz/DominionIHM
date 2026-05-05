package fr.umontpellier.iut.dominionfx.cards;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.StartTurnState;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.TreasurePhase;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.plain.SalvagerState;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class SalvagerTest extends BaseTestClass {

    @Override
    public void start(Stage stage) {
        game = new Game(new String[]{"PlayerTest1", "PlayerTest2"},
                new String[]{"Salvager", "Lighthouse", "Bazaar", "Sailor"});
        super.start(stage);
    }

    @Override
    public void setPlayersHands() {
        addToFirstPlayersHand("Salvager");
    }

    @Test
    public void skipNotAllowedWhenTrashIsExpected() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Salvager");
        clickOnSkip();
        assertInstanceOf(SalvagerState.class, currentPlayer.getCurrentState());
        assertEquals(currentPlayer, game.currentPlayer());
    }

    @Test
    public void addsBuys() {
        Player currentPlayer = game.currentPlayer();
        assertEquals(1, currentPlayer.getNumberOfBuys());
        int initialNbOfBuys = currentPlayer.getNumberOfBuys();
        clickOnCardInHand("Salvager");
        assertEquals(initialNbOfBuys + 1, currentPlayer.getNumberOfBuys());
//        pause(2);
    }

    @Test
    public void trashes() {
        Player currentPlayer = game.currentPlayer();
        getFromSupplyToHand(currentPlayer, "Lighthouse");
        assertEquals(1, currentPlayer.getNumberOfBuys());
        int initialNbOfCardsInHand = currentPlayer.getCardsInHand().size();
        clickOnCardInHand("Salvager");
        clickOnCardInHand("Lighthouse");
        assertEquals(initialNbOfCardsInHand -2, currentPlayer.getCardsInHand().size());
//        pause(2);
    }

    @Test
    public void increasesMoney() {
        Player currentPlayer = game.currentPlayer();
        getFromSupplyToHand(currentPlayer, "Lighthouse");
        int initialMoney = currentPlayer.getMoney();
        clickOnCardInHand("Salvager");
        clickOnCardInHand("Lighthouse");
        assertEquals(initialMoney + 2, currentPlayer.getMoney());
//        pause(2);
    }

    @Test
    public void movesToRightStates() {
        Player currentPlayer = game.currentPlayer();
        getFromSupplyToHand(currentPlayer, "Lighthouse");
        assertInstanceOf(StartTurnState.class, currentPlayer.getCurrentState());
        clickOnCardInHand("Salvager");
        assertInstanceOf(SalvagerState.class, currentPlayer.getCurrentState());
        clickOnCardInHand("Lighthouse");
        assertInstanceOf(TreasurePhase.class, currentPlayer.getCurrentState());
//        pause(2);
    }

    @Test
    public void movesToRightStatesWithRemainingActions() {
        Player currentPlayer = game.currentPlayer();
        getFromSupplyToHand(currentPlayer, "Lighthouse");
        getFromSupplyToHand(currentPlayer, "Bazaar");
        clickOnCardInHand("Bazaar");
        assertInstanceOf(StartTurnState.class, currentPlayer.getCurrentState());
        clickOnCardInHand("Salvager");
        assertInstanceOf(SalvagerState.class, currentPlayer.getCurrentState());
        clickOnCardInHand("Lighthouse");
        assertInstanceOf(StartTurnState.class, currentPlayer.getCurrentState());
//        pause(2);
    }

}