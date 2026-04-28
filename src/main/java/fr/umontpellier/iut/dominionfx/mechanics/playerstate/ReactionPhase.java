package fr.umontpellier.iut.dominionfx.mechanics.playerstate;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;

import java.util.*;
import java.util.stream.Collectors;

public class ReactionPhase extends PlayerState {

    private List<Card> reactingCards;
    private Player reactingCardOwner;
    private final Card gainedCard;
    private final Map<Player, List<Card>> allReactionsToProcess = new LinkedHashMap<>() ;

    public ReactionPhase(Player currentPlayer, Card gainedCard) {
        super(currentPlayer);
        getGame().instructionProperty().setValue("Reacting to " + gainedCard.getName());
        this.gainedCard = gainedCard;
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
            cardToPlay.reaction(reactingCardOwner);
            getGame().setTemporaryCards(null);
            moveToNextStep();
        }
    }

    private void moveToNextStep() {
        if (noMoreReactingPlayersToProcess()) {
/*            if (currentPlayer.areBuysCompleted())
                endOfCurrentPlayersTurn();
            else {
                currentPlayer.setCurrentState(new TreasurePhase(currentPlayer));
            }*/
            moveToNextPhase();
        }
    }

    public void processReactingCard() {
        for (Player cardOwner : currentPlayer.getPlayers()) {
            // révéler et activer une carte réaction
            List<Card> listOfReactingCards = cardOwner.getHand().stream() // ce ne serait pas InPlay ???
                    .filter(c -> c.canReactToPlayerGainCard(currentPlayer, gainedCard, cardOwner))
                    .collect(Collectors.toCollection(ArrayList::new));
            if (!listOfReactingCards.isEmpty()) {
                allReactionsToProcess.put(cardOwner, listOfReactingCards);
            }
        }
        moveToNextStep();
    }

    private boolean noMoreReactingPlayersToProcess() {
        Iterator<Map.Entry<Player, List<Card>>> iterator = allReactionsToProcess.entrySet().iterator();
        if (iterator.hasNext()) {
            Map.Entry<Player, List<Card>> reactionsOfOwner = iterator.next();
            reactingCardOwner = reactionsOfOwner.getKey();
            getGame().instructionProperty().setValue(reactingCardOwner.getName() + " reacts to " + gainedCard.getName());
            List<Card> listOfReactionCards = reactionsOfOwner.getValue();
            if (listOfReactionCards != null && !listOfReactionCards.isEmpty()) {
                getGame().setTemporaryCards(reactingCardOwner.getHand());
                reactingCards = listOfReactionCards;
                return false;
            } else {
                iterator.remove();
                return !iterator.hasNext();
            }
        }
        return true;
    }
}
