package fr.umontpellier.iut.dominionJavaFX.dominion.playerstate;

import fr.umontpellier.iut.dominionJavaFX.dominion.*;

public abstract class PlayerState {
    protected final Player currentPlayer;
    protected PlayerState nextState;


    public PlayerState(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
       // nextState = this;
    }

    public void skip() {
    }

    public void playTreasuresWasChosen() {
    }

    public void cardInHandWasChosen(String carteChoisie) {
    }

    public void supplyCardWasChosen(String carteChoisie) {
    }

    public void endOfCurrentPlayersTurn() {
        // on vérifie si fin de partie ??
        getGame().moveToNextPlayerState(); // fait le cleanUp
        currentPlayer.startTurn();
    }

    protected Game getGame() {
        return currentPlayer.getGame();
    }
}