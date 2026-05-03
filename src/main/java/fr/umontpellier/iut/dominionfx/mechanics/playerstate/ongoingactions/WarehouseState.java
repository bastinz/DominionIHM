package fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;

import java.util.List;

public class WarehouseState extends OnGoingActionState {

    private int nbCardsToDiscard;

    public WarehouseState(Player currentPlayer) {
        super(currentPlayer);
        this.nbCardsToDiscard = 3;
        getGame().instructionProperty().setValue("Discard 3 cards");
    }

    @Override
    public void skip() {
    }

    @Override
    public void cardInHandWasChosen(String cardName) {
        List<String> availableChoices = currentPlayer.getNamesOfCardsInHand();
        if (!availableChoices.isEmpty() && availableChoices.contains(cardName)) {
            Card cardToDiscard = currentPlayer.getCardFromHand(cardName);
            currentPlayer.moveToDiscard(cardToDiscard);
            nbCardsToDiscard--;
            if (nbCardsToDiscard == 0)
                complete();
            else
                getGame().instructionProperty().setValue("Discard %d card%s".formatted(nbCardsToDiscard, nbCardsToDiscard > 1 ? "s" : ""));
        }
    }
}






















