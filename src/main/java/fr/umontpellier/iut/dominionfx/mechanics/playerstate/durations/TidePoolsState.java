package fr.umontpellier.iut.dominionfx.mechanics.playerstate.durations;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;

import java.util.List;

public class TidePoolsState extends DurationState {

    int nbCardsToDiscard;
    public TidePoolsState(Player currentPlayer, int nbCardsToDiscard, Card durationCard) {
        super(currentPlayer, durationCard);
        getGame().instructionProperty().setValue("Discard %d card%s"
                .formatted(nbCardsToDiscard, nbCardsToDiscard > 1 ? "s" : ""));
        this.nbCardsToDiscard = nbCardsToDiscard;
    }

    @Override
    public void cardInHandWasChosen(String cardName) {
        List<String> availableChoices = currentPlayer.getNamesOfCardsInHand();
        if (!availableChoices.isEmpty() && availableChoices.contains(cardName)) {
            Card cardToDiscard = currentPlayer.getCardsInHand().stream().findFirst().orElse(null);
            currentPlayer.moveToDiscard(cardToDiscard);
            nbCardsToDiscard -= 1;
            if (nbCardsToDiscard == 0)
                skip();
            else
                currentPlayer.setCurrentState(new TidePoolsState(currentPlayer, nbCardsToDiscard, durationCard));
        }
    }


}

