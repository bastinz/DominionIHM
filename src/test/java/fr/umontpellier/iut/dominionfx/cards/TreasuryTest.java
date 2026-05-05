package fr.umontpellier.iut.dominionfx.cards;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.StartTurnState;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.plain.TreasuryState;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TreasuryTest extends BaseTestClass {

    @Override
    public void start(Stage stage) {
        game = new Game(new String[]{"PlayerTest1", "PlayerTest2"},
                new String[]{"Duchy", "Treasury", "Province"});
        super.start(stage);
    }

    @Override
    public void setPlayersHands() {
        addToFirstPlayersHand("Treasury");
        addToFirstPlayersHand("Gold");
    }

    @Test
    public void skipNotAllowedWhenDiscarding() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Treasury");
        clickOnTreasures();
        clickOnSupplyPile("Copper");
        clickOnSkip();
        assertInstanceOf(TreasuryState.class, currentPlayer.getCurrentState());
        assertEquals(currentPlayer, game.currentPlayer());
    }

    @Test
    public void endOfTurnWithVictory() {
        Player firstPlayer = game.currentPlayer();
        clickOnCardInHand("Treasury");
        clickOnTreasures();
        clickOnSupplyPile("Duchy");
        assertNotEquals(firstPlayer, game.currentPlayer());
        assertInstanceOf(StartTurnState.class, game.currentPlayer().getCurrentState());
        clickOnSkip();
        assertEquals(firstPlayer, game.currentPlayer());
        assertFalse(listContainsCard(firstPlayer.getHand(), "Treasury"));
//        pause(2);
    }

    @Test
    public void endOfTurnWithoutVictoryAndAnswerYes() {
        Player firstPlayer = game.currentPlayer();
        clickOnCardInHand("Treasury");
        clickOnTreasures();
        clickOnSupplyPile("Copper");
        clickOnYes();
        assertNotEquals(firstPlayer, game.currentPlayer());
        assertInstanceOf(StartTurnState.class, game.currentPlayer().getCurrentState());
        assertTrue(listContainsCard(firstPlayer.getHand(), "Treasury"));
//        pause(2);
    }

    @Test
    public void endOfTurnWithoutVictoryAndAnswerNo() {
        Player firstPlayer = game.currentPlayer();
        clickOnCardInHand("Treasury");
        clickOnTreasures();
        clickOnSupplyPile("Copper");
        clickOnNo();
        assertNotEquals(firstPlayer, game.currentPlayer());
        assertInstanceOf(StartTurnState.class, game.currentPlayer().getCurrentState());
        assertFalse(listContainsCard(firstPlayer.getHand(), "Treasury"));
//        pause(2);
    }
 }
