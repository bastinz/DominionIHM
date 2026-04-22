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
        List<String> availableChoices = currentPlayer.getNamesOfCardsInHand();
        if (!availableChoices.isEmpty() && availableChoices.contains(cardName)) {
            currentPlayer.salvagerAction(cardName);
            moveToNextPhase();
        }
    }
    
}
