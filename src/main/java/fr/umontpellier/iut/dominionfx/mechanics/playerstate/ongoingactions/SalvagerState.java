package fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions;

import fr.umontpellier.iut.dominionfx.mechanics.Player;

import java.util.List;

public class SalvagerState extends OnGoingActionState {
    public SalvagerState(Player currentPlayer) {
        super(currentPlayer);
        getGame().instructionProperty().setValue("Trash a card from your hand");
    }

    @Override
    public void cardInHandWasChosen(String cardName) {
        List<String> choixPossibles = currentPlayer.getNamesOfCardsInHand();
        if (!choixPossibles.isEmpty() && choixPossibles.contains(cardName)) {
            currentPlayer.salvagerAction(cardName);
            moveToNextPhase();
        }
    }
    
}
