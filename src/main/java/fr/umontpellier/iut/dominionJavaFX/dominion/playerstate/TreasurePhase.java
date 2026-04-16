package fr.umontpellier.iut.dominionJavaFX.dominion.playerstate;

import fr.umontpellier.iut.dominionJavaFX.dominion.Player;

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
        List<String> choixPossibles = currentPlayer.getNamesOfTreasuresInHand();
        if (!choixPossibles.isEmpty() && choixPossibles.contains(cardName)) {
            currentPlayer.playTreasureCard(cardName);
        }
    }

    public void playTreasuresWasChosen() {
        currentPlayer.playTreasures();
    }

    public void supplyCardWasChosen(String cardName) {
        List<String> choixPossibles = currentPlayer.getAvailableSupplyCards();
        if (!choixPossibles.isEmpty() && choixPossibles.contains(cardName)) {
            currentPlayer.buy(cardName);
            if (currentPlayer.areBuysCompleted())
                endOfCurrentPlayersTurn();
        }
    }
}
