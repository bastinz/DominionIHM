package fr.umontpellier.iut.dominionJavaFX.dominion.playerstate;

import fr.umontpellier.iut.dominionJavaFX.dominion.Player;

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
        nextState = new TreasuresPhase(currentPlayer);
        nextState.playTreasuresWasChosen();
    }

    public void cardInHandWasChosen(String cardName) {
        List<String> choixPossibles = currentPlayer.getNamesOfCardsInHand();
        if (!choixPossibles.isEmpty() && choixPossibles.contains(cardName)) {
            currentPlayer.switchToStateByCardType(cardName);
        }
        // ici il faut passer à la phase suivante
        // pour l'instant on passe au joueur suivant
//        passer();
    }



}
