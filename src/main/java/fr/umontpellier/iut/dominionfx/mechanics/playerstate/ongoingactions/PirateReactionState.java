package fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions;

/*

public class PirateReactionState extends ReactionPhase { // A voir quelle classe de base

    private final Card reactingCard;
    private Player reactingCardOwner;
    private CompletableFuture<Void> future;

    public PirateReactionState(Player currentPlayer, Player reactingCardOwner, Card reactingCard, CompletableFuture<Void> future) {
        super(currentPlayer);
        this.reactingCard =  reactingCard;
        this.reactingCardOwner = reactingCardOwner;
        this.future = future;
        getGame().instructionProperty().setValue(reactingCardOwner.getName() + " : you may reveal a Reaction card from your hand");
    }

    @Override
    public void temporaryCardWasChosen(String cardName) {
        if (cardName.equals(reactingCard.getName())) {
            Card cardToPlay = reactingCardOwner.getCardFromHand(cardName);
            reactingCardOwner.playCard(cardToPlay);
//            future.complete(null);
        }
    }

    @Override
    public void skip() {
//        future.complete(null);
    }
}



*/
/*        List<String> availableChoices = getGame().getTemporaryCardsNames();
        if (!availableChoices.isEmpty() && availableChoices.contains(cardName)) {
            Card cardToPlay = currentPlayer.getCardFromHand(cardName);
        }*/
