package fr.umontpellier.iut.dominionfx.mechanics.playerstate.durations;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.ActionPhase;

public class DurationState extends ActionPhase {

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
