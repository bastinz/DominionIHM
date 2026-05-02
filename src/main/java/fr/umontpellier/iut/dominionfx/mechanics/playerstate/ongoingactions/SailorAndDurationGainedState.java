package fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.cards.seaside.afaire.Sailor;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.PlayerState;

public class SailorAndDurationGainedState extends PlayerState {

    private Card gainedCard;
    private Sailor sailorCard;

    public SailorAndDurationGainedState(Player currentPlayer, Player cardOwner, Card gainedCard, Sailor sailorCard) {
        super(currentPlayer);
        getGame().instructionProperty().setValue("Do you want to play " + gainedCard.getName());
        this.gainedCard = gainedCard;
        this.sailorCard = sailorCard;
    }

    @Override
    public void answer(String choice) {
        if (choice.equals("Yes")) {
            sailorCard.cannotPlayDurationAnyMore();
            currentPlayer.playCard(gainedCard);
//            currentPlayer.setCurrentState(new ExecutingEffectState(currentPlayer));
        }
        future.complete(null);
//        else {
/*            Card nextCardExecutingEffect = currentPlayer.getNextCardExecutingEffect();
            if (nextCardExecutingEffect != null) {
                nextCardExecutingEffect.onPlayerGainCard(currentPlayer, gainedCard, cardOwner);
//                currentPlayer.setCurrentState(new ExecutingGainedCardEffects(currentPlayer, cardOwner, gainedCard, nextCardExecutingEffect));
            } else {
                if (currentPlayer.getInPlay().stream()
                        .anyMatch(card -> card.hasType(CardType.TREASURE))) // si on est en phase Buy, au cas où la carte DURATION gagnée a augmenté les actions
                    currentPlayer.endActionPhase();
                moveToNextExecutingEffect(gainedCard);*/
//            currentPlayer.getCurrentState().moveToNextExecutingEffect(gainedCard);
//            }
//        }
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