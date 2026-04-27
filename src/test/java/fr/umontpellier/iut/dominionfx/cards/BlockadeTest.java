package fr.umontpellier.iut.dominionfx.cards;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.StartTurnState;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.TreasurePhase;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions.BlockadeState;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BlockadeTest extends BaseTestClass {

    @Override
    public void start(Stage stage) {
        game = new Game(new String[]{"PlayerTest1", "PlayerTest2"},
                new String[]{"Blockade", "Lighthouse", "Treasury", "Bazaar", "Fishing Village"});
        super.start(stage);
    }

    @Override
    public void setPlayersHands() {
       addToFirstPlayersHand("Blockade");
    }

    @Test
    public void increasesNumberOfCardsAside() {
        Player currentPlayer = game.currentPlayer();
        int initialNbOfCardsAside = currentPlayer.getCardsSetAside().size();
        clickOnCardInHand("Blockade");
        clickOnSupplyPile("Lighthouse");
        assertEquals(initialNbOfCardsAside + 1, currentPlayer.getCardsSetAside().size());
        assertInstanceOf(TreasurePhase.class, currentPlayer.getCurrentState());
//        pause(2);
    }

    @Test
    public void refusesCardCostingMoreThan4() {
        Player currentPlayer = game.currentPlayer();
        int initialNbOfCardsAside = currentPlayer.getCardsSetAside().size();
        clickOnCardInHand("Blockade");
        clickOnSupplyPile("Treasury");
        assertEquals(initialNbOfCardsAside, currentPlayer.getCardsSetAside().size());
        assertInstanceOf(BlockadeState.class, currentPlayer.getCurrentState());
//        pause(2);
    }

    @Test
    public void gainCardOnNextTurn() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Blockade");
        clickOnSupplyPile("Lighthouse");
        assertFalse(currentPlayer.getNamesOfCardsInHand().contains("Lighthouse"));
        assertTrue(currentPlayer.getCardsSetAside().stream().map(Card::getName).toList().contains("Lighthouse"));
        clickOnSkip();
        clickOnSkip();
        assertTrue(currentPlayer.getNamesOfCardsInHand().contains("Lighthouse"));
//        pause(2);
    }

    @Test
    public void movesToRightStatesWithRemainingActions() {
        Player currentPlayer = game.currentPlayer();
        getFromSupplyToHand(currentPlayer, "Bazaar");
        WaitForAsyncUtils.waitForFxEvents();

        clickOnCardInHand("Bazaar");
        assertInstanceOf(StartTurnState.class, currentPlayer.getCurrentState());
        clickOnCardInHand("Blockade");
        assertInstanceOf(BlockadeState.class, currentPlayer.getCurrentState());
        clickOnSupplyPile("Lighthouse");
        assertInstanceOf(StartTurnState.class, currentPlayer.getCurrentState());
//        pause(2);
    }

    @Test
    public void getSetAsideCardOnNextTurn() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Blockade");
        assertInstanceOf(BlockadeState.class, currentPlayer.getCurrentState());
        assertTrue(listContainsCard(new ArrayList<>(currentPlayer.getInPlay()), "Blockade"));
        clickOnSupplyPile("Fishing Village");
        assertTrue(listContainsCard(currentPlayer.getCardsSetAside(),"Fishing Village"));
        assertTrue(listContainsCard(currentPlayer.getInPlay(), "Blockade"));
        assertInstanceOf(TreasurePhase.class, currentPlayer.getCurrentState());
        clickOnSkip();
        assertTrue(listContainsCard(game.getPreviousTurnPlayer().getCardsSetAside(), "Fishing Village"));
        assertTrue(listContainsCard(game.getPreviousTurnPlayer().getInPlay(), "Blockade"));
        clickOnSkip();
        assertTrue(listContainsCard(currentPlayer.getHand(), "Fishing Village"));
//        pause(2);
    }

    @Test
    public void otherPlayerGetsCurse() {
        clickOnCardInHand("Blockade");
        clickOnSupplyPile("Lighthouse");
        clickOnSkip();
        clickOnTreasures();
        clickOnSupplyPile("Lighthouse"); // on passe au joueur suivant
        assertTrue(listContainsCard(game.getPreviousTurnPlayer().getDiscard(), "Curse"));
//        pause(2);
    }

    @Test
    public void otherPlayerDoesNotGetCurse() {
        clickOnCardInHand("Blockade");
        clickOnSupplyPile("Lighthouse");
        clickOnSkip();
        clickOnTreasures();
        clickOnSupplyPile("Copper"); // on passe au joueur suivant
        assertFalse(listContainsCard(game.getPreviousTurnPlayer().getDiscard(), "Curse"));
//        pause(2);
    }

}
