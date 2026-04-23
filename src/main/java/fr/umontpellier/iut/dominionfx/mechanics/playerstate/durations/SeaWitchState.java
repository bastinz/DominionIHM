package fr.umontpellier.iut.dominionfx.mechanics.playerstate.durations;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.cards.seaside.traitees.SeaWitch;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions.OnGoingActionState;

import java.util.List;

public class SeaWitchState extends OnGoingActionState {

    private int nbCardsToDiscard;
    private SeaWitch seaWitch;

    public SeaWitchState(Player currentPlayer, SeaWitch seaWitch) {
        super(currentPlayer);
        this.nbCardsToDiscard = 2;
        this.seaWitch = seaWitch;
        getGame().instructionProperty().setValue("Discard 2 cards");
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
            if (nbCardsToDiscard == 0) {
                seaWitch.setHasDurationEffect(false);
                moveToNextPhase();
            }
            else
                getGame().instructionProperty().setValue("Discard %d card%s".formatted(nbCardsToDiscard, nbCardsToDiscard > 1 ? "s" : ""));

        }
    }
}






















