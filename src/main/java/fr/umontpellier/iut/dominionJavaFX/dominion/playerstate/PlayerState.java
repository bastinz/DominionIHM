package fr.umontpellier.iut.dominionJavaFX.dominion.playerstate;

import fr.umontpellier.iut.dominionJavaFX.dominion.*;

public abstract class PlayerState {
    protected final Player currentPlayer;
    protected PlayerState nextState;


    public PlayerState(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        nextState = this;
    }

    public void passer() {
    }

    public void defausser() {
    }

    public void playTreasuresWasChosen() {
    }

    public void cardInHandWasChosen(String carteChoisie) {
    }

    public void continuerTour() {
/*        if (currentPlayer.getCurrentState() instanceof TourNormal && currentPlayer.actionsRestantAJouer().isEmpty()) { // revoir ce test
            finDuTour();
        }*/
    }

/*
    public void finDuTour() {
//        currentPlayer.finaliserLeTour();
//        getGame().verifieSiFinDePartie();
//        getGame().joueurSuivant();
    }
*/

    public void finDuTour() {
/*        if (getGame().getCurrentState().initialisationTerminee()) {
            getGame().setCurrentState(new PartieEnCours(getGame()));
            getGame().getCurrentState().demarrerPartie();
        } else {*/
            getGame().moveToNextPlayerState();
//        }
    }

    protected Game getGame() {
        return currentPlayer.getGame();
    }
}