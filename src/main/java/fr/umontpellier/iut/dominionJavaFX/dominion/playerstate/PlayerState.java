package fr.umontpellier.iut.dominionJavaFX.dominion.playerstate;

import fr.umontpellier.iut.dominionJavaFX.dominion.Game;
import fr.umontpellier.iut.dominionJavaFX.dominion.Player;
import fr.umontpellier.iut.dominionJavaFX.dominion.cards.Card;

public abstract class PlayerState {
    protected final Player currentPlayer;
//    protected PlayerState nextState;

    public PlayerState(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
       // nextState = this;
    }

    public void skip() {
    }

    public void playTreasuresWasChosen() {
    }

    public void cardInHandWasChosen(String cardName) {
    }

    public void supplyCardWasChosen(String cardName) {
    }

    public void startProcess(Card cardToPlay)  {
    }

    public void endOfCurrentPlayersTurn() {
        // on vérifie si fin de partie ??
        getGame().moveToNextPlayerState(); // fait le cleanUp et startTurn
    }

    protected Game getGame() {
        return currentPlayer.getGame();
    }
}