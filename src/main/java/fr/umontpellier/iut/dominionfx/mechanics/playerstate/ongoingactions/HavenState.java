package fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.cards.seaside.traitees.Haven;

import java.util.List;

public class HavenState extends OnGoingActionState {

    private Haven haven;

    public HavenState(Player currentPlayer, Haven haven) {
        super(currentPlayer);
        getGame().instructionProperty().setValue("Set aside a card from your hand");
        this.haven = haven;
    }

    @Override
    public void cardInHandWasChosen(String cardName) {
        List<String> availableChoices = currentPlayer.getNamesOfCardsInHand();
        if (!availableChoices.isEmpty() && availableChoices.contains(cardName)) {
            Card card = currentPlayer.getCardFromHand(cardName);
            currentPlayer.moveToSetAside(card);
            haven.setCardSetAside(card);
            currentPlayer.getCurrentState().moveToNextPhase();
        }
    }
}