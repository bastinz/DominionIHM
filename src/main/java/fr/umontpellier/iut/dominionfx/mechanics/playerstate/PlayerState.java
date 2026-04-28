package fr.umontpellier.iut.dominionfx.mechanics.playerstate;

import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;

public abstract class PlayerState {
    protected final Player currentPlayer;
//    protected PlayerState nextState;

    public PlayerState(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
//        nextState = this;
    }

    public void moveToNextPhase() {
        if (currentPlayer.areActionsCompleted()) {
            if (currentPlayer.areBuysCompleted())
                endOfCurrentPlayersTurn();
            else
                currentPlayer.setCurrentState(new TreasurePhase(currentPlayer));
        } else
            currentPlayer.setCurrentState(new StartTurnState(currentPlayer));
    }

    public void skip() {
    }

    public void playTreasuresWasChosen() {
        currentPlayer.endActionPhase();
        currentPlayer.setCurrentState(new TreasurePhase(currentPlayer));
//        currentPlayer.getCurrentState().playTreasuresWasChosen();
        currentPlayer.playTreasures();
    }

    public void answer(String choice) {
    }

    public void cardInHandWasChosen(String cardName) {
    }

    public void supplyCardWasChosen(String cardName) {
    }

    public void temporaryCardWasChosen(String cardName) {
    }

    public void addToMat() {
    }

    public void takeFromMat() {
    }


    public void endOfCurrentPlayersTurn() {
        // on vérifie si fin de partie ??
        getGame().moveToNextPlayerState(); // fait le cleanUp et startTurn
    }

    protected Game getGame() {
        return currentPlayer.getGame();
    }
}