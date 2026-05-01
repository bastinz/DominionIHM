package fr.umontpellier.iut.dominionfx.mechanics.playerstate;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;

import java.util.*;
import java.util.stream.Collectors;

public class ReactionPhase extends PlayerState {

    private List<Card> reactingCards;
    private Player reactingCardOwner;
    private final Card gainedCard;

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
//            moveToNextExecutingEffect(gainedCard);
        }
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
            moveToNextExecutingEffect(gainedCard);
        else
            getGame().setTemporaryCards(reactingCardOwner.getHand());
    }

}
