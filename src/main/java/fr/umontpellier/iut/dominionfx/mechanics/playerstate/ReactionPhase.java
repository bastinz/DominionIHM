package fr.umontpellier.iut.dominionfx.mechanics.playerstate;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ReactionPhase extends PlayerState {

    private List<Card> reactingCards;
    private Player reactingCardOwner;
    private final Card gainedCard;
    private final CompletableFuture<Void> completionFuture = new CompletableFuture<>();

    public ReactionPhase(Player currentPlayer, Player reactingCardOwner, Card gainedCard) {
        super(currentPlayer);
        getGame().instructionProperty().setValue("Reacting to " + gainedCard.getName());
        this.gainedCard = gainedCard;
        this.reactingCardOwner = reactingCardOwner;
        processReactingCard();
    }

    @Override
    public void skip() {
//        endOfCurrentPlayersTurn();
        moveToNextPhase();
    }

    @Override
    public void temporaryCardWasChosen(String cardName) {
        List<String> availableCards = reactingCards.stream().map(Card::getName).toList();
        if (!availableCards.isEmpty() && availableCards.contains(cardName)) {
            Card cardToPlay = reactingCardOwner.getCardFromHand(cardName);
            reactingCards.remove(cardToPlay);
            getGame().setTemporaryCards(null);
            cardToPlay.reactToPlayerGainCard(currentPlayer, gainedCard, reactingCardOwner);

            complete();
//            moveToNextExecutingEffect(gainedCard);
        }
    }

    public CompletableFuture<Void> getCompletionFuture() {
        return completionFuture;
    }

    // À appeler quand la phase est terminée
    public void complete() {
        completionFuture.complete(null);
    }

/*    private void moveToNextStep() {
        if (noMoreReactingPlayersToProcess()) {
*//*            if (currentPlayer.areBuysCompleted())
                endOfCurrentPlayersTurn();
            else {
                currentPlayer.setCurrentState(new TreasurePhase(currentPlayer));
            }*//*
            moveToNextExecutingEffect(gainedCard);
        }
    }*/

    public void processReactingCard() {
        // révéler et activer une carte réaction
        reactingCards = reactingCardOwner.getHand().stream()
                .filter(c -> c.canReactToPlayerGainCard(currentPlayer, gainedCard, reactingCardOwner))
                .collect(Collectors.toCollection(ArrayList::new));
        if (reactingCards.isEmpty())
            complete();
        else
            getGame().setTemporaryCards(reactingCardOwner.getHand());
    }

}
