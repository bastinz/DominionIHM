package fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions;

import fr.umontpellier.iut.dominionfx.mechanics.CardType;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.cards.seaside.afaire.Sailor;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.ExecutingEffectState;
//import fr.umontpellier.iut.dominionfx.mechanics.playerstate.ExecutingGainedCardEffects;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.ReactionPhase;

import java.util.ArrayList;

import static fr.umontpellier.iut.dominionfx.mechanics.CardType.TREASURE;

public class SailorAndDurationGainedState extends ExecutingEffectState {

    private Card gainedCard;
    private Sailor sailorCard;
    private Player cardOwner;

    public SailorAndDurationGainedState(Player currentPlayer, Player cardOwner, Card gainedCard, Sailor sailorCard) {
        super(currentPlayer);
        getGame().instructionProperty().setValue("Do you want to play " + gainedCard.getName());
        this.gainedCard = gainedCard;
        this.sailorCard = sailorCard;
        this.cardOwner = cardOwner;
    }

    @Override
    public void answer(String choice) {
        if (choice.equals("Yes")) {
            sailorCard.cannotPlayDurationAnyMore();
            currentPlayer.playCard(gainedCard);
        } else {
            Card nextCardExecutingEffect = currentPlayer.getNextCardExecutingEffect();
            if (nextCardExecutingEffect != null) {
                nextCardExecutingEffect.onPlayerGainCard(currentPlayer, gainedCard, cardOwner);
//                currentPlayer.setCurrentState(new ExecutingGainedCardEffects(currentPlayer, cardOwner, gainedCard, nextCardExecutingEffect));
            } else {
                if (currentPlayer.getInPlay().stream()
                        .anyMatch(card -> card.hasType(CardType.TREASURE))) // si la carte DURATION gagnée a augmentée les actions en phase Buy
                    currentPlayer.endActionPhase();
                moveToNextExecutingEffect(gainedCard);
            }
        }
    }
}

/*        if (canPlayDuration && gainedCard.hasType(CardType.DURATION) && p == owner) {
            String choice = p.chooseStringFromButtons(
                    "%s: Do you want to play %s?".formatted(this, gainedCard),
                    Arrays.asList(new Button("Yes", "y"), new Button("No", "n")),
                    false);
            if (choice.equals("y")) {
                canPlayDuration = false;
                p.playCard(gainedCard);
            }
        }*/