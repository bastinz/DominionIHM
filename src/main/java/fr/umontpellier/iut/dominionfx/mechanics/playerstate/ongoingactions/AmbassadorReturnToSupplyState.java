package fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions;

import fr.umontpellier.iut.dominionfx.mechanics.Player;

import java.util.concurrent.CompletableFuture;

public class AmbassadorReturnToSupplyState extends OnGoingActionState {

    private int nbCardsToReveal;
    private final String revealedCardName; // pour le message d'instruction uniquement
    private final CompletableFuture<Void> future;

    public AmbassadorReturnToSupplyState(Player currentPlayer, String revealedCardName, CompletableFuture<Void> future) {
        super(currentPlayer);
        getGame().instructionProperty().setValue("Return up to 2 copies of " + revealedCardName);
        this.nbCardsToReveal = 2;
        this.revealedCardName = revealedCardName;
        this.future = future;
    }

    @Override
    public void skip() {
        if (nbCardsToReveal < 2) {
            moveToNextPhase();
            future.complete(null);
        }
    }

    @Override
    public void cardInHandWasChosen(String cardName) {
        if (cardName.equals(revealedCardName)) {
            currentPlayer.moveFromHandToSupply(cardName);
            nbCardsToReveal--;
            if (nbCardsToReveal == 0)
                skip();
            else
                getGame().instructionProperty().setValue("Return 1 copie of "+ revealedCardName);
        }
    }
}






















