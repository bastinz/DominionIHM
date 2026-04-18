package fr.umontpellier.iut.dominionJavaFX.dominion.playerstate;

import fr.umontpellier.iut.dominionJavaFX.dominion.Player;
import fr.umontpellier.iut.dominionJavaFX.dominion.cards.Card;

public class ActionPhase extends PlayerState {
    public ActionPhase(Player currentPlayer) {
        super(currentPlayer);
        getGame().instructionProperty().setValue("Action phase or skip");
    }

    public void skip() {
        endOfCurrentPlayersTurn();
    }

    public void startProcess(Card cardName) {
        currentPlayer.decreaseNumberOfActions();
        currentPlayer.playCard(cardName);
    }

    public void playTreasuresWasChosen() {
        currentPlayer.setCurrentState(new TreasurePhase(currentPlayer));
        currentPlayer.getCurrentState().playTreasuresWasChosen();
    }

}
