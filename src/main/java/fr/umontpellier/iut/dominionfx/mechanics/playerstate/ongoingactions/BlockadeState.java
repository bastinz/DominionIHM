package fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.cards.seaside.Blockade;

import java.util.List;

public class BlockadeState extends OnGoingActionState {
    final private Blockade blocadeCard;

    public BlockadeState(Player currentPlayer, Blockade blocadeCard) {
        super(currentPlayer);
        this.blocadeCard = blocadeCard;
        getGame().instructionProperty().setValue("Gain a card costing up to 4");
    }

    @Override
    public void supplyCardWasChosen(String cardName) {
        List<String> choixPossibles = currentPlayer.getGame().getCardsFromSupplyMatchingCondition(c -> c.getCost() <= 4);
        if (!choixPossibles.isEmpty() && choixPossibles.contains(cardName)) {
            Card card = currentPlayer.getCardFromSupply(cardName);
            currentPlayer.gainToSetAside(card);
            blocadeCard.setCardSetAside(card);
            blocadeCard.setPlayer(currentPlayer);
            moveToNextPhase();
        }
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
