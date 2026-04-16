package fr.umontpellier.iut.dominionJavaFX.dominion.playerstate;

import fr.umontpellier.iut.dominionJavaFX.dominion.Player;

import java.util.List;

public class ActionPhase extends PlayerState {
    public ActionPhase(Player currentPlayer) {
        super(currentPlayer);
        getGame().instructionProperty().setValue("Action phase or skip");
    }

    public void passer() {
        endOfCurrentPlayersTurn();
    }

    public void playTreasuresWasChosen() {
        nextState = new TreasuresPhase(currentPlayer);
        nextState.playTreasuresWasChosen();
    }

    public void cardInHandWasChosen(String cardName) {
        List<String> choixPossibles = currentPlayer.getNamesOfCardsInHand();
        if (!choixPossibles.isEmpty() && choixPossibles.contains(cardName)) {
//            nextState = new CardsInPlayExist(currentPlayer);
            nextState.playTreasuresWasChosen();
        }
        // ici il faut passer à la phase suivante
        // pour l'instant on passe au joueur suivant
//        passer();
    }


}
