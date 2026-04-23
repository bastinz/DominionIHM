package fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.cards.seaside.traitees.Blockade;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BlockadeState extends OnGoingActionState {
    final private Blockade blocadeCard;
    CompletableFuture<Void> future;

    public BlockadeState(Player currentPlayer, Blockade blocadeCard, CompletableFuture<Void> future) {
        super(currentPlayer);
        this.blocadeCard = blocadeCard;
        getGame().instructionProperty().setValue("Gain a card costing up to 4");
        this.future = future;
    }

    @Override
    public void supplyCardWasChosen(String cardName) {
        List<String> availableChoices = currentPlayer.getGame().getCardsFromSupplyMatchingCondition(c -> c.getCost() <= 4);
        if (!availableChoices.isEmpty() && availableChoices.contains(cardName)) {
            Card card = currentPlayer.getCardFromSupply(cardName);
            currentPlayer.gainToSetAside(card);
            blocadeCard.setCardSetAside(card);
            blocadeCard.setPlayer(currentPlayer);
//            moveToNextPhase(); non parce que c'est une carte attaque
            future.complete(null);
        }
    }
}