package fr.umontpellier.iut.dominionfx.mechanics.playerstate;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;

public class ActionPhase extends PlayerState {
    public ActionPhase(Player currentPlayer) {
        super(currentPlayer);
        getGame().instructionProperty().setValue("Action phase or skip");
    }

    public void skip() {
        moveToNextPhase();
    }

    public void startProcess(Card cardName) {
        currentPlayer.decreaseNumberOfActions();
        currentPlayer.playCard(cardName);
        if (currentPlayer.getCurrentState().endOfAction()) {
            moveToNextPhase();
        }
    }

    public void playTreasuresWasChosen() {
        currentPlayer.setCurrentState(new TreasurePhase(currentPlayer));
        currentPlayer.getCurrentState().playTreasuresWasChosen();
    }

    public void moveToNextPhase() {
        if (currentPlayer.areActionsCompleted())
            currentPlayer.setCurrentState(new TreasurePhase(currentPlayer));
        else {
            currentPlayer.setCurrentState(new StartTurnState(currentPlayer, false));
        }
    }

}
