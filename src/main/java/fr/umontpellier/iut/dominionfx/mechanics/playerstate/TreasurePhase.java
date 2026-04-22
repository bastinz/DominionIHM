package fr.umontpellier.iut.dominionfx.mechanics.playerstate;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;

import java.util.List;

public class TreasurePhase extends PlayerState {
    public TreasurePhase(Player currentPlayer) {
        super(currentPlayer);
        getGame().instructionProperty().setValue("Treasure phase");
    }

    public void skip() {
        endOfCurrentPlayersTurn();
    }

    public void cardInHandWasChosen(String cardName) {
        List<String> availableChoices = currentPlayer.getNamesOfTreasuresInHand();
        if (!availableChoices.isEmpty() && availableChoices.contains(cardName)) {
            currentPlayer.playTreasureCard(cardName);
        }
    }

    public void playTreasuresWasChosen() {
        currentPlayer.playTreasures();
    }

    public void supplyCardWasChosen(String cardName) {
        List<String> availableChoices = currentPlayer.getAvailableSupplyCards();
        if (!availableChoices.isEmpty() && availableChoices.contains(cardName)) {
            currentPlayer.buy(cardName);
            if (currentPlayer.areBuysCompleted())
                endOfCurrentPlayersTurn();
        }
    }

    public void startProcess(Card cardName) {
        currentPlayer.disablePlayActions();
        currentPlayer.playCard(cardName);
    }
}
