package fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.seaside.Blockade;

import java.util.List;

public class BlockadeState extends OnGoingActionPhase {
    final private Blockade blocadeCard;

    public BlockadeState(Player currentPlayer, Blockade blocadeCard) {
        super(currentPlayer);
        this.blocadeCard = blocadeCard;
        getGame().instructionProperty().setValue("Gain a card costing up to 4");
    }

    @Override
    public void supplyCardWasChosen(String cardName) {
        List<String> availableChoices = currentPlayer.getGame().getCardsFromSupplyMatchingCondition(c -> c.getCost() <= 4);
        if (!availableChoices.isEmpty() && availableChoices.contains(cardName)) {
            blocadeCard.endAction(cardName);
            complete();
        }
    }

    @Override
    public void skip() {
    }
}