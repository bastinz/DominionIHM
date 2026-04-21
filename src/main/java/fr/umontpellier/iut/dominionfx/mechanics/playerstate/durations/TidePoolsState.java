package fr.umontpellier.iut.dominionfx.mechanics.playerstate.durations;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.StartTurnState;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions.OnGoingActionState;

import java.util.List;

public class TidePoolsState extends OnGoingActionState {

    int nbCardsToDiscard;
    public TidePoolsState(Player currentPlayer, int nbCardsToDiscard) {
        super(currentPlayer);
        getGame().instructionProperty().setValue("Discard %d card%s"
                .formatted(nbCardsToDiscard, nbCardsToDiscard > 1 ? "s" : ""));
        this.nbCardsToDiscard = nbCardsToDiscard;
    }

    @Override
    public void cardInHandWasChosen(String cardName) {
        List<String> choixPossibles = currentPlayer.getNamesOfCardsInHand();
        if (!choixPossibles.isEmpty() && choixPossibles.contains(cardName)) {
            Card cardToDiscard = currentPlayer.getCardsInHand().stream().findFirst().orElse(null);
            currentPlayer.moveToDiscard(cardToDiscard);
            nbCardsToDiscard -= 1;
            if (nbCardsToDiscard == 0)
                skip();
            else
                currentPlayer.setCurrentState(new TidePoolsState(currentPlayer, nbCardsToDiscard));
        }
    }

    @Override
    public void skip() {
        currentPlayer.setCurrentState(new StartTurnState(currentPlayer, false));
    }

}

/*
Card chosenCard = p.chooseCardFromSupply(
        "%s: Gain a card costing up to 4$".formatted(this),
        c -> c.getCost() <= 4,
        false);
        if (chosenCard != null) {
Card card = p.getCardFromSupply(chosenCard.getName());
            p.log("gains %s and sets it aside".formatted(card.toLog()));
        p.gainToSetAside(card);
cardSetAside = card;
// il faut mémoriser le joueur qui a joué la carte pour pouvoir plus
// tard chercher la carte mise de côté dans la liste des cartes mises
// de côté du joueur (à cause de l'interaction possible avec Sailor)
player = p;
*/
