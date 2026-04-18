package fr.umontpellier.iut.dominionJavaFX.dominion.playerstate;

import fr.umontpellier.iut.dominionJavaFX.dominion.Player;
import fr.umontpellier.iut.dominionJavaFX.dominion.cards.Card;

import java.util.List;

public class StartTurn extends PlayerState {
    public StartTurn(Player currentPlayer) {
        super(currentPlayer);
        currentPlayer.startTurn();
        getGame().instructionProperty().setValue("Action phase or skip");
    }

    public void skip() {
        endOfCurrentPlayersTurn();
    }

    public void playTreasuresWasChosen() {
        currentPlayer.setCurrentState(new TreasurePhase(currentPlayer));
        currentPlayer.getCurrentState().playTreasuresWasChosen();
    }

    public void cardInHandWasChosen(String cardName) {
        List<String> choixPossibles = currentPlayer.getNamesOfCardsInHand();
        if (!choixPossibles.isEmpty() && choixPossibles.contains(cardName)) {
            Card cardToPlay = currentPlayer.switchToStateByCardType(cardName);
            currentPlayer.getCurrentState().startProcess(cardToPlay);
        }
    }

}
