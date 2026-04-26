package fr.umontpellier.iut.dominionfx.mechanics.playerstate;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;

import java.util.*;
import java.util.stream.Collectors;

public class ReactionPhase extends PlayerState {

    public ReactionPhase(Player currentPlayer, Card gainedCard) {
        super(currentPlayer);
        getGame().instructionProperty().setValue("Reacting to " + gainedCard.getName());
        this.gainedCard = gainedCard;
        processReactingCard();
    }

    private List<Card> reactingCards;
    private Player reactingCardOwner;
    private Card gainedCard;


    @Override
    public void skip() {
        endOfCurrentPlayersTurn();
    }

    @Override
    public void temporaryCardWasChosen(String cardName) {
        if (!reactingCards.isEmpty() && reactingCards.contains(cardName)) {
            Card cardToPlay = reactingCardOwner.getCardFromHand(cardName);
            cardToPlay.reaction(reactingCardOwner);
            getGame().setTemporaryCards(null);
            moveToNextStep();
        }
    }

    private void moveToNextStep() {
        if (noMoreReactingPlayersToProcess()) {
/*            if (currentPlayer.areBuysCompleted())
                endOfCurrentPlayersTurn();
            else*/
            currentPlayer.setCurrentState(new TreasurePhase(currentPlayer));
        }
    }

    private Map<Player, List<Card>> allReactionsToProcess = new LinkedHashMap<>() ;

    public void processReactingCard() {
        for (Player cardOwner : currentPlayer.getPlayers()) {
/*            // exécuter les effets onGain de toutes les cartes en jeu du joueur
            for (Card cardInPlay : new ArrayList<>(cardOwner.getInPlay())) {
                cardInPlay.onPlayerGainCard(currentPlayer, gainedCard, cardOwner);
            }*/
            // révéler et activer une carte réaction
            List<Card> listOfReactingCards = cardOwner.getHand().stream()
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
            }
            else
                iterator.remove();
        }
        return false;
    }

/*    private boolean noMoreReactionsToProcess() {
        Iterator<Map.Entry<Player, List<Card>>> iterator = allReactionsToProcess.entrySet().iterator();
        if (iterator.hasNext()) {
            Map.Entry<Player, List<Card>> reactionsOfOwner = iterator.next();
            reactingCardOwner = reactionsOfOwner.getKey();
            List<Card> listOfReactionCards = reactionsOfOwner.getValue();
            if (listOfReactionCards != null && !listOfReactionCards.isEmpty()) {
                getGame().setTemporaryCards(reactingCardOwner.getHand());
                reactingCard = listOfReactionCards.removeFirst();
                return false;
            } else {
                iterator.remove();
                return false;
            }
        }
        return true;
    }*/
}
