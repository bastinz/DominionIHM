package fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.cards.seaside.afaire.Sailor;

public class SailorAndDurationGainedState extends OnGoingActionState {

    private Card gainedCard;
    private Sailor sailorCard;

    public SailorAndDurationGainedState(Player currentPlayer, Card gainedCard, Sailor sailorCard) {
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