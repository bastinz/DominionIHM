package fr.umontpellier.iut.dominionJavaFX.dominion.playerstate;

import fr.umontpellier.iut.dominionJavaFX.dominion.Player;

import java.util.List;

public class PlayTreasures extends PlayerState {
    public PlayTreasures(Player currentPlayer) {
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
        // ici il faut passer à la phase suivante
        // pour l'instant on passe au joueur suivant
//        passer();
    }

    public void playTreasuresWasChosen() {
        currentPlayer.playTreasures();
    }
}
