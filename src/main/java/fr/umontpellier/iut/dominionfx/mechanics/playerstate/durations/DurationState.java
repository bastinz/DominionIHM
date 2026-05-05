package fr.umontpellier.iut.dominionfx.mechanics.playerstate.durations;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions.OnGoingActionPhase;

public class DurationState extends OnGoingActionPhase {

    protected Card durationCard;
    public DurationState(Player currentPlayer, Card durationCard) {
        super(currentPlayer);
        this.durationCard = durationCard;
    }

    @Override
    public void skip() {
        durationCard.setHasDurationEffect(false);
        complete();
        moveToNextPhase();
    }
}
