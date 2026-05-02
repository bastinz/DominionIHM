package fr.umontpellier.iut.dominionfx.mechanics.playerstate;

import fr.umontpellier.iut.dominionfx.mechanics.Player;

public class ActionPhase extends PlayerState {
    public ActionPhase(Player currentPlayer) {
        super(currentPlayer);
        getGame().instructionProperty().setValue("Action phase or skip");
    }

    @Override
    public void skip() {
        complete();
        moveToNextPhase();
    }

/*    @Override
    public void playTreasuresWasChosen() {
        currentPlayer.endActionPhase();
        currentPlayer.setCurrentState(new TreasurePhase(currentPlayer));
        currentPlayer.getCurrentState().playTreasuresWasChosen();
    }*/

/*    public void moveToNextPhase() {
        if (currentPlayer.areActionsCompleted())
            currentPlayer.setCurrentState(new TreasurePhase(currentPlayer));
        else {
            currentPlayer.setCurrentState(new StartTurnState(currentPlayer));
        }
    }*/
}
