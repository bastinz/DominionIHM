package fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.seaside.traitees.GhostShip;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GhostShipState extends OnGoingActionState {

    private int nbCardsToDiscard;
    private GhostShip ghostShip;
    private CompletableFuture<Void> future;

    public GhostShipState(Player currentPlayer, GhostShip ghostShip, int nbCardsToDiscard, CompletableFuture<Void> future) {
        super(currentPlayer);
        this.nbCardsToDiscard = nbCardsToDiscard;
        this.ghostShip = ghostShip;
        getGame().instructionProperty().setValue("Discard down to 3 cards");
        this.future = future;
    }

    @Override
    public void skip() {
    }

    @Override
    public void temporaryCardWasChosen(String cardName) {
        if (future.isDone()) return;
        List<String> availableChoices = getGame().getTemporaryCardsNames();
        if (!availableChoices.isEmpty() && availableChoices.contains(cardName)) {
            ghostShip.discardFromTargetHand(cardName);
            nbCardsToDiscard--;
            if (nbCardsToDiscard == 0) {
                moveToNextPhase();
                future.complete(null);
            }
        }
    }
}



