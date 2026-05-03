package fr.umontpellier.iut.dominionfx.mechanics.playerstate;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import javafx.collections.FXCollections;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReactionPhase extends PlayerState {

    private List<Card> reactingCards;
    private final Player reactingCardOwner;
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
        moveToNextPhase();
    }

    @Override
    public void temporaryCardWasChosen(String cardName) {
        List<String> availableCards = reactingCards.stream().map(Card::getName).toList();
        if (!availableCards.isEmpty() && availableCards.contains(cardName)) {
            Card cardToPlay = reactingCardOwner.getCardFromHand(cardName);
            reactingCards.remove(cardToPlay);
            cardToPlay.reactToPlayerGainCard(currentPlayer, gainedCard, reactingCardOwner);
            getGame().setTemporaryCards(null, null);
            complete();
        }
    }

    public void processReactingCard() {
        // révéler et activer une carte réaction
        reactingCards = reactingCardOwner.getHand().stream()
                .filter(c -> c.canReactToPlayerGainCard(currentPlayer, gainedCard, reactingCardOwner))
                .collect(Collectors.toCollection(ArrayList::new));
        if (reactingCards.isEmpty())
            complete();
        else {
            getGame().setTemporaryCards(FXCollections.observableArrayList(reactingCards), reactingCardOwner.getHand());
        }
    }

}
