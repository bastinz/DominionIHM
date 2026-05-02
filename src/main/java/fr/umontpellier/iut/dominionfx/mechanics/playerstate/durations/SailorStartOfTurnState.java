package fr.umontpellier.iut.dominionfx.mechanics.playerstate.durations;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;

import java.util.List;

public class SailorStartOfTurnState extends DurationState {

    public SailorStartOfTurnState(Player currentPlayer, Card durationCard) {
        super(currentPlayer, durationCard);
        getGame().instructionProperty().setValue("You may trash a card from your hand");
    }

    @Override
    public void cardInHandWasChosen(String cardName) {
        List<String> availableChoices = currentPlayer.getNamesOfCardsInHand();
        if (!availableChoices.isEmpty() && availableChoices.contains(cardName)) {
            Card cardToTrash = currentPlayer.getCardsInHand().stream().findFirst().orElseThrow();
            currentPlayer.moveToTrash(cardToTrash);
            skip();
        }
    }
}