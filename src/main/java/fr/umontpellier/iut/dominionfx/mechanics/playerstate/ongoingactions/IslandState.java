package fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;

import java.util.List;

public class IslandState extends OnGoingActionState {

    public IslandState(Player currentPlayer) {
        super(currentPlayer);
        getGame().instructionProperty().setValue("Choose a card to set aside");
    }

    @Override
    public void cardInHandWasChosen(String cardName) {
        List<String> availableChoices = currentPlayer.getNamesOfCardsInHand();
        if (!availableChoices.isEmpty() && availableChoices.contains(cardName)) {
            Card c = currentPlayer.getCardFromHand(cardName);
            currentPlayer.moveToIslandMat(c);
            skip();
        }
    }

    @Override
    public void skip() {
        Card c = currentPlayer.getCardFromInPlay("Island");
        currentPlayer.moveToIslandMat(c);
        complete();
    }
}
