package fr.umontpellier.iut.dominionfx.cards;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.SupplyPile;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.TreasurePhase;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions.AmbassadorReturnToSupplyState;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions.AmbassadorRevealState;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions.OnGoingActionPhase;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AmbassadorTest extends BaseTestClass {

    @Override
    public void start(Stage stage) {
        game = new Game(new String[]{"PlayerTest1", "PlayerTest2"},
                new String[]{"Ambassador", "Lighthouse"});
        super.start(stage);
    }

    @Override
    public void setPlayersHands() {
       addToFirstPlayersHand("Ambassador");
    }

    @Test
    public void cannotSkipWhenRevealedCardIsExpected() {
        Player currentPlayer = game.currentPlayer();
        clickOnCardInHand("Ambassador");
        clickOnSkip();
        assertInstanceOf(AmbassadorRevealState.class, game.currentPlayer().getCurrentState());
        assertEquals(currentPlayer, game.currentPlayer());
    }

    @Test
    public void cannotSkipWhenNoCopyWasChosen() {
        Player currentPlayer = game.currentPlayer();
        getFromSupplyToHand(currentPlayer, "Lighthouse");
        clickOnCardInHand("Ambassador");
        clickOnCardInHand("Lighthouse");
        clickOnSkip();
        assertInstanceOf(AmbassadorReturnToSupplyState.class, game.currentPlayer().getCurrentState());
        assertEquals(currentPlayer, game.currentPlayer());
    }

    @Test
    public void returnsOneCardToSupply() {
        Player currentPlayer = game.currentPlayer();
        getFromSupplyToHand(currentPlayer, "Lighthouse");
        getFromSupplyToHand(currentPlayer, "Lighthouse");
        int initialNbOfCardsInHand = currentPlayer.getHand().size();
        clickOnCardInHand("Ambassador"); // -1 card
        clickOnCardInHand("Lighthouse");
        clickOnCardInHand("Lighthouse"); // -1 card
        clickOnSkip();
        assertInstanceOf(TreasurePhase.class, currentPlayer.getCurrentState());
        assertEquals(initialNbOfCardsInHand - 2, currentPlayer.getHand().size());
//        pause(2);
    }

    @Test
    public void cannotReturnAnyOtherCardToSupply() {
        Player currentPlayer = game.currentPlayer();
        getFromSupplyToHand(currentPlayer, "Lighthouse");
        getFromSupplyToHand(currentPlayer, "Ambassador");
        int initialNbOfCardsInHand = currentPlayer.getHand().size();
        clickOnCardInHand("Ambassador"); // -1 card
        clickOnCardInHand("Lighthouse");
        clickOnCardInHand("Ambassador"); // nothing should happen
        clickOnSkip();
        assertEquals(initialNbOfCardsInHand - 1, currentPlayer.getHand().size());
        assertInstanceOf(OnGoingActionPhase.class, currentPlayer.getCurrentState());
//        pause(2);
    }

    @Test
    public void returnsTwoCardsToSupply() {
        Player currentPlayer = game.currentPlayer();
        getFromSupplyToHand(currentPlayer, "Lighthouse");
        getFromSupplyToHand(currentPlayer, "Lighthouse");
        int initialNbOfCardsInHand = currentPlayer.getHand().size();
        clickOnCardInHand("Ambassador"); // -1 card
        clickOnCardInHand("Lighthouse");
        clickOnCardInHand("Lighthouse"); // -1 card
        clickOnCardInHand("Lighthouse"); // -1 card
        assertEquals(initialNbOfCardsInHand - 3, currentPlayer.getHand().size());
        assertInstanceOf(TreasurePhase.class, currentPlayer.getCurrentState());
//        pause(2);
    }

    @Test
    public void otherPlayersGetACopyOfRevealedCard() {
        Player currentPlayer = game.currentPlayer();
        getFromSupplyToHand(currentPlayer, "Lighthouse");
        clickOnCardInHand("Ambassador");
        clickOnCardInHand("Lighthouse");
        int initialNbOfCardsInLighthousePile = game.getSupplyPiles().stream()
                .filter(p -> p.getName().equals("Lighthouse"))
                .mapToInt(SupplyPile::size) // ou getTaille(), selon ton modèle
                .findFirst()
                .orElse(0);
        clickOnCardInHand("Lighthouse");
        int currentNbOfCardsInLighthousePile = game.getSupplyPiles().stream()
                .filter(p -> p.getName().equals("Lighthouse"))
                .mapToInt(SupplyPile::size) // ou getTaille(), selon ton modèle
                .findFirst()
                .orElse(0);
        assertEquals(initialNbOfCardsInLighthousePile + 1, currentNbOfCardsInLighthousePile);
        int initialNumberOfOwnedCards = game.getLastPlayer().getAllOwnedCards().size();
        clickOnSkip();
        assertInstanceOf(TreasurePhase.class, currentPlayer.getCurrentState());
        assertEquals(initialNumberOfOwnedCards + 1, game.getLastPlayer().getAllOwnedCards().size());
        assertTrue(listContainsCard(game.getPlayers().get(1).getDiscard(), "Lighthouse"));
//        pause(2);
    }
}
