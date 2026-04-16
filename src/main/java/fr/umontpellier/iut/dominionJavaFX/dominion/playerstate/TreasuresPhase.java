package fr.umontpellier.iut.dominionJavaFX.dominion.playerstate;

import fr.umontpellier.iut.dominionJavaFX.dominion.Player;

public class TreasuresPhase extends PlayerState {
    public TreasuresPhase(Player currentPlayer) {
        super(currentPlayer);
        getGame().instructionProperty().setValue("Treasure phase");
    }

    public void passer() {
        endOfCurrentPlayersTurn();
    }

    public void playTreasuresWasChosen() {
        currentPlayer.playTreasures();
        // ici il faut passer à la phase suivante
        // pour l'instant on passe au joueur suivant
//        passer();
    }


}
