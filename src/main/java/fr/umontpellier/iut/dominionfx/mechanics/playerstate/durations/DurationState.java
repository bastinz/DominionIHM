package fr.umontpellier.iut.dominionfx.mechanics.playerstate.durations;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.ActionPhase;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.StartTurnState;

public class DurationState extends ActionPhase {

    protected Card durationCard;
    public DurationState(Player currentPlayer, Card cardWithDuration) {
        super(currentPlayer);
        this.durationCard = cardWithDuration;
    }

    @Override
    public void skip() {
        durationCard.setHasDurationEffect(false);
        currentPlayer.setCurrentState(new StartTurnState(currentPlayer));
    }
}
